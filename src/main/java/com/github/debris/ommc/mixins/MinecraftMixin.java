package com.github.debris.ommc.mixins;

import com.github.debris.ommc.config.MainConfig;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @ModifyReturnValue(method = "inDevMode", at = @At("RETURN"))
    private static boolean overrideDev(boolean original) {
        if (original) return true;
        return MainConfig.DevClient.getBooleanValue();
    }

    @ModifyReturnValue(method = "getLimitFramerate", at = @At("RETURN"))
    private int unlockFPS(int original) {
        if (original == 2) return 2;
        if (MainConfig.FPSUnlocked.getBooleanValue()) return Integer.MAX_VALUE;
        return original;
    }
}
