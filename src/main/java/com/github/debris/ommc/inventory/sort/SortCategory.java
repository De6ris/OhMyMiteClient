package com.github.debris.ommc.inventory.sort;

import com.github.debris.ommc.config.InventoryConfig;
import com.github.debris.ommc.util.ItemUtil;
import com.github.debris.ommc.util.PinYinSupport;
import com.github.debris.ommc.util.StringUtil;
import net.minecraft.Item;
import net.minecraft.ItemStack;

import java.util.Comparator;

public enum SortCategory {
    CREATIVE_INVENTORY(CreativeInventoryOrder::compare),
    ITEM_ID(Comparator.comparing(x -> x.itemID)),
    TRANSLATION_KEY(Comparator.comparing(Item::getUnlocalizedName)),
    TRANSLATION_RESULT(Comparator.comparing(StringUtil::translateItem)),
    PINYIN(SortCategory::compareByPinyin);

    private final Comparator<Item> comparator;// this assumes they are distinct

    SortCategory(Comparator<Item> comparator) {
        this.comparator = comparator;
    }

    private static final Comparator<Item> FALLBACK = TRANSLATION_KEY.comparator;

    public static SortCategory getCategory() {
        return InventoryConfig.ItemSortingOrder.getEnumValue();
    }

    /*
     * When using, if result > 0, I will swap.
     * Thus, if you want a comes before b, you should let a be smaller than b in the comparator.
     * */
    public static Comparator<ItemStack> getItemStackSorter() {
        SortCategory category = getCategory();
        setup(category);
        Comparator<Item> itemOrderByConfig = category.comparator;
        Comparator<ItemStack> itemTypeComparator = (c1, c2) -> {
            if (ItemUtil.compareIDMeta(c1, c2)) {
                return 0;
            }
            return itemOrderByConfig.compare(c1.getItem(), c2.getItem());
        };

        return itemTypeComparator
                .thenComparing(ItemStackComparators.COUNT.reversed())// large stacks come first
//                .thenComparing(ItemStackComparators.SHULKER_BOX)
//                .thenComparing(ItemStackComparators.ENCHANTMENT.reversed())// more enchantments come first
                .thenComparing(ItemStackComparators.DAMAGE)// here damage is lost durability, so lossless items come first
                .thenComparing(ItemStack::getDisplayName)
                ;
    }

    private static void setup(SortCategory category) {
        switch (category) {
            case CREATIVE_INVENTORY -> CreativeInventoryOrder.setup();
            case PINYIN -> PinYinSupport.tryInit();
        }
    }

    private static int compareByPinyin(Item c1, Item c2) {
        if (PinYinSupport.available()) {
            String translate1 = StringUtil.translateItem(c1);
            String translate2 = StringUtil.translateItem(c2);
            return PinYinSupport.compareString(translate1, translate2, () -> FALLBACK.compare(c1, c2));
        }

        return FALLBACK.compare(c1, c2);
    }
}
