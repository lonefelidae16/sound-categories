package dev.stashy.soundcategories.mc1_18.gui.widget;

import dev.stashy.soundcategories.shared.SoundCategories;
import dev.stashy.soundcategories.shared.gui.widget.VersionedElementListWrapper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.option.CyclingOption;
import net.minecraft.client.option.DoubleOption;
import net.minecraft.client.option.Option;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class SoundList extends ElementListWidget<VersionedElementListWrapper.VersionedSoundEntry> implements VersionedElementListWrapper {
    public SoundList(MinecraftClient minecraftClient, int i, int j, int k, int l, int m) {
        super(minecraftClient, i, j, k, l, m);
        this.centerListVertically = false;
    }

    public static SoundList init(MinecraftClient client, int width, int height, int top, int bottom, int itemHeight) {
        return new SoundList(client, width, height, top, bottom, itemHeight);
    }

    @Override
    protected int getScrollbarPositionX() {
        return super.getScrollbarPositionX() + 32;
    }

    @Override
    public int getRowWidth() {
        return 400;
    }

    @Override
    public int addSingleOptionEntry(Object option, boolean editable) {
        var entry = VersionedSoundEntry.create(this.client.options, this.width, option);
        if (!editable) {
            entry.widgets.forEach(widget -> widget.active = false);
        }
        return this.addEntry(entry);
    }

    @Override
    public int addOptionEntry(Object firstOption, @Nullable Object secondOption) {
        // TODO: Here is already "Versioned" package; Is it necessary using reflection?
        return this.addEntry(VersionedSoundEntry.createDouble(this.client.options, this.width, firstOption, secondOption));
    }

    @Override
    public int addCategory(SoundCategory cat) {
        return this.addSingleOptionEntry(this.createCustomizedOption(cat));
    }

    @Override
    public int addReadOnlyCategory(SoundCategory cat) {
        return this.addSingleOptionEntry(this.createCustomizedOption(cat), false);
    }

    @Override
    public void addAllCategory(SoundCategory[] categories) {
        this.addAll(Arrays.stream(categories).map(this::createCustomizedOption).toArray());
    }

    @Override
    public int addGroup(SoundCategory group, ButtonWidget.PressAction pressAction) {
        return super.addEntry(VersionedSoundEntry.createGroup(this.client.options, this.createCustomizedOption(group), this.width, pressAction));
    }

    @Override
    public void setDimensionsImpl(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean mouseScrolledImpl(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        return this.mouseScrolled(mouseX, mouseY, verticalAmount);
    }

    private Option createCustomizedOption(SoundCategory category) {
        final DoubleOption option = new DoubleOption(SoundCategories.getOptionsTranslationKey(category), 0, 1, 0,
                gameOptions -> (double) gameOptions.getSoundVolume(category),
                (gameOptions, value) -> gameOptions.setSoundVolume(category, value.floatValue()),
                (gameOptions, doubleOption) -> {
                    double value = doubleOption.get(gameOptions);
                    if (value == 0.) {
                        return doubleOption.getGenericLabel(ScreenTexts.OFF);
                    } else {
                        return doubleOption.getPercentLabel(value);
                    }
                });
        if (SoundCategories.TOGGLEABLE_CATS.getOrDefault(category, false)) {
            return CyclingOption.create(SoundCategories.getOptionsTranslationKey(category),
                    SoundCategories.TOOLTIPS.getOrDefault(category, Text.of("")), gameOptions -> {
                        return gameOptions.getSoundVolume(category) > 0;
                    },
                    (gameOptions, o, v) -> MinecraftClient.getInstance().options.setSoundVolume(category, v ? 1.0f : 0.0f)
            );
        }
        return option;
    }

    @Override
    public void addDrawable(ClickableWidget button) {
        this.addEntry(new SoundEntry(List.of(button)));
    }
}
