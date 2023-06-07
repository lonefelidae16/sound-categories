package dev.stashy.soundcategories.gui;

import dev.stashy.soundcategories.SoundCategories;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;

import java.util.Arrays;

public class SoundGroupOptionsScreen extends GameOptionsScreen {
    private final SoundCategory parentCategory;

    public SoundGroupOptionsScreen(Screen parent, GameOptions gameOptions, SoundCategory category) {
        super(parent, gameOptions, Text.translatable("soundCategory." + category.getName()));
        parentCategory = category;
    }

    private SoundList list;

    protected void init() {
        this.list = new SoundList(this.client, this.width, this.height, 32, this.height - 32, 25);

        this.list.addCategory(parentCategory);

        final SoundCategory[] categories = Arrays.stream(SoundCategory.values()).filter(it -> {
            return SoundCategories.PARENTS.containsKey(it) && SoundCategories.PARENTS.get(it) == parentCategory;
        }).toArray(SoundCategory[]::new);
        this.list.addAllCategory(categories);

        this.addSelectableChild(this.list);

        this.addDrawableChild(
                ButtonWidget.builder(ScreenTexts.DONE, (button) -> {
                            this.client.options.write();
                            this.client.setScreen(this.parent);
                        })
                        .dimensions(this.width / 2 - 155, this.height - 27, 310, 20)
                        .build()
        );
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        this.list.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 5, 16777215);
        super.render(context, mouseX, mouseY, delta);
    }
}
