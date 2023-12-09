package dev.stashy.soundcategories.gui;

import dev.stashy.soundcategories.SoundCategories;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;

import java.util.Arrays;
import java.util.Objects;

public class SoundGroupOptionsScreen extends AbstractSoundListedScreen {
    private final SoundCategory parentCategory;

    public SoundGroupOptionsScreen(Screen parent, GameOptions gameOptions, SoundCategory category) {
        super(parent, gameOptions, Text.translatable("soundCategory." + category.getName()));
        parentCategory = category;
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

        this.list.addCategory(parentCategory);

        final SoundCategory[] categories = Arrays.stream(SoundCategory.values()).filter(it -> {
            return SoundCategories.PARENTS.containsKey(it) && SoundCategories.PARENTS.get(it) == parentCategory;
        }).toArray(SoundCategory[]::new);
        this.list.addAllCategory(categories);

        this.addSelectableChild(this.list);
    }
}
