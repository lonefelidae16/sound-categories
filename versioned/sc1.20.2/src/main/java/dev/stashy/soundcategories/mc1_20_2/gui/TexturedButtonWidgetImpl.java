package dev.stashy.soundcategories.mc1_20_2.gui;

import dev.stashy.soundcategories.shared.SoundCategories;
import dev.stashy.soundcategories.shared.gui.VersionedTexturedButtonWrapper;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.util.Identifier;

public class TexturedButtonWidgetImpl extends TexturedButtonWidget implements VersionedTexturedButtonWrapper {
    public TexturedButtonWidgetImpl(int x, int y, int width, int height, ButtonTextures textures, PressAction pressAction) {
        super(x, y, width, height, textures, pressAction);
    }

    public static TexturedButtonWidgetImpl init(int x, int y, int width, int height, int u, int v, int hoveredVOffset, int textureWidth, int textureHeight, PressAction pressAction) {
        ButtonTextures textures = new ButtonTextures(
                Identifier.of(SoundCategories.MOD_ID, "settings/button"),
                Identifier.of(SoundCategories.MOD_ID, "settings/disabled"),
                Identifier.of(SoundCategories.MOD_ID, "settings/hover"),
                Identifier.of(SoundCategories.MOD_ID, "settings/disabled")
        );
        return new TexturedButtonWidgetImpl(x, y, width, height, textures, pressAction);
    }
}
