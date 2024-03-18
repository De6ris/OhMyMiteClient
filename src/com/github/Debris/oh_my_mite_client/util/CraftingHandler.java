package com.github.Debris.oh_my_mite_client.util;

import net.minecraft.*;

public class CraftingHandler {

    private static final CraftingHandler INSTANCE = new CraftingHandler();

    public static CraftingHandler getInstance() {
        return INSTANCE;
    }

    public int getSlotSameItem(Item item, int item_subtype, InventoryPlayer inventoryPlayer) {
        if (item == null) {
            return -1;
        } else {
            int i;
            for (i = 0; i < 36; ++i) {
                if (getSameItem(item, item_subtype, inventoryPlayer.mainInventory[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    private boolean getSameItem(Item item, int item_subtype, ItemStack item_stack) {
        if (item_stack == null) return false;
        return item_stack.getItem().equals(item) && item_stack.getItemSubtype() == item_subtype;
    }
}
