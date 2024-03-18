package com.github.Debris.oh_my_mite_client.mixins;

import com.github.Debris.oh_my_mite_client.config.TweakToggle;
import net.minecraft.EntityLivingBase;
import net.minecraft.RendererLivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RendererLivingEntity.class)
public class RenderLivingEntityMixin {
    @Inject(method = "renderModel", at = @At("HEAD"), cancellable = true)
    private void inject(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4, float par5, float par6, float par7, CallbackInfo ci) {
        if (TweakToggle.Tweak_RenderLivings.getBooleanValue()) {
            ci.cancel();
        }
    }
}
