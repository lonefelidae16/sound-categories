package dev.stashy.soundcategories.mc1_18.text;

import dev.stashy.soundcategories.shared.text.VersionedText;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class TextImpl extends VersionedText {
    @Override
    public Text empty() {
        return Text.of("");
    }

    @Override
    public Text getDoneText() {
        return ScreenTexts.DONE;
    }

    @Override
    public Text getCancelText() {
        return ScreenTexts.CANCEL;
    }

    @Override
    public Text translatable(String key) {
        return new TranslatableText(key);
    }
}
