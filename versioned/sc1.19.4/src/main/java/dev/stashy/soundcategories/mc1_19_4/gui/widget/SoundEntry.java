package dev.stashy.soundcategories.mc1_19_4.gui.widget;

import dev.stashy.soundcategories.shared.gui.widget.VersionedElementListWrapper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;

import java.util.List;

public class SoundEntry extends VersionedElementListWrapper.VersionedSoundEntry {
    public SoundEntry(List<? extends ClickableWidget> w) {
        super(w);
    }

    @Override
    public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        if (this.widgets.isEmpty()) {
            return;
        }

        int i = 0;
        int j = this.widgets.get(0).getX();

        for (ClickableWidget s : this.widgets) {
            s.setPosition(j + i, y);
            s.render(matrices, mouseX, mouseY, tickDelta);
            i += s.getWidth() + 10;
        }
    }
}
