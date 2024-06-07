package dev.stashy.soundcategories.mc1_21.gui.screen;

import dev.stashy.soundcategories.shared.SoundCategories;
import dev.stashy.soundcategories.shared.gui.screen.VersionedSoundGroupOptionsScreen;
import dev.stashy.soundcategories.shared.gui.screen.VersionedSoundOptionsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.sound.SoundCategory;

import java.util.Arrays;

public class CustomSoundOptionsScreen extends VersionedSoundOptionsScreen {
    public CustomSoundOptionsScreen(Screen parent, GameOptions gameOptions) {
        super(parent, gameOptions);
    }

    @Override
    protected void initList() {
        this.list.addCategory(SoundCategory.MASTER);
        SoundCategory[] cats = Arrays.stream(SoundCategory.values()).filter(it -> {
            return !SoundCategories.PARENTS.containsKey(it) &&
                    !SoundCategories.PARENTS.containsValue(it) &&
                    it != SoundCategory.MASTER;
        }).toArray(SoundCategory[]::new);
        this.list.addAllCategory(cats);
        this.list.addSingleOptionEntry(this.gameOptions.getSoundDevice());
        this.list.addAll(new SimpleOption<?>[]{this.gameOptions.getShowSubtitles(), this.gameOptions.getDirectionalAudio()});

        for (String key : SoundCategories.MASTER_CLASSES) {
            final SoundCategory category = SoundCategories.MASTERS.get(key);
            this.list.addGroup(category, button -> this.client.setScreen(VersionedSoundGroupOptionsScreen.newInstance(this, this.gameOptions, category)));
        }
    }

    @Override
    protected void addOptions() {
    }

    @Override
    protected void initTabNavigation() {
        super.initTabNavigation();
        this.list.setDimensionsImpl(this.width, this.height - 64);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        return this.list.mouseScrolledImpl(mouseX, mouseY, horizontalAmount, verticalAmount);
    }
}
