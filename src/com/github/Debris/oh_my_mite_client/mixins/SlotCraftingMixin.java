package com.github.Debris.oh_my_mite_client.mixins;

import com.github.Debris.oh_my_mite_client.config.TweakToggle;
import com.github.Debris.oh_my_mite_client.util.CraftingHandler;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SlotCrafting.class)
public abstract class SlotCraftingMixin extends Slot {
    @Shadow
    @Final
    private IInventory craftMatrix;

    @Shadow
    private EntityPlayer thePlayer;

    public SlotCraftingMixin(IInventory inventory, int slot_index, int display_x, int display_y) {
        super(inventory, slot_index, display_x, display_y);
    }

    @Inject(method = "onPickupFromSlot", at = @At("TAIL"))
    private void inject(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack, CallbackInfo ci) {
        if (TweakToggle.Tweak_AutoCraft.getBooleanValue()) {

            for (int slot = 0; slot < this.craftMatrix.getSizeInventory(); ++slot) {
                ItemStack var4 = this.craftMatrix.getStackInSlot(slot);
                if (var4 != null) {
                    int supplier_slot = CraftingHandler.getInstance().getSlotSameItem
                            (var4.getItem(), var4.getItemSubtype(), this.thePlayer.inventory);
                    if (supplier_slot == -1) continue;
                    this.thePlayer.inventory.decrStackSize(supplier_slot, 1);
                    var4.stackSize += 1;
                    this.getContainer().onCraftMatrixChanged(this.craftMatrix);
                }
            }
        }
    }
}
