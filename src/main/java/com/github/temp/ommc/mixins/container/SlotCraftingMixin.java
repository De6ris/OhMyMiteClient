package com.github.temp.ommc.mixins.container;

import com.github.temp.ommc.config.InventoryConfig;
import com.github.temp.ommc.util.AutoCrafting;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SlotCrafting.class)
public abstract class SlotCraftingMixin extends Slot {
    @Shadow
    private EntityPlayer thePlayer;

    public SlotCraftingMixin(IInventory inventory, int slot_index, int display_x, int display_y) {
        super(inventory, slot_index, display_x, display_y);
    }

    @Inject(method = "onSlotClicked", at = @At(value = "INVOKE", target = "Lnet/minecraft/EntityClientPlayerMP;hasCurse(Lnet/minecraft/Curse;Z)Z"))
    private void onStartCrafting(EntityPlayer player, int button, Container container, CallbackInfo ci) {
        if (this.shouldAutoCraft()) {
            AutoCrafting.recordRecipe();
        }
    }

    // 0 craft result, 1-9 craft matrix, 10-36 inventory, 37-45 hotbar
    @Inject(method = "onPickupFromSlot", at = @At("RETURN"))
    private void onCraftingSuccess(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack, CallbackInfo ci) {
        if (this.shouldAutoCraft()) {
            try {
                AutoCrafting.tryAutoCraft();
            } catch (Exception e) {
                System.out.println("ommc: auto craft error:");
                e.printStackTrace();
            }
        }
    }

    @Unique
    private boolean shouldAutoCraft() {
        return this.thePlayer.onClient() && InventoryConfig.AutoCrafting.getBooleanValue() && Minecraft.getMinecraft().currentScreen instanceof GuiCrafting;
    }
}
