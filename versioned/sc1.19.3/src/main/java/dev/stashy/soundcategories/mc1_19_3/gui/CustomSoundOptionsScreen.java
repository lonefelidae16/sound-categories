package dev.stashy.soundcategories.mc1_19_3.gui;

import dev.stashy.soundcategories.shared.gui.VersionedSoundOptionsScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.util.math.MatrixStack;

public class CustomSoundOptionsScreen extends VersionedSoundOptionsScreen {
    public CustomSoundOptionsScreen(Screen parent, GameOptions gameOptions) {
        super(parent, gameOptions);
    }

    @Override
    protected void init() {
        super.init();

        super.addDoneButton();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawCenteredTextWithShadow(matrices, this.textRenderer, this.title.asOrderedText(), this.width / 2, 20, 0xffffff);
    }
}
