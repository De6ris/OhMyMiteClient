package com.github.temp.ommc.mixins.gui;

import com.github.temp.ommc.inventory.section.SectionHandler;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayer.class)
public abstract class ClientPlayerMixin extends AbstractClientPlayer {
    public ClientPlayerMixin(World par1World, String par2Str) {
        super(par1World, par2Str);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(Minecraft par1Minecraft, World par2World, Session par3Session, int par4, CallbackInfo ci) {
        SectionHandler.onClientPlayerInit(this.inventoryContainer);
    }
}