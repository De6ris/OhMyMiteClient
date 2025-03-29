package com.github.Debris.ommc.inventory.sort;

import com.github.Debris.ommc.config.OMMCConfig;
import com.github.Debris.ommc.util.ItemUtil;
import com.github.Debris.ommc.util.PinYinSupport;
import com.github.Debris.ommc.util.StringUtil;
import fi.dy.masa.malilib.config.interfaces.IConfigOptionListEntry;
import net.minecraft.Item;
import net.minecraft.ItemStack;

import java.util.Comparator;

public enum SortCategory implements IConfigOptionListEntry {
    ITEM_ID("item_id", "物品ID", Comparator.comparing(x -> x.itemID)),
    TRANSLATION_KEY("translation_key", "翻译键", Comparator.comparing(Item::getUnlocalizedName)),
    TRANSLATION_RESULT("translation_result", "翻译结果", Comparator.comparing(StringUtil::translateItem)),
    PINYIN("pinyin", "拼音(需要Rei)", SortCategory::compareByPinyin);

    private final String configString;
    private final String translationKey;
    private final Comparator<Item> order;// this assumes they are distinct

    SortCategory(String configString, String translationKey, Comparator<Item> order) {
        this.configString = configString;
        this.translationKey = translationKey;
        this.order = order;
    }

    public static SortCategory getCategory() {
        return OMMCConfig.ItemSortingOrder.getEnumValue();
    }

    /*
     * When using, if result > 0, I will swap.
     * Thus, if you want a comes before b, you should let a be smaller than b in the comparator.
     * */
    public static Comparator<ItemStack> getItemStackSorter() {
        SortCategory category = getCategory();
        setup(category);
        Comparator<Item> itemOrderByConfig = category.order;
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
        if (category == SortCategory.PINYIN) {
            PinYinSupport.tryInit();
        }
    }

    private static int compareByPinyin(Item c1, Item c2) {
        if (PinYinSupport.available()) {
            String translate1 = StringUtil.translateItem(c1);
            String translate2 = StringUtil.translateItem(c2);
            return PinYinSupport.compareString(translate1, translate2, () -> TRANSLATION_KEY.order.compare(c1, c2));
        }

        return TRANSLATION_KEY.order.compare(c1, c2);
    }

    @Override
    public String getStringValue() {
        return this.configString;
    }

    @Override
    public String getDisplayName() {
        return this.translationKey;
    }

    @Override
    public IConfigOptionListEntry cycle(boolean forward) {
        int id = this.ordinal();
        if (forward) {
            if (++id >= values().length) {
                id = 0;
            }
        } else {
            if (--id < 0) {
                id = values().length - 1;
            }
        }
        return values()[id % values().length];
    }

    @Override
    public IConfigOptionListEntry fromString(String name) {
        return fromStringStatic(name);
    }

    public static SortCategory fromStringStatic(String name) {
        for (SortCategory val : values()) {
            if (val.configString.equalsIgnoreCase(name)) {
                return val;
            }
        }
        return SortCategory.ITEM_ID;
    }
}
