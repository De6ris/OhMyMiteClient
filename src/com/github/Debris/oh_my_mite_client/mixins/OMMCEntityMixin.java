package com.github.Debris.oh_my_mite_client.mixins;

import com.github.Debris.oh_my_mite_client.config.FeatureToggle;
import net.minecraft.Entity;
import net.minecraft.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class OMMCEntityMixin {
    @Inject(method = "isInvisibleToPlayer", at = @At("HEAD"), cancellable = true)
    private void overrideInsivible(EntityPlayer par1EntityPlayer, CallbackInfoReturnable<Boolean> cir) {
        if (FeatureToggle.Tweak_RenderEntities.getBooleanValue()) {
            cir.setReturnValue(true);//TODO
        }
    }
}
