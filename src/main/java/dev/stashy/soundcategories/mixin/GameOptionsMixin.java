package dev.stashy.soundcategories.mixin;

import dev.stashy.soundcategories.SoundCategories;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import net.minecraft.client.option.GameOptions;
import net.minecraft.sound.SoundCategory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(GameOptions.class)
public abstract class GameOptionsMixin
{
    @Shadow
    @Final
    private Object2FloatMap<SoundCategory> soundVolumeLevels;

    @Inject(method = "accept", at = @At(value = "HEAD"))
    private void soundcategories$modifyDefaultVol(CallbackInfo ci)
    {
        for (Map.Entry<SoundCategory, Float> entry : SoundCategories.defaultLevels.entrySet()) {
            soundVolumeLevels.putIfAbsent(entry.getKey(), entry.getValue().floatValue());
        }
    }
}
