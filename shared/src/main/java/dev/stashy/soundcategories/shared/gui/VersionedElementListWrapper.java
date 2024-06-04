package dev.stashy.soundcategories.shared.gui;

import dev.stashy.soundcategories.shared.SoundCategories;
import me.lonefelidae16.groominglib.api.McVersionInterchange;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.sound.SoundCategory;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public interface VersionedElementListWrapper {
    static VersionedElementListWrapper newInstance(MinecraftClient client, int width, int height, int top, int bottom, int itemHeight) {
        try {
            Class<VersionedElementListWrapper> clazz = McVersionInterchange.getCompatibleClass(SoundCategories.BASE_PACKAGE,"gui.SoundList");
            Method init = clazz.getMethod("init", MinecraftClient.class, int.class, int.class, int.class, int.class, int.class);
            return (VersionedElementListWrapper) init.invoke(null, client, width, height, top, bottom, itemHeight);
        } catch (Exception ex) {
            SoundCategories.LOGGER.error("Cannot instantiate 'SoundList'", ex);
        }
        return null;
    }

    default int addSingleOptionEntry(SimpleOption<?> option) {
        return this.addSingleOptionEntry(option, true);
    }

    int addSingleOptionEntry(SimpleOption<?> option, boolean editable);

    int addOptionEntry(SimpleOption<?> firstOption, @Nullable SimpleOption<?> secondOption);

    default void addAll(SimpleOption<?>[] options) {
        for (int i = 0; i < options.length; i += 2) {
            this.addOptionEntry(options[i], i < options.length - 1 ? options[i + 1] : null);
        }
    }

    int addCategory(SoundCategory cat);

    int addReadOnlyCategory(SoundCategory cat);

    int addDoubleCategory(SoundCategory first, @Nullable SoundCategory second);

    void addAllCategory(SoundCategory[] categories);

    int addGroup(SoundCategory group, ButtonWidget.PressAction pressAction);

    void setDimensionsImpl(int width, int height);

    boolean mouseScrolledImpl(double mouseX, double mouseY, double horizontalAmount, double verticalAmount);

    static SimpleOption<?> createCustomizedOption(MinecraftClient client, SoundCategory category) {
        final SimpleOption<Double> simpleOption = client.options.getSoundVolumeOption(category);
        if (SoundCategories.TOGGLEABLE_CATS.getOrDefault(category, false)) {
            return SimpleOption.ofBoolean(simpleOption.toString(), value -> {
                return Tooltip.of(SoundCategories.TOOLTIPS.getOrDefault(category, ScreenTexts.EMPTY));
            }, simpleOption.getValue() > 0, value -> {
                simpleOption.setValue(value ? 1.0 : 0.0);
            });
        }
        return simpleOption;
    }

    @Environment(EnvType.CLIENT)
    abstract class VersionedSoundEntry extends ElementListWidget.Entry<VersionedSoundEntry> {
        public List<? extends ClickableWidget> widgets;

        public VersionedSoundEntry(List<? extends ClickableWidget> w) {
            widgets = w;
        }

        public static VersionedSoundEntry newInstance(List<? extends ClickableWidget> w) {
            try {
                Class<VersionedSoundEntry> screen = McVersionInterchange.getCompatibleClass(SoundCategories.BASE_PACKAGE, "gui.SoundEntry");
                Constructor<VersionedSoundEntry> constructor = screen.getConstructor(List.class);
                return constructor.newInstance(w);
            } catch (Exception ex) {
                SoundCategories.LOGGER.error("Cannot instantiate 'SoundEntry'", ex);
            }
            return null;
        }

        public static VersionedSoundEntry create(GameOptions options, int width, SimpleOption<?> simpleOption) {
            return VersionedSoundEntry.newInstance(
                    List.of(Objects.requireNonNull(
                            VersionedSimpleOptionProvider.createWidget(simpleOption, options, width / 2 - 155, 0, 310)
                    ))
            );
        }

        public static VersionedSoundEntry createDouble(GameOptions options, int width, SimpleOption<?> first, @Nullable SimpleOption<?> second) {
            List<ClickableWidget> widgets = new ArrayList<>();
            widgets.add(VersionedSimpleOptionProvider.createWidget(first, options, width / 2 - 155, 0, 150));
            if (second != null) {
                widgets.add(VersionedSimpleOptionProvider.createWidget(second, options, width / 2 + 5, 0, 150));
            }
            return VersionedSoundEntry.newInstance(widgets);
        }

        public static VersionedSoundEntry createGroup(GameOptions options, SimpleOption<?> group, int width, ButtonWidget.PressAction pressAction) {
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
