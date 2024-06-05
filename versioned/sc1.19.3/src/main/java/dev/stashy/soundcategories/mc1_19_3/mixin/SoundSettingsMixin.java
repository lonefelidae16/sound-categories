package dev.stashy.soundcategories.mc1_19_3.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.stashy.soundcategories.shared.gui.VersionedSoundOptionsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.option.GameOptions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(OptionsScreen.class)
public class SoundSettingsMixin {
    @Shadow
    private @Final GameOptions settings;

    @ModifyReturnValue(method = "method_19829", at = @At("RETURN"))
    private Screen soundcategories$redirectToCustomScreen(Screen original) {
        return VersionedSoundOptionsScreen.newInstance(OptionsScreen.class.cast(this), settings);
    }
}
