package dev.stashy.soundcategories.mc1_20.gui;

import dev.stashy.soundcategories.shared.gui.VersionedElementListWrapper;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ClickableWidget;

import java.util.List;

public class SoundEntry extends VersionedElementListWrapper.VersionedSoundEntry {
    public SoundEntry(List<? extends ClickableWidget> w) {
        super(w);
    }

    @Override
    public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        int i = 0;
        int j = context.getScaledWindowWidth() / 2 - 155;

        for (ClickableWidget s : this.widgets) {
            s.setPosition(j + i, y);
            s.render(context, mouseX, mouseY, tickDelta);
            i += s.getWidth() + 10;
        }
    }
}
