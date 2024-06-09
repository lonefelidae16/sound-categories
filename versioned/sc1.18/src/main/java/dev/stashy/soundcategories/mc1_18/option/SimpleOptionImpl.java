package dev.stashy.soundcategories.mc1_18.option;

import dev.stashy.soundcategories.shared.SoundCategories;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.DoubleOption;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Option;
import net.minecraft.sound.SoundCategory;

public class SimpleOptionImpl {
    public static ClickableWidget createWidget(Object instance, GameOptions options, int x, int y, int width) {
        return ((Option) instance).createButton(options, x, y, width);
    }

    public static Option init(GameOptions options, SoundCategory category) {
        return new DoubleOption(SoundCategories.getOptionsTranslationKey(category), 0, 1, 0,
                gameOptions -> (double) gameOptions.getSoundVolume(category),
                (gameOptions, value) -> gameOptions.setSoundVolume(category, value.floatValue()),
                (gameOptions, doubleOption) -> {
                    double value = doubleOption.get(gameOptions);
                    if (value == 0.) {
                        return doubleOption.getGenericLabel(ScreenTexts.OFF);
                    } else {
                        return doubleOption.getPercentLabel(value);
                    }
                });
    }
}
