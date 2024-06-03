package dev.stashy.soundcategories.shared.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public abstract class AbstractSoundListedScreen extends GameOptionsScreen {
    protected VersionedElementListWrapper list;

    public AbstractSoundListedScreen(Screen parent, GameOptions gameOptions, Text title) {
        super(parent, gameOptions, title);
    }

    protected void addDoneButton() {
        addDoneButton(false);
    }

    protected void addDoneButton(boolean withCancel) {
        if (withCancel) {
            this.addDrawableChild(
                    ButtonWidget.builder(ScreenTexts.DONE, (button) -> {
                                this.client.options.write();
                                this.client.setScreen(this.parent);
                            })
                            .dimensions(this.width / 2 - 155, this.height - 27, 150, 20)
                            .build()
            );
            this.addDrawableChild(
                    ButtonWidget.builder(ScreenTexts.CANCEL, (button) -> this.client.setScreen(this.parent))
                            .dimensions(this.width / 2 - 155 + 160, this.height - 27, 150, 20)
                            .build()
            );
        } else {
            this.addDrawableChild(
                    ButtonWidget.builder(ScreenTexts.DONE, (button) -> {
                                this.client.options.write();
                                this.client.setScreen(this.parent);
                            })
                            .dimensions(this.width / 2 - 100, this.height - 27, 200, 20)
                            .build()
            );
        }
    }

    @Override
    protected void initTabNavigation() {
        super.initTabNavigation();
        this.list.setDimensionsImpl(this.width, this.height - 64);
    }

    @Override
    protected void init() {
        this.list = VersionedElementListWrapper.newInstance(this.client, this.width, this.height, 32, this.height - 32, 25);
        super.init();
    }
}
