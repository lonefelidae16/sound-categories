package dev.stashy.soundcategories.shared.gui;

import dev.stashy.soundcategories.shared.SoundCategories;
import me.lonefelidae16.groominglib.api.McVersionInterchange;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

import java.lang.reflect.Method;

public interface VersionedButtonWrapper {
    static VersionedButtonWrapper newInstance(int x, int y, int width, int height, Text message, ButtonWidget.PressAction callback) {
        return newInstance(x, y, width, height, message, callback, Text.empty());
    }

    static VersionedButtonWrapper newInstance(int x, int y, int width, int height, Text message, ButtonWidget.PressAction callback, Text tooltipContent) {
        try {
            Class<VersionedButtonWrapper> clazz = McVersionInterchange.getCompatibleClass(SoundCategories.BASE_PACKAGE,"gui.ButtonWidgetImpl");
            Method init = clazz.getMethod("init", int.class, int.class, int.class, int.class, Text.class, ButtonWidget.PressAction.class, Text.class);
            return (VersionedButtonWrapper) init.invoke(null, x, y, width, height, message, callback, tooltipContent);
        } catch (Exception ex) {
            SoundCategories.LOGGER.error("Cannot instantiate 'ButtonWidget'", ex);
        }
        return null;
    }
}
