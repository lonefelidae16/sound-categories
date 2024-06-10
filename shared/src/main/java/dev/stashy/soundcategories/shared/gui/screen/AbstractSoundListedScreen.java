package dev.stashy.soundcategories.shared.gui.screen;

import dev.stashy.soundcategories.shared.SoundCategories;
import dev.stashy.soundcategories.shared.gui.widget.VersionedButtonWrapper;
import dev.stashy.soundcategories.shared.gui.widget.VersionedElementListWrapper;
import dev.stashy.soundcategories.shared.text.VersionedText;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;

import java.util.Arrays;

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
                    VersionedButtonWrapper.newInstance(
                            this.width / 2 - 155, this.height - 27, 150, 20,
                            VersionedText.INSTANCE.getDoneText(), (button) -> {
                                this.client.options.write();
                                this.client.setScreen(this.parent);
                            }
                    )
            );
            this.addDrawableChild(
                    VersionedButtonWrapper.newInstance(
                            this.width / 2 - 155 + 160, this.height - 27, 150, 20,
                            VersionedText.INSTANCE.getCancelText(), (button) -> this.client.setScreen(this.parent)
                    )
            );
        } else {
            this.addDrawableChild(
                    VersionedButtonWrapper.newInstance(
                            this.width / 2 - 100, this.height - 27, 200, 20,
                            VersionedText.INSTANCE.getDoneText(), (button) -> {
                                this.client.options.write();
                                this.client.setScreen(this.parent);
                            }
                    )
            );
        }
    }

    @Override
    protected void init() {
        this.list = VersionedElementListWrapper.newInstance(this.client, this.width, this.height, 32, this.height - 32, 25);
        super.init();

        this.addDrawableChild(this.list);
    }

    protected SoundCategory[] filterByParentCategory(SoundCategory parentCategory) {
        return Arrays.stream(SoundCategory.values()).filter(it -> {
            return SoundCategories.PARENTS.containsKey(it) && SoundCategories.PARENTS.get(it) == parentCategory;
        }).toArray(SoundCategory[]::new);
    }
}
