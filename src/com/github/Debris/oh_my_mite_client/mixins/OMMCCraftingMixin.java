package com.github.Debris.oh_my_mite_client.mixins;

import net.minecraft.MITEContainerCrafting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MITEContainerCrafting.class)
public class OMMCCraftingMixin {
    @Inject(method = "onUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/NetClientHandler;addToSendQueue(Lnet/minecraft/Packet;)V"))
    private void inject(CallbackInfo ci) {

    }
}
