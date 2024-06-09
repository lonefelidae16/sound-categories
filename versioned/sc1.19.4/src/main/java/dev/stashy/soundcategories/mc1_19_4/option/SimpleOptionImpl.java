package dev.stashy.soundcategories.mc1_19_4.option;

import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.sound.SoundCategory;

public class SimpleOptionImpl {
    public static ClickableWidget createWidget(Object instance, GameOptions options, int x, int y, int width) {
        return ((SimpleOption<Double>) instance).createWidget(options, x, y, width);
    }

    public static SimpleOption<?> init(GameOptions options, SoundCategory category) {
        return options.getSoundVolumeOption(category);
    }
}
