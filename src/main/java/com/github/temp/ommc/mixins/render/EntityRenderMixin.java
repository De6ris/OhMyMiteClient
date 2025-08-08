package com.github.temp.ommc.mixins.render;

import com.github.temp.ommc.config.MainConfig;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.EntityClientPlayerMP;
import net.minecraft.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityRenderer.class)
public class EntityRenderMixin {
    @WrapOperation(method = "updateCameraAndRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/EntityClientPlayerMP;getSpeedBoostVsSlowDown()F"))
    private float neverSlowDown(EntityClientPlayerMP instance, Operation<Float> original) {
        return MainConfig.RotateFree.getBooleanValue() ? 0.0F : original.call(instance);
    }

    @ModifyReturnValue(method = "performanceToFps", at = @At("RETURN"))
    private static int unlockFPS(int original) {
        if (MainConfig.FPSUnlocked.getBooleanValue()) return Integer.MAX_VALUE;
        return original;
    }
}
