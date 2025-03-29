package com.github.Debris.ommc.mixins.movement;

import com.github.Debris.ommc.config.OMMCConfig;
import net.minecraft.PlayerCapabilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerCapabilities.class)
public class PlayerAbilitiesMixin {
    @Inject(method = "getFlySpeed", at = @At("HEAD"), cancellable = true)
    private void inject(CallbackInfoReturnable<Float> cir) {
        float overrideSpeed = OMMCConfig.FastFlying.isOn() ? (float) OMMCConfig.FlySpeedLevel.getDoubleValue() : 0.05F;
        cir.setReturnValue(overrideSpeed);
    }
}
