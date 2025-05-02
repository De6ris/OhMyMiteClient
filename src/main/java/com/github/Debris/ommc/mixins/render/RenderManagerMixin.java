package com.github.Debris.ommc.mixins.render;

import com.github.Debris.ommc.config.OMMCConfig;
import net.minecraft.Entity;
import net.minecraft.RenderManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderManager.class)
public class RenderManagerMixin {

    @Inject(method = "renderEntity", at = @At("HEAD"), cancellable = true)
    private void inject_1(Entity par1Entity, float par2, CallbackInfo ci) {
        if (OMMCConfig.CullEntityRender.isOn()) {
            ci.cancel();
        }
    }

    @Inject(method = "renderEntityWithPosYaw", at = @At("HEAD"), cancellable = true)
    private void inject_2(Entity par1Entity, double par2, double par4, double par6, float par8, float par9, CallbackInfo ci) {
        if (OMMCConfig.CullEntityRender.isOn()) {
            ci.cancel();
        }
    }
}
