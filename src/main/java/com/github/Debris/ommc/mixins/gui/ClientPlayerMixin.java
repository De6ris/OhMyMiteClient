package com.github.Debris.ommc.mixins.gui;

import com.github.Debris.ommc.inventory.section.SectionHandler;
import net.minecraft.ClientPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayer.class)
public class ClientPlayerMixin {
    @Inject(method = "closeScreen", at = @At("RETURN"))
    private void onContainerClosed(CallbackInfo ci) {
        SectionHandler.clear();// maybe free some memory
    }
}