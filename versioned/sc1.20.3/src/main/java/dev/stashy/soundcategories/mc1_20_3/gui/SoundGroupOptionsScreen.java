package dev.stashy.soundcategories.mc1_20_3.gui;

import dev.stashy.soundcategories.shared.SoundCategories;
import dev.stashy.soundcategories.shared.gui.VersionedSoundGroupOptionsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.sound.SoundCategory;

import java.util.Arrays;
import java.util.Objects;

public class SoundGroupOptionsScreen extends VersionedSoundGroupOptionsScreen {
    public SoundGroupOptionsScreen(Screen parent, GameOptions gameOptions, SoundCategory category) {
        super(parent, gameOptions, category);
    }

    @Override
    protected void setupWidgets() {
        try {
            Objects.requireNonNull(this.list);
        } catch (NullPointerException ex) {
            SoundCategories.LOGGER.error("Error during screen initialization", ex);
            return;
        }

        this.list.addCategory(parentCategory);

        final SoundCategory[] categories = Arrays.stream(SoundCategory.values()).filter(it -> {
            return SoundCategories.PARENTS.containsKey(it) && SoundCategories.PARENTS.get(it) == parentCategory;
        }).toArray(SoundCategory[]::new);
        this.list.addAllCategory(categories);

        this.addDrawableChild(this.list);
    }

    @Override
    protected void init() {
        super.init();

        super.addDoneButton();
    }
}
