package dev.stashy.soundcategories.mc1_19.gui.option;

import dev.stashy.soundcategories.shared.SoundCategories;
import me.lonefelidae16.groominglib.Util;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;

import java.util.EnumMap;
import java.util.Objects;
import java.util.function.Consumer;

public class SimpleOptionImpl {
    private static final EnumMap<SoundCategory, SimpleOption<Double>> VOLUME_OPTS = Util.make(new EnumMap<>(SoundCategory.class), map -> {
        for (var cat : SoundCategory.values()) {
            final SimpleOption.TooltipFactoryGetter<Double> getter;
            if (SoundCategories.TOOLTIPS.containsKey(cat)) {
                getter = SimpleOption.constantTooltip(SoundCategories.TOOLTIPS.get(cat));
            } else {
                getter = SimpleOption.emptyTooltip();
            }
            final var option = new SimpleOption<>(SoundCategories.getOptionsTranslationKey(cat), getter, (prefix, value) -> {
                return value == 0.0 ? GameOptions.getGenericValueText(prefix, ScreenTexts.OFF) : GameOptions.getPercentValueText(prefix, value);
            }, SimpleOption.DoubleSliderCallbacks.INSTANCE, (double) MinecraftClient.getInstance().options.getSoundVolume(cat), (value) -> {
                var client = MinecraftClient.getInstance();
                client.getSoundManager().updateSoundVolume(cat, value.floatValue());
                client.options.setSoundVolume(cat, value.floatValue());
            });
            map.put(cat, option);
        }
    });

    public static ClickableWidget createWidget(Object instance, GameOptions options, int x, int y, int width) {
        return ((SimpleOption<Double>) instance).createButton(options, x, y, width);
    }

    public static SimpleOption<?> init(GameOptions options, SoundCategory category) {
        return Objects.requireNonNull(VOLUME_OPTS.get(category));
    }

    public static SimpleOption<Boolean> ofBoolean(String key, Text tooltip, boolean value, Consumer<Boolean> consumer) {
        return SimpleOption.ofBoolean(key, tooltip.equals(Text.empty()) ? SimpleOption.emptyTooltip() : SimpleOption.constantTooltip(tooltip), value, consumer);
    }
}
