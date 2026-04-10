package com.github.debris.ommc.util;

import net.minecraft.ItemStack;

import java.util.function.Predicate;

public class ItemUtil {
    public static boolean compareID(ItemStack itemStack, ItemStack other) {
        return itemStack.itemID == other.itemID;
    }

    public static boolean compareMeta(ItemStack itemStack, ItemStack other) {
        return itemStack.getItemSubtype() == other.getItemSubtype();
    }

    public static boolean compareIDMeta(ItemStack itemStack, ItemStack other) {
        return compareID(itemStack, other) && compareMeta(itemStack, other);
    }

    public static Predicate<ItemStack> predicateIDMeta(ItemStack template) {
        return x -> compareIDMeta(x, template);
    }

    public static boolean isFullStack(ItemStack itemStack) {
        return itemStack.stackSize >= itemStack.getMaxStackSize();
    }

    public static boolean canMerge(ItemStack to, ItemStack from) {
        if (isFullStack(to)) return false;// full slot can not merge
        return compareIDMeta(to, from);
    }
}
