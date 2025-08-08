package com.github.temp.ommc.feat;

import com.github.temp.ommc.inventory.InventoryUtil;
import com.github.temp.ommc.inventory.section.ContainerSection;
import com.github.temp.ommc.inventory.section.EnumSection;
import net.minecraft.*;

import java.util.List;
import java.util.Optional;

public class TradingRestock {
    public static void tryTradingRestock(GuiMerchant guiMerchant) {
        SortInventory.makeSureNotHoldingItem(EnumSection.InventoryStorage.get());
        ContainerSection merchantIn = EnumSection.MerchantIn.get();
        ItemStack[] required = getRequiredItems(guiMerchant);
        ItemStack firstItem = required[0];
        List<Slot> slots = merchantIn.slots();
        if (firstItem != null) {
            trySupplySlot(slots.get(0), firstItem);
        }
        ItemStack secondItem = required[1];
        if (secondItem != null) {
            trySupplySlot(slots.get(1), secondItem);
        }

    }

    private static void trySupplySlot(Slot slot, ItemStack itemStack) {
        if (slot.getHasStack()) {
            if (slot.getStack().stackSize < slot.getStack().getMaxStackSize()) {
                supplyNonEmptySlot(slot);// double click to gather
            }
        } else {
            supplyEmptySlot(slot, itemStack);// try make it not empty
            if (slot.getHasStack() && slot.getStack().stackSize < slot.getStack().getMaxStackSize()) {
                supplyNonEmptySlot(slot);// if not empty, double click to gather
            }
        }
    }

    private static void supplyNonEmptySlot(Slot slot) {
        InventoryUtil.leftClick(slot);// pick item up
        InventoryUtil.gatherItems(slot);// gather
        InventoryUtil.leftClick(slot);// put item down
    }

    private static void supplyEmptySlot(Slot emptySlot, ItemStack itemStack) {
        Optional<Slot> optionalSlot = EnumSection.InventoryStorage.get().findItem(itemStack);
        if (optionalSlot.isPresent()) {
            InventoryUtil.moveToEmpty(optionalSlot.get(), emptySlot);
        } else {
            optionalSlot = EnumSection.InventoryHotBar.get().findItem(itemStack);
            optionalSlot.ifPresent(providerSlot -> InventoryUtil.moveToEmpty(providerSlot, emptySlot));
        }
    }

    private static ItemStack[] getRequiredItems(GuiMerchant guiMerchant) {
        MerchantRecipeList recipes = guiMerchant.getIMerchant().getRecipes(Minecraft.getMinecraft().thePlayer);
        if (recipes != null && !recipes.isEmpty()) {
            int currentRecipeIndex = guiMerchant.currentRecipeIndex;
            MerchantRecipe merchantRecipe = (MerchantRecipe) recipes.get(currentRecipeIndex);
            ItemStack itemToBuy = merchantRecipe.getItemToBuy();
            ItemStack secondItemToBuy = merchantRecipe.getSecondItemToBuy();
            return new ItemStack[]{itemToBuy, secondItemToBuy};
        }
        return new ItemStack[]{null, null};
    }
}
