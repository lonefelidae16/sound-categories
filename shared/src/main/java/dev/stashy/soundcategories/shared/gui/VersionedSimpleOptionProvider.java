package dev.stashy.soundcategories.shared.gui;

import dev.stashy.soundcategories.shared.SoundCategories;
import me.lonefelidae16.groominglib.api.McVersionInterchange;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;

import java.lang.reflect.Method;
import java.util.function.Consumer;

public abstract class VersionedSimpleOptionProvider {
    private static final String TARGET_CLASS = "gui.SimpleOptionImpl";

    public static ClickableWidget createWidget(SimpleOption<?> instance, GameOptions options, int x, int y, int width) {
        try {
            Class<?> clazz = McVersionInterchange.getCompatibleClass(SoundCategories.BASE_PACKAGE, TARGET_CLASS);
            Method method = clazz.getMethod("createWidget", SimpleOption.class, GameOptions.class, int.class, int.class, int.class);
            return (ClickableWidget) method.invoke(null, instance, options, x, y, width);
        } catch (Exception ex) {
            SoundCategories.LOGGER.error("Cannot instantiate 'SimpleOption' widget", ex);
        }
        return null;
    }

    public static <T> SimpleOption<T> newInstance(GameOptions options, SoundCategory category) {
        try {
            Class<?> clazz = McVersionInterchange.getCompatibleClass(SoundCategories.BASE_PACKAGE, TARGET_CLASS);
            Method method = clazz.getMethod("init", GameOptions.class, SoundCategory.class);
            return (SimpleOption<T>) method.invoke(null, options, category);
        } catch (Exception ex) {
            SoundCategories.LOGGER.error("Cannot instantiate 'SimpleOption'", ex);
        }
        return null;
    }

    public static SimpleOption<Boolean> ofBoolean(String key, Text tooltip, boolean value, Consumer<Boolean> consumer) {
        try {
            Class<?> clazz = McVersionInterchange.getCompatibleClass(SoundCategories.BASE_PACKAGE, TARGET_CLASS);
            Method method = clazz.getMethod("ofBoolean", String.class, Text.class, boolean.class, Consumer.class);
            return (SimpleOption<Boolean>) method.invoke(null, key, tooltip, value, consumer);
        } catch (Exception ex) {
            SoundCategories.LOGGER.error("Cannot instantiate 'SimpleOption<Boolean>'", ex);
        }
        return null;
    }
}
