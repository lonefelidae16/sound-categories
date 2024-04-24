package dev.stashy.soundcategories.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;

public abstract class AbstractSoundListedScreen extends GameOptionsScreen {
    protected SoundList list;

    public AbstractSoundListedScreen(Screen parent, GameOptions gameOptions, Text title) {
        super(parent, gameOptions, title);
    }

    @Override
    protected void init() {
        super.init();
        this.list = new SoundList(this.client, this.width, this.height - 64, 32, 25);
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        if (this.list != null) {
            this.list.render(context, mouseX, mouseY, delta);
        }
    }
}
