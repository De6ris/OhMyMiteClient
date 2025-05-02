package com.github.Debris.ommc.mixins;

import com.github.Debris.ommc.feat.BetterQuickMoving;
import com.github.Debris.ommc.inventory.SlotActionType;
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
