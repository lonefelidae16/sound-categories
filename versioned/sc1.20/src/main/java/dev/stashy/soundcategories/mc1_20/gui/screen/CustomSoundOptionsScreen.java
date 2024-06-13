package dev.stashy.soundcategories.mc1_20.gui.screen;

import dev.stashy.soundcategories.shared.gui.screen.VersionedSoundOptionsScreen;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.sound.SoundCategory;

public class CustomSoundOptionsScreen extends VersionedSoundOptionsScreen {
    public CustomSoundOptionsScreen(Screen parent, GameOptions gameOptions) {
        super(parent, gameOptions);
    }

    @Override
    protected void initList() {
        this.list.addCategory(SoundCategory.MASTER);
        this.list.addAllCategory(this.filterVanillaCategory());
        this.list.addSingleOptionEntry(this.gameOptions.getSoundDevice());
        this.list.addAll(new SimpleOption<?>[]{this.gameOptions.getShowSubtitles(), this.gameOptions.getDirectionalAudio()});

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
    protected void initTabNavigation() {
        super.initTabNavigation();
        this.list.setDimensionsImpl(this.width, this.height - 64);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float tickDelta) {
        super.renderBackground(context);
        super.render(context, mouseX, mouseY, tickDelta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xffffff);
    }
}
