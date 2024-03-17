package com.github.Debris.oh_my_mite_client.mixins;

import com.github.Debris.oh_my_mite_client.config.FeatureToggle;
import net.minecraft.MovementInput;
import net.minecraft.MovementInputFromOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MovementInputFromOptions.class)
public abstract class OMMCMovementMixin extends MovementInput {
    @Inject(method = "updatePlayerMoveState", at = @At("TAIL"))
    private void inject(CallbackInfo ci) {
        if (FeatureToggle.Tweak_AutoForward.getBooleanValue()) {
            this.moveForward = 1.0F;
        }
        if (FeatureToggle.Tweak_AutoLeft.getBooleanValue()) {
            this.moveStrafe = 1.0F;
        }
        if (FeatureToggle.Tweak_AutoBack.getBooleanValue()) {
            this.moveForward = -1.0F;
        }
        if (FeatureToggle.Tweak_AutoRight.getBooleanValue()) {
            this.moveStrafe = -1.0F;
        }
        if (FeatureToggle.Tweak_Autojump.getBooleanValue()) {
            this.jump = true;
        }
        if (FeatureToggle.Tweak_AutoSneak.getBooleanValue()) {
            this.sneak = true;
        }
    }
}
