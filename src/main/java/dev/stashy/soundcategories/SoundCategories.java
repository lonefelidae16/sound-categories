package dev.stashy.soundcategories;

import com.google.common.collect.Maps;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SoundCategories implements PreLaunchEntrypoint {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final Map<SoundCategory, SoundCategory> PARENTS = new HashMap<>();
    public static final Map<SoundCategory, Float> DEFAULT_LEVELS = new HashMap<>();
    public static final Map<SoundCategory, Boolean> TOGGLEABLE_CATS = Maps.newHashMap();

    public static final Identifier SETTINGS_ICON = new Identifier("soundcategories", "textures/gui/settings.png");

    public static Map<CategoryLoader, List<Field>> getCategories() {
        return FabricLoader.getInstance().getEntrypoints("sound-categories", CategoryLoader.class).stream()
                .collect(Collectors.toMap(it -> it, SoundCategories::getRegistrations));
    }

    private static List<Field> getRegistrations(CategoryLoader loader) {
        return Arrays.stream(loader.getClass().getDeclaredFields()).filter(it -> {
            return it.isAnnotationPresent(CategoryLoader.Register.class);
        }).toList();
    }

    @Override
    public void onPreLaunch() {
        //required so that the new categories are actually created, not actually used
        SoundCategory.MASTER.getClass().getClassLoader();

        var cats = getCategories();
        for (CategoryLoader loader : cats.keySet()) {
            SoundCategory master = null;
            for (Field field : cats.get(loader)) {
                var annotation = field.getAnnotation(CategoryLoader.Register.class);
                try {
                    var category = (SoundCategory) field.get(loader);

                    if (annotation.master()) {
                        master = category;
                    } else if (master != null) {
                        PARENTS.put(category, master);
                    }

                    if (annotation.defaultLevel() != 1f) {
                        DEFAULT_LEVELS.put(category, annotation.defaultLevel());
                    }

                    if (annotation.toggle()) {
                        TOGGLEABLE_CATS.put(category, true);
                    }
                } catch (IllegalAccessException ex) {
                    LOGGER.error("[%s] Unexpected error has caught".formatted(SoundCategories.class.getSimpleName()), ex);
                }
            }
        }
    }
}
