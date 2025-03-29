package com.github.Debris.ommc.inventory.sort;


import net.minecraft.ItemStack;

import java.util.Comparator;

public class ItemStackComparators {
    public static final Comparator<ItemStack> COUNT = Comparator.comparing(x -> x.stackSize);
    //    public static final Comparator<ItemStack> SHULKER_BOX = ItemStackComparators::compareShulkerBox;
//    public static final Comparator<ItemStack> ENCHANTMENT = Comparator.comparing(x -> x.getOrDefault(DataComponentTypes.ENCHANTMENTS, ItemEnchantmentsComponent.DEFAULT).getEnchantmentEntries().size());
    public static final Comparator<ItemStack> DAMAGE = Comparator.comparing(ItemStack::getItemDamage);

//    public static int compareShulkerBox(ItemStack c1, ItemStack c2) {
//        if (InventoryTweaks.isShulkerBox(c1) && InventoryTweaks.isShulkerBox(c2)) {
//            List<ItemStack> list1 = c1.getOrDefault(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT).streamNonEmpty().toList();
//            List<ItemStack> list2 = c2.getOrDefault(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT).streamNonEmpty().toList();
//            int compare = Integer.compare(list1.size(), list2.size());// comparing size
//            if (compare != 0) return compare;
//            if (list1.isEmpty()) return 0;
//            if (isPureItemStackList(list1) && isPureItemStackList(list2)) {
//                ItemStack itemStack1 = list1.getFirst();
//                ItemStack itemStack2 = list2.getFirst();
//                return SortCategory.getItemStackSorter().compare(itemStack1, itemStack2);
//            }
//        }
//        return 0;
//    }

    // Assuming no empty stack and non-empty list
//    private static boolean isPureItemStackList(List<ItemStack> list) {
//        ItemStack first = list.getFirst();
//        Predicate<ItemStack> predicate = ItemUtil.predicateIDMeta(first);
//        return list.stream().allMatch(predicate);
//    }
}
