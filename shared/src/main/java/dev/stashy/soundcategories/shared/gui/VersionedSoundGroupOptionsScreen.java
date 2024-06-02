package dev.stashy.soundcategories.shared.gui;

import dev.stashy.soundcategories.shared.SoundCategories;
import me.lonefelidae16.groominglib.api.McVersionInterchange;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;

import java.lang.reflect.Constructor;

public abstract class VersionedSoundGroupOptionsScreen extends AbstractSoundListedScreen {
    protected final SoundCategory parentCategory;

    public VersionedSoundGroupOptionsScreen(Screen parent, GameOptions gameOptions, SoundCategory category) {
        super(parent, gameOptions, Text.translatable("soundCategory." + category.getName()));
        parentCategory = category;
    }

    protected abstract void setupWidgets();

    public static VersionedSoundGroupOptionsScreen newInstance(Screen parent, GameOptions settings, SoundCategory category) {
        try {
            Class<VersionedSoundGroupOptionsScreen> screen = McVersionInterchange.getCompatibleClass(SoundCategories.BASE_PACKAGE, "gui.SoundGroupOptionsScreen");
            Constructor<VersionedSoundGroupOptionsScreen> constructor = screen.getConstructor(Screen.class, GameOptions.class, SoundCategory.class);
            return constructor.newInstance(parent, settings, category);
        } catch (Exception ex) {
            SoundCategories.LOGGER.error("Cannot instantiate 'SoundGroupOptionsScreen'", ex);
        }
        return null;
    }

    @Override
    protected void init() {
        super.init();

        this.setupWidgets();
    }
}
