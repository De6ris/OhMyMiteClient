package com.github.debris.ommc.mixins.network;

import net.minecraft.NetClientHandler;
import net.minecraft.Packet5PlayerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetClientHandler.class)
public class NetClientHandlerMixin {
    @Inject(method = "handlePlayerInventory", at = @At("HEAD"), cancellable = true)
    public void handlePlayerInventory(Packet5PlayerInventory par1Packet5PlayerInventory, CallbackInfo ci) {
        if (par1Packet5PlayerInventory.full_inventory) {
            ci.cancel();
        }
    }
}
