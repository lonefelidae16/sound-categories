package dev.stashy.soundcategories.mc1_20.gui;

import dev.stashy.soundcategories.shared.gui.VersionedSoundOptionsScreen;
import net.minecraft.client.gui.DrawContext;
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

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float tickDelta) {
        super.renderBackground(context);
        super.render(context, mouseX, mouseY, tickDelta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xffffff);
    }
}
