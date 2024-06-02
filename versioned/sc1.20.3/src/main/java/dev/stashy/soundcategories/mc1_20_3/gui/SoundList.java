package dev.stashy.soundcategories.mc1_20_3.gui;

import dev.stashy.soundcategories.shared.gui.VersionedSoundList;
import net.minecraft.client.MinecraftClient;

public class SoundList extends VersionedSoundList {
    public SoundList(MinecraftClient minecraftClient, int i, int j, int k, int l) {
        super(minecraftClient, i, j, k, l);
    }

    @Override
    protected int getScrollbarPositionX() {
        return super.getScrollbarPositionX() + 32;
    }
}
