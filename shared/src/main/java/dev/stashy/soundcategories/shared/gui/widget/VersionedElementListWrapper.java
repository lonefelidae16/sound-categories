package dev.stashy.soundcategories.shared.gui.widget;

import dev.stashy.soundcategories.shared.SoundCategories;
import dev.stashy.soundcategories.shared.gui.option.VersionedSimpleOptionProvider;
import me.lonefelidae16.groominglib.api.McVersionInterchange;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.sound.SoundCategory;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public interface VersionedElementListWrapper extends Drawable, Element, Selectable {
    String METHOD_KEY_INIT = VersionedElementListWrapper.class.getCanonicalName() + "#init";

    static VersionedElementListWrapper newInstance(MinecraftClient client, int width, int height, int top, int bottom, int itemHeight) {
        Method init = SoundCategories.CACHED_METHOD_MAP.getOrDefault(METHOD_KEY_INIT, null);

        if (init == null) {
            try {
                Class<?> clazz = McVersionInterchange.getCompatibleClass(SoundCategories.BASE_PACKAGE, "gui.widget.SoundList");
                init = Objects.requireNonNull(clazz).getMethod("init", MinecraftClient.class, int.class, int.class, int.class, int.class, int.class);
                SoundCategories.CACHED_METHOD_MAP.put(METHOD_KEY_INIT, Objects.requireNonNull(init));
            } catch (Exception ex) {
                SoundCategories.LOGGER.error("Failed to init 'SoundList' class.", ex);
            }
        }

        try {
            return (VersionedElementListWrapper) Objects.requireNonNull(init).invoke(null, client, width, height, top, bottom, itemHeight);
        } catch (Exception ex) {
            SoundCategories.LOGGER.error("Cannot instantiate 'SoundList'", ex);
        }

        return null;
    }

    default int addSingleOptionEntry(Object option) {
        return this.addSingleOptionEntry(option, true);
    }

    int addSingleOptionEntry(Object option, boolean editable);

    int addOptionEntry(Object firstOption, @Nullable Object secondOption);

    default void addAll(Object[] options) {
        for (int i = 0; i < options.length; i += 2) {
            this.addOptionEntry(options[i], i < options.length - 1 ? options[i + 1] : null);
        }
    }

    int addCategory(SoundCategory cat);

    int addReadOnlyCategory(SoundCategory cat);

    void addAllCategory(SoundCategory[] categories);

    int addGroup(SoundCategory group, ButtonWidget.PressAction pressAction);

    void setDimensionsImpl(int width, int height);

    boolean mouseScrolledImpl(double mouseX, double mouseY, double horizontalAmount, double verticalAmount);

    void addDrawable(ClickableWidget button);

    @Environment(EnvType.CLIENT)
    abstract class VersionedSoundEntry extends ElementListWidget.Entry<VersionedSoundEntry> {
        private static final String METHOD_SIGN_CONSTR = VersionedSoundEntry.class.getCanonicalName() + "#<init>";

        public List<? extends ClickableWidget> widgets;

        static {
            try {
                Class<VersionedSoundEntry> entry = McVersionInterchange.getCompatibleClass(SoundCategories.BASE_PACKAGE, "gui.widget.SoundEntry");
                Constructor<VersionedSoundEntry> constructor = entry.getConstructor(List.class);
                SoundCategories.CACHED_INIT_MAP.put(METHOD_SIGN_CONSTR, Objects.requireNonNull(constructor));
            } catch (Exception ex) {
                SoundCategories.LOGGER.error("Failed to init 'SoundEntry' class.", ex);
            }
        }

        public VersionedSoundEntry(List<? extends ClickableWidget> w) {
            this.widgets = w;
        }

        public static VersionedSoundEntry newInstance(List<? extends ClickableWidget> w) {
            try {
                Constructor<VersionedSoundEntry> constructor = (Constructor<VersionedSoundEntry>) SoundCategories.CACHED_INIT_MAP.get(METHOD_SIGN_CONSTR);
                return constructor.newInstance(w);
            } catch (Exception ex) {
                SoundCategories.LOGGER.error("Cannot instantiate 'SoundEntry'", ex);
            }
            return null;
        }

        public static VersionedSoundEntry create(GameOptions options, int width, Object option) {
            return VersionedSoundEntry.newInstance(
                    List.of(Objects.requireNonNull(
                            VersionedSimpleOptionProvider.createWidget(option, options, width / 2 - 155, 0, 310)
                    ))
            );
        }

        public static VersionedSoundEntry createDouble(GameOptions options, int width, Object first, @Nullable Object second) {
            List<ClickableWidget> widgets = new ArrayList<>();
            widgets.add(VersionedSimpleOptionProvider.createWidget(first, options, width / 2 - 155, 0, 150));
            if (second != null) {
                widgets.add(VersionedSimpleOptionProvider.createWidget(second, options, width / 2 + 5, 0, 150));
            }
            return VersionedSoundEntry.newInstance(widgets);
        }

        public static VersionedSoundEntry createGroup(GameOptions options, Object group, int width, ButtonWidget.PressAction pressAction) {
            return VersionedSoundEntry.newInstance(
                    List.of(
                            Objects.requireNonNull(VersionedSimpleOptionProvider.createWidget(group, options, width / 2 - 155, 0, 280)),
                            (TexturedButtonWidget) Objects.requireNonNull(
                                    VersionedTexturedButtonWrapper.newInstance(width / 2 + 135, 0, 20, 20, 0, 0, 20,
                                            20, 40, pressAction)
                            )
                    ));
        }

        public List<? extends Element> children() {
            return this.widgets;
        }

        public List<? extends Selectable> selectableChildren() {
            return this.widgets;
        }
    }
}
