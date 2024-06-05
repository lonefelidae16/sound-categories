package dev.stashy.soundcategories.mc1_19_3.gui;

import dev.stashy.soundcategories.shared.gui.VersionedButtonWrapper;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.function.Supplier;

public class ButtonWidgetImpl extends ButtonWidget implements VersionedButtonWrapper {
    protected ButtonWidgetImpl(int x, int y, int width, int height, Text message, PressAction onPress, NarrationSupplier narrationSupplier) {
        super(x, y, width, height, message, onPress, narrationSupplier);
    }

    public static ButtonWidgetImpl init(int x, int y, int width, int height, Text message, ButtonWidget.PressAction callback, Text tooltipContent) {
        var result = new ButtonWidgetImpl(x, y, width, height, message, callback, ButtonWidget.DEFAULT_NARRATION_SUPPLIER);
        result.setTooltip(Tooltip.of(tooltipContent));
        return result;
    }
}
