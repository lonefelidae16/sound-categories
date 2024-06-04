package dev.stashy.soundcategories.shared.gui;

import dev.stashy.soundcategories.shared.SoundCategories;
import me.lonefelidae16.groominglib.api.McVersionInterchange;
import net.minecraft.client.gui.widget.ButtonWidget;

import java.lang.reflect.Method;

public interface VersionedTexturedButtonWrapper {
    static VersionedTexturedButtonWrapper newInstance(int x, int y, int width, int height, int u, int v, int hoveredVOffset, int textureWidth, int textureHeight, ButtonWidget.PressAction pressAction) {
        try {
            Class<VersionedTexturedButtonWrapper> clazz = McVersionInterchange.getCompatibleClass(SoundCategories.BASE_PACKAGE,"gui.TexturedButtonWidgetImpl");
            Method init = clazz.getMethod("init", int.class, int.class, int.class, int.class, int.class, int.class, int.class, int.class, int.class, ButtonWidget.PressAction.class);
            return (VersionedTexturedButtonWrapper) init.invoke(null, x, y, width, height, u, v, hoveredVOffset, textureWidth, textureHeight, pressAction);
        } catch (Exception ex) {
            SoundCategories.LOGGER.error("Cannot instantiate 'TexturedButtonWidget'", ex);
        }
        return null;
    }
}
