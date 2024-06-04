package dev.stashy.soundcategories.mc1_19_4.gui;

import dev.stashy.soundcategories.shared.gui.VersionedElementListWrapper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.sound.SoundCategory;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class SoundList_ extends ElementListWidget<VersionedElementListWrapper.VersionedSoundEntry> implements VersionedElementListWrapper {
    public SoundList_(MinecraftClient minecraftClient, int i, int j, int k, int l, int m) {
        super(minecraftClient, i, j, k, l, m);
        this.centerListVertically = false;
    }

    public static SoundList_ init(MinecraftClient client, int width, int height, int top, int bottom, int itemHeight) {
        return new SoundList_(client, width, height, top, bottom, itemHeight);
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
    public int addSingleOptionEntry(SimpleOption<?> option, boolean editable) {
        var entry = VersionedSoundEntry.create(this.client.options, this.width, option);
        if (!editable) {
            entry.widgets.forEach(widget -> widget.active = false);
        }
        return this.addEntry(entry);
    }

    @Override
    public int addOptionEntry(SimpleOption<?> firstOption, @Nullable SimpleOption<?> secondOption) {
        return this.addEntry(VersionedSoundEntry.createDouble(this.client.options, this.width, firstOption, secondOption));
    }

    @Override
    public int addCategory(SoundCategory cat) {
        return this.addSingleOptionEntry(VersionedElementListWrapper.createCustomizedOption(this.client, cat));
    }

    @Override
    public int addReadOnlyCategory(SoundCategory cat) {
        return this.addSingleOptionEntry(VersionedElementListWrapper.createCustomizedOption(this.client, cat), false);
    }

    @Override
    public int addDoubleCategory(SoundCategory first, @Nullable SoundCategory second) {
        return this.addOptionEntry(VersionedElementListWrapper.createCustomizedOption(this.client, first),
                (second != null) ? VersionedElementListWrapper.createCustomizedOption(this.client, second) : null
        );
    }

    @Override
    public void addAllCategory(SoundCategory[] categories) {
        this.addAll(Arrays.stream(categories).map(cat -> VersionedElementListWrapper.createCustomizedOption(this.client, cat)).toArray(SimpleOption[]::new));
    }

    @Override
    public int addGroup(SoundCategory group, ButtonWidget.PressAction pressAction) {
        return super.addEntry(VersionedSoundEntry.createGroup(this.client.options, VersionedElementListWrapper.createCustomizedOption(this.client, group), this.width, pressAction));
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
}
