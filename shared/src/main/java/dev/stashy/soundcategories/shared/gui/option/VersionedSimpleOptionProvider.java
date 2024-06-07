package dev.stashy.soundcategories.shared.gui.option;

import dev.stashy.soundcategories.shared.SoundCategories;
import me.lonefelidae16.groominglib.api.McVersionInterchange;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class VersionedSimpleOptionProvider {
    private static final String METHOD_SIGN_CREATE_WIDGET = VersionedSimpleOptionProvider.class.getCanonicalName() + "#createWidget";
    private static final String METHOD_SIGN_INIT = VersionedSimpleOptionProvider.class.getCanonicalName() + "#init";
    private static final String METHOD_SIGN_OF_BOOLEAN = VersionedSimpleOptionProvider.class.getCanonicalName() + "#ofBoolean";

    static {
        try {
            Class<?> clazz = McVersionInterchange.getCompatibleClass(SoundCategories.BASE_PACKAGE, "gui.option.SimpleOptionImpl");

            {
                Method createWidget = clazz.getMethod("createWidget", Object.class, GameOptions.class, int.class, int.class, int.class);
                SoundCategories.CACHED_METHOD_MAP.put(METHOD_SIGN_CREATE_WIDGET, Objects.requireNonNull(createWidget));
            }
            {
                Method init = clazz.getMethod("init", GameOptions.class, SoundCategory.class);
                SoundCategories.CACHED_METHOD_MAP.put(METHOD_SIGN_INIT, Objects.requireNonNull(init));
            }
            {
                Method ofBoolean = clazz.getMethod("ofBoolean", String.class, Text.class, boolean.class, Consumer.class);
                SoundCategories.CACHED_METHOD_MAP.put(METHOD_SIGN_OF_BOOLEAN, Objects.requireNonNull(ofBoolean));
            }
        } catch (Exception ex) {
            SoundCategories.LOGGER.error("Failed to init 'OptionImpl' class.", ex);
        }
    }

    public static ClickableWidget createWidget(Object instance, GameOptions options, int x, int y, int width) {
        try {
            Method method = SoundCategories.CACHED_METHOD_MAP.get(METHOD_SIGN_CREATE_WIDGET);
            return (ClickableWidget) method.invoke(null, instance, options, x, y, width);
        } catch (Exception ex) {
            SoundCategories.LOGGER.error("Cannot instantiate 'Option' widget", ex);
        }
        return null;
    }

    public static Object newInstance(GameOptions options, SoundCategory category) {
        try {
            Method method = SoundCategories.CACHED_METHOD_MAP.get(METHOD_SIGN_INIT);
            return method.invoke(null, options, category);
        } catch (Exception ex) {
            SoundCategories.LOGGER.error("Cannot instantiate 'Option'", ex);
        }
        return null;
    }

    public static Object ofBoolean(String key, Text tooltip, boolean value, Consumer<Boolean> consumer) {
        try {
            Method method = SoundCategories.CACHED_METHOD_MAP.get(METHOD_SIGN_OF_BOOLEAN);
            return method.invoke(null, key, tooltip, value, consumer);
        } catch (Exception ex) {
            SoundCategories.LOGGER.error("Cannot instantiate Boolean option.", ex);
        }
        return null;
    }
}
