package com.github.Debris.oh_my_mite_client.mixins;

import net.minecraft.Packet202PlayerAbilities;
import net.minecraft.PlayerCapabilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Packet202PlayerAbilities.class)
public abstract class Packet202PlayerAbilitiesMixin {
    @Shadow
    public abstract void setFlySpeed(float v);

    @Inject(method = "<init>(Lnet/minecraft/PlayerCapabilities;)V", at = @At("TAIL"))
    private void inject(PlayerCapabilities par1, CallbackInfo ci) {
        this.setFlySpeed(0.05F);
    }
}
