package dev.stashy.soundcategories.shared.gui.screen;

import dev.stashy.soundcategories.shared.SoundCategories;
import me.lonefelidae16.groominglib.api.McVersionInterchange;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.sound.SoundCategory;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Objects;

public abstract class VersionedSoundGroupOptionsScreen extends AbstractSoundListedScreen {
    private static final String METHOD_SIGN_CONSTR = VersionedSoundGroupOptionsScreen.class.getCanonicalName() + "#<init>";

    protected final SoundCategory parentCategory;

    static {
        try {
            Class<VersionedSoundGroupOptionsScreen> clazz = McVersionInterchange.getCompatibleClass(SoundCategories.BASE_PACKAGE, "gui.screen.SoundGroupOptionsScreen");
            Constructor<VersionedSoundGroupOptionsScreen> constructor = clazz.getConstructor(Screen.class, GameOptions.class, SoundCategory.class);
            SoundCategories.CACHED_INIT_MAP.put(METHOD_SIGN_CONSTR, Objects.requireNonNull(constructor));
        } catch (Exception ex) {
            SoundCategories.LOGGER.error("Failed to init 'SoundGroupOptionsScreen' class.", ex);
        }
    }

    public VersionedSoundGroupOptionsScreen(Screen parent, GameOptions gameOptions, SoundCategory category) {
        super(parent, gameOptions, VersionedText.INSTANCE.translatable(SoundCategories.getOptionsTranslationKey(category)));
        this.parentCategory = category;
    }

    protected abstract void addParentCategoryWidget();

    public static VersionedSoundGroupOptionsScreen newInstance(Screen parent, GameOptions settings, SoundCategory category) {
        try {
            Constructor<VersionedSoundGroupOptionsScreen> constructor = (Constructor<VersionedSoundGroupOptionsScreen>) SoundCategories.CACHED_INIT_MAP.get(METHOD_SIGN_CONSTR);
            return constructor.newInstance(parent, settings, category);
        } catch (Exception ex) {
            SoundCategories.LOGGER.error("Cannot instantiate 'SoundGroupOptionsScreen'", ex);
        }
        return null;
    }

    @Override
    protected void init() {
        super.init();

        try {
            Objects.requireNonNull(this.list);
        } catch (NullPointerException ex) {
            SoundCategories.LOGGER.error("Error during screen initialization", ex);
            return;
        }

        this.addParentCategoryWidget();

        final SoundCategory[] categories = Arrays.stream(SoundCategory.values()).filter(it -> {
            return SoundCategories.PARENTS.containsKey(it) && SoundCategories.PARENTS.get(it) == this.parentCategory;
        }).toArray(SoundCategory[]::new);
        this.list.addAllCategory(categories);

        this.addDrawableChild((Element & Drawable & Selectable) this.list);
    }
}
