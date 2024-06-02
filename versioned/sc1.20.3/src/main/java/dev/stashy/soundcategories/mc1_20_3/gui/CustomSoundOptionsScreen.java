package dev.stashy.soundcategories.mc1_20_3.gui;

import dev.stashy.soundcategories.shared.gui.VersionedSoundOptionsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.GameOptions;

public class CustomSoundOptionsScreen extends VersionedSoundOptionsScreen {
    public CustomSoundOptionsScreen(Screen parent, GameOptions gameOptions) {
        super(parent, gameOptions);
    }

    @Override
    protected void init() {
        super.init();

        super.addDoneButton();
    }
}
