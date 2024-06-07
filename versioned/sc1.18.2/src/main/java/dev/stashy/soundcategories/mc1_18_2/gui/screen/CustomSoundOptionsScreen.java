package dev.stashy.soundcategories.mc1_18_2.gui.screen;

import dev.stashy.soundcategories.shared.SoundCategories;
import dev.stashy.soundcategories.shared.gui.screen.VersionedSoundGroupOptionsScreen;
import dev.stashy.soundcategories.shared.gui.screen.VersionedSoundOptionsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Option;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.sound.SoundCategory;

import java.util.Arrays;

public class CustomSoundOptionsScreen extends VersionedSoundOptionsScreen {
    public CustomSoundOptionsScreen(Screen parent, GameOptions gameOptions) {
        super(parent, gameOptions);
    }

    @Override
    protected void initList() {
        this.list.addCategory(SoundCategory.MASTER);
        SoundCategory[] cats = Arrays.stream(SoundCategory.values()).filter(it -> {
            return !SoundCategories.PARENTS.containsKey(it) &&
                    !SoundCategories.PARENTS.containsValue(it) &&
                    it != SoundCategory.MASTER;
        }).toArray(SoundCategory[]::new);
        this.list.addAllCategory(cats);

        this.list.addDrawable(Option.AUDIO_DEVICE.createButton(this.gameOptions, this.width / 2 - 155, 0, 310));
        this.list.addDrawable(Option.SUBTITLES.createButton(this.gameOptions, this.width / 2 - 75, 0, 150));

        for (String key : SoundCategories.MASTER_CLASSES) {
            final SoundCategory category = SoundCategories.MASTERS.get(key);
            this.list.addGroup(category, button -> this.client.setScreen(VersionedSoundGroupOptionsScreen.newInstance(this, this.gameOptions, category)));
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
