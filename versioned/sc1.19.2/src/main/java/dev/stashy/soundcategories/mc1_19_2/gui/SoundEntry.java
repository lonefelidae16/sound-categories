package dev.stashy.soundcategories.mc1_19_2.gui;

import dev.stashy.soundcategories.shared.gui.VersionedElementListWrapper;
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
        int i = 0;
        int j = MinecraftClient.getInstance().getWindow().getScaledWidth() / 2 - 155;

        for (ClickableWidget s : this.widgets) {
            s.x = j + i;
            s.y = y;
            s.render(matrices, mouseX, mouseY, tickDelta);
            i += s.getWidth() + 10;
        }
    }
}
