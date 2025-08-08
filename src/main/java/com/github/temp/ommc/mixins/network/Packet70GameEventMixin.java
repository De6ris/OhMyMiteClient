package com.github.temp.ommc.mixins.network;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.Packet;
import net.minecraft.Packet70GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Packet70GameEvent.class)
public abstract class Packet70GameEventMixin extends Packet {
    @ModifyExpressionValue(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/Minecraft;inDevMode()Z"))
    private static boolean alwaysDevMessage(boolean original) {
        return true;
    }
}
