package dev.stashy.soundcategories.shared.gui;

import dev.stashy.soundcategories.shared.SoundCategories;
import me.lonefelidae16.groominglib.api.McVersionInterchange;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;

import java.lang.reflect.Method;

public abstract class VersionedSimpleOptionProvider {
    public static ClickableWidget createWidget(SimpleOption<?> instance, GameOptions options, int x, int y, int width) {
        try {
            Class<VersionedSimpleOptionProvider> clazz = McVersionInterchange.getCompatibleClass(SoundCategories.BASE_PACKAGE, "gui.SimpleOptionImpl");
            Method method = clazz.getMethod("init", SimpleOption.class, GameOptions.class, int.class, int.class, int.class);
            return (ClickableWidget) method.invoke(null, instance, options, x, y, width);
        } catch (Exception ex) {
            SoundCategories.LOGGER.error("Cannot instantiate 'SimpleOption'", ex);
        }
        return null;
    }
}
