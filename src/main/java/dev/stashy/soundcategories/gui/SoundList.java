package dev.stashy.soundcategories.gui;

import dev.stashy.soundcategories.SoundCategories;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.*;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.sound.SoundCategory;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SoundList extends ElementListWidget<SoundList.SoundEntry> {

    public SoundList(MinecraftClient minecraftClient, int i, int j, int k, int l, int m)
    {
        super(minecraftClient, i, j, k, l, m);
        this.centerListVertically = false;
    }

    public int addCategory(SoundCategory cat)
    {
        return super.addEntry(SoundEntry.create(cat, this.width));
    }

    public int addDoubleCategory(SoundCategory first, SoundCategory second)
    {
        return super.addEntry(SoundEntry.createDouble(first, second, this.width));
    }

    public int addSingleOptionEntry(SimpleOption<?> option)
    {
        return super.addEntry(SoundEntry.createOption(this.client.options, option, this.width));
    }

    public void addOptionEntry(SimpleOption<?> firstOption, @Nullable SimpleOption<?> secondOption) {
        this.addEntry(SoundEntry.createDoubleOption(this.client.options, firstOption, secondOption, this.width));
    }

    public int addGroup(SoundCategory group, ButtonWidget.PressAction pressAction)
    {
        return super.addEntry(SoundEntry.createGroup(group, this.width, pressAction));
    }

    public int getRowWidth()
    {
        return 400;
    }

    protected int getScrollbarPositionX()
    {
        return super.getScrollbarPositionX() + 32;
    }

    public void addAll(SimpleOption<?>[] options) {
        for (int i = 0; i < options.length; i += 2) {
            this.addOptionEntry(options[i], i < options.length - 1 ? options[i + 1] : null);
        }
    }

    @Environment(EnvType.CLIENT)
    protected static class SoundEntry extends ElementListWidget.Entry<SoundList.SoundEntry>
    {
        List<? extends ClickableWidget> widgets;

        public SoundEntry(List<? extends ClickableWidget> w)
        {
            widgets = w;
        }

        public static SoundEntry create(SoundCategory cat, int width)
        {
            return new SoundEntry(List.of(
                    new SoundSliderWidget(MinecraftClient.getInstance(), width / 2 - 155, 0, cat, 310)));
        }

        public static SoundEntry createDouble(SoundCategory first, @Nullable SoundCategory second, int width)
        {
            List<SoundSliderWidget> widgets = new ArrayList<>();
            widgets.add(new SoundSliderWidget(MinecraftClient.getInstance(), width / 2 - 155, 0, first, 150));
            if (second != null)
                widgets.add(new SoundSliderWidget(MinecraftClient.getInstance(), width / 2 + 5, 0, second, 150));
            return new SoundEntry(widgets);
        }

        public static SoundEntry createOption(GameOptions options, SimpleOption<?> option, int width)
        {
            var widget = option.createButton(options, width / 2 - 155, 0, 310);
            return new SoundEntry(List.of(widget));
        }

        public static SoundEntry createDoubleOption(GameOptions options, SimpleOption<?> first, @Nullable SimpleOption<?> second, int width)
        {
            List<ClickableWidget> widgets = new ArrayList<>();
            widgets.add(first.createButton(options, width / 2 - 155, 0, 150));
            if (second != null)
                widgets.add(second.createButton(options, width / 2 + 5, 0, 150));
            return new SoundEntry(widgets);
        }

        public static SoundEntry createGroup(SoundCategory group, int width, ButtonWidget.PressAction pressAction)
        {
            return new SoundEntry(
                    List.of(
                            new SoundSliderWidget(MinecraftClient.getInstance(), width / 2 - 155, 0, group, 285),
                            new TexturedButtonWidget(width / 2 + 135, 0, 20, 20, 0, 0, 20,
                                    SoundCategories.SETTINGS_ICON, 20, 40, pressAction)
                    ));
        }

        public List<? extends Element> children()
        {
            return this.widgets;
        }

        public List<? extends Selectable> selectableChildren()
        {
            return this.widgets;
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta)
        {
            this.widgets.forEach((s) -> {
                s.y = y;
                s.render(matrices, mouseX, mouseY, tickDelta);
            });
        }
    }
}
