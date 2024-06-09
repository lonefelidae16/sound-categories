package dev.stashy.soundcategories.shared.gui.option;

import dev.stashy.soundcategories.shared.SoundCategories;
import me.lonefelidae16.groominglib.api.McVersionInterchange;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.sound.SoundCategory;

import java.lang.reflect.Method;
import java.util.Objects;

public abstract class VersionedSimpleOptionProvider {
    private static final String METHOD_KEY_CREATE_WIDGET = VersionedSimpleOptionProvider.class.getCanonicalName() + "#createWidget";
    private static final String METHOD_KEY_INIT = VersionedSimpleOptionProvider.class.getCanonicalName() + "#init";

    static {
        try {
            Class<?> clazz = McVersionInterchange.getCompatibleClass(SoundCategories.BASE_PACKAGE, "option.SimpleOptionImpl");

            {
                Method createWidget = clazz.getMethod("createWidget", Object.class, GameOptions.class, int.class, int.class, int.class);
                SoundCategories.CACHED_METHOD_MAP.put(METHOD_KEY_CREATE_WIDGET, Objects.requireNonNull(createWidget));
            }
            {
                Method init = clazz.getMethod("init", GameOptions.class, SoundCategory.class);
                SoundCategories.CACHED_METHOD_MAP.put(METHOD_KEY_INIT, Objects.requireNonNull(init));
            }
        } catch (Exception ex) {
            SoundCategories.LOGGER.error("Failed to init 'OptionImpl' class.", ex);
        }
    }

    public static ClickableWidget createWidget(Object instance, GameOptions options, int x, int y, int width) {
        try {
            Method method = SoundCategories.CACHED_METHOD_MAP.get(METHOD_KEY_CREATE_WIDGET);
            return (ClickableWidget) method.invoke(null, instance, options, x, y, width);
        } catch (Exception ex) {
            SoundCategories.LOGGER.error("Cannot instantiate 'Option' widget", ex);
        }
        return null;
    }

    public static Object newInstance(GameOptions options, SoundCategory category) {
        try {
            Method method = SoundCategories.CACHED_METHOD_MAP.get(METHOD_KEY_INIT);
            return method.invoke(null, options, category);
        } catch (Exception ex) {
            SoundCategories.LOGGER.error("Cannot instantiate 'Option'", ex);
        }
        return null;
    }
}
