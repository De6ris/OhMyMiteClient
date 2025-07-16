package com.github.Debris.ommc.mixins.movement;

import com.github.Debris.ommc.config.MainConfig;
import net.minecraft.MovementInput;
import net.minecraft.MovementInputFromOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MovementInputFromOptions.class)
public abstract class MovementMixin extends MovementInput {
    @Inject(method = "updatePlayerMoveState", at = @At("TAIL"))
    private void inject(CallbackInfo ci) {
        if (MainConfig.AutoForward.isOn()) {
            this.moveForward = 1.0F;
        }
        if (MainConfig.AutoLeft.isOn()) {
            this.moveStrafe = 1.0F;
        }
        if (MainConfig.AutoBack.isOn()) {
            this.moveForward = -1.0F;
        }
        if (MainConfig.AutoRight.isOn()) {
            this.moveStrafe = -1.0F;
        }
        if (MainConfig.AutoJump.isOn()) {
            this.jump = true;
        }
        if (MainConfig.AutoSneak.isOn()) {
            this.sneak = true;
        }
    }
}
