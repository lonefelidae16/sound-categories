package dev.stashy.soundcategories.mixin;

import dev.stashy.soundcategories.SoundCategories;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.sound.SoundCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameOptions.class)
public abstract class GameOptionsMixin {
    @Unique
    private SoundCategory currentCategory = null;

    /**
     * Stores the current SoundCategory in loop.
     */
    @Inject(method = "createSoundVolumeOption", at = @At("HEAD"))
    private void soundcategories$storeCategory(String key, SoundCategory soundCategory, CallbackInfoReturnable<SimpleOption<?>> cir) {
        this.currentCategory = soundCategory;
    }

    /**
     * Modifies a Constant of the default sound volume that exists in {@link SoundCategories#DEFAULT_LEVELS} and matches {@link GameOptionsMixin#currentCategory}.<br>
     * default value is 1.0.
     *
     * @see GameOptions#createSoundVolumeOption
     * @see GameOptions#GameOptions
     */
    @ModifyConstant(method = "createSoundVolumeOption", constant = @Constant(doubleValue = 1.0))
    private double soundcategories$changeDefault(double value) {
        if (this.currentCategory == null) {
            return value;
        }
        return SoundCategories.DEFAULT_LEVELS.getOrDefault(this.currentCategory, (float) value);
    }

    @Redirect(method = "createSoundVolumeOption", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/SimpleOption;emptyTooltip()Lnet/minecraft/client/option/SimpleOption$TooltipFactory;"), require = 0)
    private SimpleOption.TooltipFactory<?> soundcategories$modifyTooltip(String key, SoundCategory category) {
        if (SoundCategories.TOOLTIPS.containsKey(category)) {
            return value -> Tooltip.of(SoundCategories.TOOLTIPS.get(category));
        }
        return SimpleOption.emptyTooltip();
    }
}
