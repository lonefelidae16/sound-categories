package dev.stashy.soundcategories.mc1_18.mixin;

import dev.stashy.soundcategories.shared.gui.screen.VersionedSoundOptionsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OptionsScreen.class)
public class SoundSettingsMixin extends Screen {
    @Shadow
    private @Final GameOptions settings;

    protected SoundSettingsMixin(Text title) {
        super(title);
    }

    @Inject(method = "method_19829", at = @At("HEAD"), cancellable = true)
    private void soundcategories$redirectToCustomScreen(ButtonWidget instance, CallbackInfo ci) {
        this.client.setScreen(VersionedSoundOptionsScreen.newInstance(OptionsScreen.class.cast(this), settings));
        ci.cancel();
    }
}
