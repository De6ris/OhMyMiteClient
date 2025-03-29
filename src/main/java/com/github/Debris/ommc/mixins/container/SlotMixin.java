package com.github.Debris.ommc.mixins.container;

import net.minecraft.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Slot.class)
public class SlotMixin {
    @Inject(method = "setLocked", at = @At("HEAD"), cancellable = true)
    private void noLocking(boolean locked, CallbackInfo ci) {
        ci.cancel();
    }

}
