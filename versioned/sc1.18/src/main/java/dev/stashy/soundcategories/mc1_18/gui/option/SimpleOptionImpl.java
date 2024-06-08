package dev.stashy.soundcategories.mc1_18.gui.option;

import dev.stashy.soundcategories.shared.SoundCategories;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.CyclingOption;
import net.minecraft.client.option.DoubleOption;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Option;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;

import java.util.function.Consumer;

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

    public static CyclingOption<Boolean> ofBoolean(String key, Text tooltip, boolean value, Consumer<Boolean> consumer) {
        final SoundCategory category = SoundCategory.valueOf(key);
        return CyclingOption.create(SoundCategories.getOptionsTranslationKey(category), tooltip, gameOptions -> {
            return gameOptions.getSoundVolume(category) > 0;
        }, (gameOptions, option, v) -> consumer.accept(v));
    }
}
