package dev.stashy.soundcategories.mc1_20_2.gui;

import dev.stashy.soundcategories.shared.gui.VersionedSoundGroupOptionsScreen;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.sound.SoundCategory;

public class SoundGroupOptionsScreen extends VersionedSoundGroupOptionsScreen {
    public SoundGroupOptionsScreen(Screen parent, GameOptions gameOptions, SoundCategory category) {
        super(parent, gameOptions, category);
    }

    @Override
    protected void addParentCategoryWidget() {
        this.list.addCategory(parentCategory);
    }

    @Override
    protected void init() {
        super.init();

        super.addDoneButton();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xffffff);
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackgroundTexture(context);
    }
}
