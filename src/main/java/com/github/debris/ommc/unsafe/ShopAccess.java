package com.github.debris.ommc.unsafe;

import net.minecraft.Slot;

public class ShopAccess {
    public static boolean isShopSlot(Slot slot) {
        return slot.getClass().getName().contains("Shop");
    }
}
