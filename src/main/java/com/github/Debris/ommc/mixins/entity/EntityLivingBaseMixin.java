package com.github.Debris.ommc.mixins.entity;

import com.github.Debris.ommc.config.OMMCConfig;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.Damage;
import net.minecraft.EntityLivingBase;
import net.minecraft.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityLivingBase.class)
public class EntityLivingBaseMixin {
    @ModifyExpressionValue(method = "attackEntityFromHelper", at = @At(value = "INVOKE", target = "Lnet/minecraft/EntityLivingBase;getHealth()F"))
    private float test(float original, @Local(argsOnly = true) Damage damage) {
        if (OMMCConfig.CreativeOneHit.getBooleanValue() && damage.getSource().getResponsibleEntity() instanceof EntityPlayer player && player.isPlayerInCreative())
            return 0.0F;
        return original;
    }
}
