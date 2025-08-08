package com.github.debris.ommc.mixins.container;

import com.github.debris.ommc.config.MainConfig;
import net.minecraft.Container;
import net.minecraft.EntityClientPlayerMP;
import net.minecraft.EntityPlayer;
import net.minecraft.MITEContainerCrafting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MITEContainerCrafting.class)
public abstract class MITEContainerCraftingMixin extends Container {
    public MITEContainerCraftingMixin(EntityPlayer player) {
        super(player);
    }

    @Inject(method = "onUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/EntityClientPlayerMP;hasFoodEnergy()Z", ordinal = 1))
    private void test(CallbackInfo ci) {
        if (!MainConfig.InstantCrafting.getBooleanValue()) return;
        EntityClientPlayerMP player = (EntityClientPlayerMP) this.player;
        player.crafting_ticks = player.crafting_period;
    }
}
