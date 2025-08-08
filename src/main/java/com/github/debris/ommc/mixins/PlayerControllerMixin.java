package com.github.debris.ommc.mixins;

import com.github.debris.ommc.feat.BetterQuickMoving;
import com.github.debris.ommc.inventory.SlotActionType;
import net.minecraft.EntityPlayer;
import net.minecraft.ItemStack;
import net.minecraft.PlayerControllerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerControllerMP.class)
public class PlayerControllerMixin {
    @Inject(method = "windowClick", at = @At("HEAD"))
    private void onClick(int windowID, int index, int button, int clickType, EntityPlayer clientPlayer, CallbackInfoReturnable<ItemStack> cir) {
        if (clickType == SlotActionType.QUICK_MOVE.ordinal()) {
            BetterQuickMoving.onQuickMove(index, button);
        }
    }
}
