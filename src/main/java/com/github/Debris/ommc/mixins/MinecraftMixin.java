package com.github.Debris.ommc.mixins;

import com.github.Debris.ommc.config.OMMCConfig;
import com.github.Debris.ommc.feat.ItemSwitch;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.Minecraft;
import net.minecraft.PlayerControllerMP;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Shadow
    public PlayerControllerMP playerController;

    @Inject(method = "tryClickEntity", at = @At("HEAD"))
    private void switchWeapon(int button, CallbackInfoReturnable<Boolean> cir) {
        if (!OMMCConfig.WeaponSwitch.getBooleanValue() || this.playerController.isInCreativeMode()) return;
        ItemSwitch.trySwitchToolOrWeapon(false);
    }

    @Inject(method = "sendClickBlockToController", at = @At(value = "FIELD", target = "Lnet/minecraft/RaycastCollision;block_hit_x:I", opcode = Opcodes.GETFIELD, ordinal = 0))
    private void switchTool(int par1, boolean par2, CallbackInfo ci) {
        if (!OMMCConfig.ToolSwitch.getBooleanValue() || this.playerController.isInCreativeMode()) return;
        ItemSwitch.trySwitchToolOrWeapon(true);
    }

    @ModifyReturnValue(method = "inDevMode", at = @At("RETURN"))
    private static boolean overrideDev(boolean original) {
        if (original) return true;
        return OMMCConfig.DevClient.getBooleanValue();
    }

    @ModifyReturnValue(method = "getLimitFramerate", at = @At("RETURN"))
    private int unlockFPS(int original) {
        if (original == 2) return 2;
        if (OMMCConfig.FPSUnlocked.getBooleanValue()) return Integer.MAX_VALUE;
        return original;
    }
}
