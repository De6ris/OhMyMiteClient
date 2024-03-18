package com.github.Debris.oh_my_mite_client.mixins;

import com.github.Debris.oh_my_mite_client.config.FishConfig;
import com.github.Debris.oh_my_mite_client.config.TweakToggle;
import net.minecraft.PlayerCapabilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerCapabilities.class)
public class PlayerAbilitiesMixin {
    @Inject(method = "getFlySpeed", at = @At("HEAD"), cancellable = true)
    private void inject(CallbackInfoReturnable<Float> cir) {
        float overrideSpeed = TweakToggle.Tweak_FastFlying.getBooleanValue() ? FishConfig.FLYING_SPEED_LEVEL.get() : 0.05F;
        cir.setReturnValue(overrideSpeed);
    }
}
