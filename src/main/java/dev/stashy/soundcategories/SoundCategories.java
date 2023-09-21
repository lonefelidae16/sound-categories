package dev.stashy.soundcategories;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.annotation.AnnotationFormatError;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SoundCategories implements PreLaunchEntrypoint {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "soundcategories";
    private static final List<String> SUPPRESSED_NAMES = Lists.newArrayList();

    /**
     * The Map of {@link SoundCategory} including to which group the category belongs.<br>
     * <code>Unique category -> Group category</code>
     */
    public static final Map<SoundCategory, SoundCategory> PARENTS = new HashMap<>();
    /**
     * The Map of {@link String} -> {@link SoundCategory} showing which a master category the class has.<br>
     * <code>Class name -> Master category</code>
     */
    public static final Map<String, SoundCategory> MASTERS = Maps.newHashMap();
    public static String[] MASTER_CLASSES;
    public static final Map<SoundCategory, Float> DEFAULT_LEVELS = new HashMap<>();
    public static final Map<SoundCategory, Boolean> TOGGLEABLE_CATS = Maps.newHashMap();
    public static final Map<SoundCategory, Text> TOOLTIPS = Maps.newHashMap();

    public static final ButtonTextures SETTINGS_ICON = new ButtonTextures(
            new Identifier(MOD_ID, "settings/button"),
            new Identifier(MOD_ID, "settings/disabled"),
            new Identifier(MOD_ID, "settings/hover"),
            new Identifier(MOD_ID, "settings/disabled")
    );

    /**
     * Retrieves all {@link EntrypointContainer} from the key <code>"sound-categories"</code> and their annotation fields.
     */
    public static Map<EntrypointContainer<CategoryLoader>, List<Field>> getCategories() {
        return FabricLoader.getInstance().getEntrypointContainers("sound-categories", CategoryLoader.class).stream()
                .collect(Collectors.toMap(it -> it, it -> getRegistrations(it.getEntrypoint())));
    }

    /**
     * Retrieves all annotated fields from specified {@link CategoryLoader}.
     *
     * @param loader The target instance to use {@link java.lang.reflect}.
     */
    private static List<Field> getRegistrations(CategoryLoader loader) {
        return Arrays.stream(loader.getClass().getDeclaredFields()).filter(it -> {
            return it.isAnnotationPresent(CategoryLoader.Register.class);
        }).toList();
    }

    private static String generateFieldClassName(Class<?> clazz, Field field) {
        return "%s#%s".formatted(clazz.getCanonicalName(), field.getName());
    }

    @Override
    public void onPreLaunch() {
        //required so that the new categories are actually created, not actually used
        SoundCategory.MASTER.getClass().getClassLoader();

        try {
            final Map<EntrypointContainer<CategoryLoader>, List<Field>> allAnnotations = getCategories();

            // First fetch for the MASTER categories.
            for (EntrypointContainer<CategoryLoader> container : allAnnotations.keySet()) {
                for (Field field : allAnnotations.get(container)) {
                    final CategoryLoader categoryLoader = container.getEntrypoint();
                    final CategoryLoader.Register annotation = field.getAnnotation(CategoryLoader.Register.class);
                    final String className = categoryLoader.getClass().getCanonicalName();
                    if (!(field.get(categoryLoader) instanceof final SoundCategory category)) {
                        final String fieldClassName = generateFieldClassName(categoryLoader.getClass(), field);
                        if (!SUPPRESSED_NAMES.contains(fieldClassName)) {
                            LOGGER.error(
                                    "[%s] Cast check failed for the member '%s'.".formatted(SoundCategories.class.getSimpleName(), fieldClassName),
                                    new ClassCastException("Can not cast %s to SoundCategory".formatted(field.get(categoryLoader).getClass().getCanonicalName())));
                            SUPPRESSED_NAMES.add(fieldClassName);
                        }
                        continue;
                    }

                    if (!annotation.master()) {
                        continue;
                    }

                    if (MASTERS.containsKey(className)) {
                        // The MASTER already registered.
                        if (!SUPPRESSED_NAMES.contains(className)) {
                            LOGGER.warn(
                                    "[%s] Unexpected annotation was found.".formatted(SoundCategories.class.getSimpleName()),
                                    new AnnotationFormatError("Class '%s' has a duplicate member with annotation value 'master'!".formatted(className)));
                            SUPPRESSED_NAMES.add(className);
                        }
                        PARENTS.put(category, MASTERS.get(className));
                    }
                    MASTERS.putIfAbsent(className, category);
                }
            }

            MASTER_CLASSES = MASTERS.keySet().toArray(String[]::new);
            Arrays.sort(MASTER_CLASSES);

            // Put all the customized SoundCategories.
            for (EntrypointContainer<CategoryLoader> container : allAnnotations.keySet()) {
                for (Field field : allAnnotations.get(container)) {
                    final CategoryLoader categoryLoader = container.getEntrypoint();
                    final CategoryLoader.Register annotation = field.getAnnotation(CategoryLoader.Register.class);
                    final String className = categoryLoader.getClass().getCanonicalName();
                    if (!(field.get(categoryLoader) instanceof final SoundCategory category)) {
                        continue;
                    }

                    if (!annotation.master()) {
                        if (MASTERS.containsKey(className)) {
                            PARENTS.put(category, MASTERS.get(className));
                        } else {
                            // The 'orphan' category was found, will be grouped together with Vanilla volume options.
                            // This is deprecated as it causes confusion for users.
                            if (!SUPPRESSED_NAMES.contains(className)) {
                                LOGGER.warn("[{}] Missing annotation value 'master' in class '{}'. This is deprecated.",
                                        SoundCategories.class.getSimpleName(), className);
                                LOGGER.warn("[{}] To avoid this message, please specify \"master = true\" in one of the @Register annotation in your class.",
                                        SoundCategories.class.getSimpleName());
                                SUPPRESSED_NAMES.add(className);
                            }
                        }
                    }

                    if (annotation.defaultLevel() != 1f) {
                        DEFAULT_LEVELS.put(category, annotation.defaultLevel());
                    }

                    if (annotation.toggle()) {
                        TOGGLEABLE_CATS.put(category, true);
                    }

                    if (!annotation.tooltip().isEmpty()) {
                        TOOLTIPS.put(category, Text.translatable(annotation.tooltip()));
                    }
                }
            }
        } catch (Throwable ex) {
            LOGGER.error("[%s] Unexpected error has caught".formatted(SoundCategories.class.getSimpleName()), ex);
        }

        // Cleanup.
        SUPPRESSED_NAMES.clear();
    }
}
