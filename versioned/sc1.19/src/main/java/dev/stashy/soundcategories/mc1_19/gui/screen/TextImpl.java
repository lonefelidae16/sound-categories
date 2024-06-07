package dev.stashy.soundcategories.mc1_19.gui.screen;

import dev.stashy.soundcategories.shared.gui.screen.VersionedText;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public class TextImpl extends VersionedText {
    public Text getDoneText() {
        return ScreenTexts.DONE;
    }

    public Text getCancelText() {
        return ScreenTexts.CANCEL;
    }

    public Text translatable(String key) {
        return Text.translatable(key);
    }
}
