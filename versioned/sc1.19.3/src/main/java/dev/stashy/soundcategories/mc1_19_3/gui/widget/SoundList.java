package dev.stashy.soundcategories.mc1_19_3.gui.widget;

import dev.stashy.soundcategories.shared.SoundCategories;
import dev.stashy.soundcategories.shared.gui.option.VersionedSimpleOptionProvider;
import dev.stashy.soundcategories.shared.gui.widget.VersionedElementListWrapper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
        return this.addEntry(VersionedSoundEntry.createDouble(this.client.options, this.width, firstOption, secondOption));
    }

    @Override
    public int addCategory(SoundCategory cat) {
        return this.addSingleOptionEntry(createCustomizedOption(this.client, cat));
    }

    @Override
    public int addReadOnlyCategory(SoundCategory cat) {
        return this.addSingleOptionEntry(createCustomizedOption(this.client, cat), false);
    }

    @Override
    public int addDoubleCategory(SoundCategory first, @Nullable SoundCategory second) {
        return this.addOptionEntry(createCustomizedOption(this.client, first),
                (second != null) ? createCustomizedOption(this.client, second) : null
        );
    }

    @Override
    public void addAllCategory(SoundCategory[] categories) {
        this.addAll(Arrays.stream(categories).map(cat -> createCustomizedOption(this.client, cat)).toArray(SimpleOption[]::new));
    }

    @Override
    public int addGroup(SoundCategory group, ButtonWidget.PressAction pressAction) {
        return super.addEntry(VersionedSoundEntry.createGroup(this.client.options, createCustomizedOption(this.client, group), this.width, pressAction));
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

    @Override
    public Object createCustomizedOption(MinecraftClient client, SoundCategory category) {
        final Object option = Objects.requireNonNull(VersionedSimpleOptionProvider.newInstance(client.options, category));
        if (SoundCategories.TOGGLEABLE_CATS.getOrDefault(category, false)) {
            if (option instanceof SimpleOption) {
                SimpleOption<Double> simpleOption = (SimpleOption<Double>) option;
                return VersionedSimpleOptionProvider.ofBoolean(
                        option.toString(),
                        SoundCategories.TOOLTIPS.getOrDefault(category, Text.of("")),
                        simpleOption.getValue() > 0,
                        value -> {
                            simpleOption.setValue(value ? 1.0 : 0.0);
                        }
                );
            }
        }
        return option;
    }

    @Override
    public void addDrawable(ClickableWidget button) {
        this.addEntry(VersionedSoundEntry.newInstance(List.of(button)));
    }
}
