package dev.stashy.soundcategories.mc1_19_4.gui;

import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;

import java.util.function.Consumer;

public class SimpleOptionImpl {
    public static ClickableWidget createWidget(SimpleOption<?> instance, GameOptions options, int x, int y, int width) {
        return instance.createWidget(options, x, y, width);
    }

    public static SimpleOption<?> init(GameOptions options, SoundCategory category) {
        return options.getSoundVolumeOption(category);
    }

    public static SimpleOption<Boolean> ofBoolean(String key, Text tooltip, boolean value, Consumer<Boolean> consumer) {
        return SimpleOption.ofBoolean(key, tooltip.equals(Text.EMPTY) ? SimpleOption.emptyTooltip() : SimpleOption.constantTooltip(tooltip), value, consumer);
    }
}
