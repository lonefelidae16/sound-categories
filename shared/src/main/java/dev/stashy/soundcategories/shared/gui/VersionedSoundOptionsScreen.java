package dev.stashy.soundcategories.shared.gui;

import dev.stashy.soundcategories.shared.SoundCategories;
import me.lonefelidae16.groominglib.api.McVersionInterchange;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Objects;

@Environment(EnvType.CLIENT)
public abstract class VersionedSoundOptionsScreen extends AbstractSoundListedScreen {
    public VersionedSoundOptionsScreen(Screen parent, GameOptions options) {
        super(parent, options, Text.translatable("options.sounds.title"));
    }

    public static VersionedSoundOptionsScreen newInstance(Screen parent, GameOptions settings) {
        try {
            Class<VersionedSoundOptionsScreen> screen = McVersionInterchange.getCompatibleClass(SoundCategories.BASE_PACKAGE, "gui.CustomSoundOptionsScreen");
            Constructor<VersionedSoundOptionsScreen> constructor = screen.getConstructor(Screen.class, GameOptions.class);
            return constructor.newInstance(parent, settings);
        } catch (Exception ex) {
            SoundCategories.LOGGER.error("Cannot instantiate 'CustomSoundOptionsScreen'", ex);
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

        this.list.addCategory(SoundCategory.MASTER);
        SoundCategory[] cats = Arrays.stream(SoundCategory.values()).filter(it -> {
            return !SoundCategories.PARENTS.containsKey(it) &&
                    !SoundCategories.PARENTS.containsValue(it) &&
                    it != SoundCategory.MASTER;
        }).toArray(SoundCategory[]::new);
        this.list.addAllCategory(cats);
        this.list.addSingleOptionEntry(gameOptions.getSoundDevice());
        this.list.addAll(new SimpleOption[]{gameOptions.getShowSubtitles(), gameOptions.getDirectionalAudio()});

        for (String key : SoundCategories.MASTER_CLASSES) {
            final SoundCategory category = SoundCategories.MASTERS.get(key);
            this.list.addGroup(category, button -> this.client.setScreen(VersionedSoundGroupOptionsScreen.newInstance(this, gameOptions, category)));
        }

        this.addDrawableChild(this.list);
    }
}
