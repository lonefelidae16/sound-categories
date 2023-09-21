package dev.stashy.soundcategories.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public abstract class AbstractSoundListedScreen extends GameOptionsScreen {
    protected SoundList list;

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

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        if (this.list != null) {
            this.list.render(context, mouseX, mouseY, delta);
        }
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 5, 0xffffff);
    }

    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackgroundTexture(context);
    }
}
