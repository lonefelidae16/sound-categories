package dev.stashy.soundcategories.mc1_19_3.gui;

import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;

public class SimpleOptionImpl {
    public static ClickableWidget init(SimpleOption<?> instance, GameOptions options, int x, int y, int width) {
        return instance.createButton(options, x, y, width);
    }
}
