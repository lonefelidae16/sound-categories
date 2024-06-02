package dev.stashy.soundcategories.mc1_20_5.gui;

import dev.stashy.soundcategories.shared.gui.VersionedSoundList;
import net.minecraft.client.MinecraftClient;

public class SoundList extends VersionedSoundList {
    public SoundList(MinecraftClient minecraftClient, int i, int j, int k, int l) {
        super(minecraftClient, i, j, k, l);
    }

    @Override
    public int getRowWidth() {
        return 310;
    }
}
