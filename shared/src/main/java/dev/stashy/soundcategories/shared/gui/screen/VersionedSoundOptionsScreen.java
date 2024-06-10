package dev.stashy.soundcategories.shared.gui.screen;

import dev.stashy.soundcategories.shared.SoundCategories;
import dev.stashy.soundcategories.shared.text.VersionedText;
import me.lonefelidae16.groominglib.api.McVersionInterchange;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.sound.SoundCategory;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Objects;

@Environment(EnvType.CLIENT)
public abstract class VersionedSoundOptionsScreen extends AbstractSoundListedScreen {
    private static final String METHOD_KEY_INIT = VersionedSoundOptionsScreen.class.getCanonicalName() + "#<init>";

    static {
        try {
            Class<VersionedSoundOptionsScreen> clazz = McVersionInterchange.getCompatibleClass(SoundCategories.BASE_PACKAGE, "gui.screen.CustomSoundOptionsScreen");
            Constructor<VersionedSoundOptionsScreen> constructor = clazz.getConstructor(Screen.class, GameOptions.class);
            SoundCategories.CACHED_INIT_MAP.put(METHOD_KEY_INIT, Objects.requireNonNull(constructor));
        } catch (Exception ex) {
            SoundCategories.LOGGER.error("Failed to find 'CustomSoundOptionsScreen' class.", ex);
        }
    }

    public VersionedSoundOptionsScreen(Screen parent, GameOptions options) {
        super(parent, options, VersionedText.INSTANCE.translatable("options.sounds.title"));
    }

    public static VersionedSoundOptionsScreen newInstance(Screen parent, GameOptions settings) {
        try {
            Constructor<VersionedSoundOptionsScreen> constructor = (Constructor<VersionedSoundOptionsScreen>) SoundCategories.CACHED_INIT_MAP.get(METHOD_KEY_INIT);
            return constructor.newInstance(parent, settings);
        } catch (Exception ex) {
            SoundCategories.LOGGER.error("Cannot instantiate 'CustomSoundOptionsScreen'", ex);
        }
        return null;
    }

    protected abstract void initList();

    @Override
    protected void init() {
        super.init();

        try {
            Objects.requireNonNull(this.list);
        } catch (NullPointerException ex) {
            SoundCategories.LOGGER.error("Error during screen initialization", ex);
            return;
        }

        this.initList();
    }

    protected SoundCategory[] filterVanillaCategory() {
        return Arrays.stream(SoundCategory.values()).filter(it -> {
            return !SoundCategories.PARENTS.containsKey(it) &&
                    !SoundCategories.PARENTS.containsValue(it) &&
                    it != SoundCategory.MASTER;
        }).toArray(SoundCategory[]::new);
    }

    protected SoundCategory[] filterCustomizedMasterCategory() {
        return Arrays.stream(SoundCategories.MASTER_CLASSES).map(SoundCategories.MASTERS::get).toArray(SoundCategory[]::new);
    }
}
