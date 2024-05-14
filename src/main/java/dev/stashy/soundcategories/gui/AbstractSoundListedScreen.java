package dev.stashy.soundcategories.gui;

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
    protected void initTabNavigation() {
        super.initTabNavigation();
        this.list.setDimensions(this.width, this.layout.getContentHeight());
    }

    @Override
    protected void init() {
        this.list = new SoundList(this.client, this.width, this.height - 64, 32, 25);
        super.init();
    }
}
