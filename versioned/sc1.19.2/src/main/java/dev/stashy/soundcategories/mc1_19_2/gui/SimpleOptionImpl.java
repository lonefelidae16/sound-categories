package dev.stashy.soundcategories.mc1_19_2.gui;

import com.google.common.collect.Maps;
import dev.stashy.soundcategories.shared.SoundCategories;
import me.lonefelidae16.groominglib.Util;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.SoundSliderWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
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
            final var option = new SimpleOption<>(SoundCategories.OPTION_PREFIX_SOUND_CAT + cat.getName(), getter, (prefix, value) -> {
                return value == 0.0 ? GameOptions.getGenericValueText(prefix, ScreenTexts.OFF) : GameOptions.getPercentValueText(prefix, value);
            }, SimpleOption.DoubleSliderCallbacks.INSTANCE, 1., (value) -> {
                MinecraftClient.getInstance().getSoundManager().updateSoundVolume(cat, value.floatValue());
            });
            map.put(cat, option);
        }
    });

    private static final Map<String, SoundCategory> KEY_CAT_MAP = Maps.newHashMap();

    public static ClickableWidget createWidget(SimpleOption<?> instance, GameOptions options, int x, int y, int width) {
        return Optional.ofNullable(KEY_CAT_MAP.getOrDefault(instance.toString(), null))
                .map(cat -> {
                    return SoundCategories.TOGGLEABLE_CATS.containsKey(cat) ?
                            SimpleOption.ofBoolean(
                                    SoundCategories.OPTION_PREFIX_SOUND_CAT + cat.getName(),
                                    options.getSoundVolume(cat) > 0, value -> {
                                        MinecraftClient.getInstance().options.setSoundVolume(cat, value ? 1.f : 0.f);
                                    }
                            ).createButton(options, x, y, width) :
                            new SoundSliderWidget(MinecraftClient.getInstance(), x, y, cat, width);
                }).orElse(instance.createButton(options, x, y, width));
    }

    public static SimpleOption<?> init(GameOptions options, SoundCategory category) {
        var ret = Objects.requireNonNull(VOLUME_OPTS.get(category));
        KEY_CAT_MAP.putIfAbsent(ret.toString(), category);
        return ret;
    }

    public static SimpleOption<Boolean> ofBoolean(String key, Text tooltip, boolean value, Consumer<Boolean> consumer) {
        return SimpleOption.ofBoolean(key, tooltip.equals(Text.empty()) ? SimpleOption.emptyTooltip() : SimpleOption.constantTooltip(tooltip), value, consumer);
    }
}
