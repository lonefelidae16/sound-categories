package dev.stashy.soundcategories.mc1_18.gui.screen;

import dev.stashy.soundcategories.shared.gui.screen.VersionedSoundOptionsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Option;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.sound.SoundCategory;

public class CustomSoundOptionsScreen extends VersionedSoundOptionsScreen {
    public CustomSoundOptionsScreen(Screen parent, GameOptions gameOptions) {
        super(parent, gameOptions);
    }

    @Override
    protected void initList() {
        this.list.addCategory(SoundCategory.MASTER);
        this.list.addAllCategory(this.filterVanillaCategory());

        this.list.addDrawable(Option.AUDIO_DEVICE.createButton(this.gameOptions, this.width / 2 - 155, 0, 310));
        this.list.addDrawable(Option.SUBTITLES.createButton(this.gameOptions, this.width / 2 - 75, 0, 150));

        for (SoundCategory category : this.filterCustomizedMasterCategory()) {
            this.list.addGroup(category, button -> this.client.setScreen(new SoundGroupOptionsScreen(this, this.gameOptions, category)));
        }
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
