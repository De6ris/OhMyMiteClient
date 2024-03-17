package com.github.Debris.oh_my_mite_client.mixins;

import com.github.Debris.oh_my_mite_client.config.FieldAccessor;
import net.minecraft.GameSettings;
import net.minecraft.MovementInput;
import net.minecraft.MovementInputFromOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MovementInputFromOptions.class)
public abstract class OMMCMovementMixin extends MovementInput {
    @Shadow
    private GameSettings gameSettings;

    @Inject(method = "updatePlayerMoveState", at = @At("TAIL"))
    private void inject(CallbackInfo ci) {
        if (((FieldAccessor) this.gameSettings).OMMCOptionsProvider().keyBindAutoForward.getBoolean()) {
            this.moveForward = 1.0F;
        }
        if (((FieldAccessor) this.gameSettings).OMMCOptionsProvider().keyBindAutoLeft.getBoolean()) {
            this.moveStrafe = 1.0F;
        }
        if (((FieldAccessor) this.gameSettings).OMMCOptionsProvider().keyBindAutoBack.getBoolean()) {
            this.moveForward = -1.0F;
        }
        if (((FieldAccessor) this.gameSettings).OMMCOptionsProvider().keyBindAutoRight.getBoolean()) {
            this.moveStrafe = -1.0F;
        }
        if (((FieldAccessor) this.gameSettings).OMMCOptionsProvider().keyBindAutojump.getBoolean()) {
            this.jump = true;
        }
        if (((FieldAccessor) this.gameSettings).OMMCOptionsProvider().keyBindAutoSneak.getBoolean()) {
            this.sneak = true;
        }
    }
}
