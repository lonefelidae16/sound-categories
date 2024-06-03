package dev.stashy.soundcategories.mc1_21.gui;

import dev.stashy.soundcategories.shared.gui.VersionedSoundOptionsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.GameOptions;

public class CustomSoundOptionsScreen extends VersionedSoundOptionsScreen {
    public CustomSoundOptionsScreen(Screen parent, GameOptions gameOptions) {
        super(parent, gameOptions);
    }

    @Override
    protected void addOptions() {

    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        return this.list.mouseScrolledImpl(mouseX, mouseY, horizontalAmount, verticalAmount);
    }
}
