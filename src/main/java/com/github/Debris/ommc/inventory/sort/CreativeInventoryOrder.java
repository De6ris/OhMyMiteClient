package com.github.Debris.ommc.inventory.sort;

import net.minecraft.CreativeTabs;
import net.minecraft.Item;

import java.util.HashMap;
import java.util.Map;

public class CreativeInventoryOrder {
    static final Map<Item, Integer> INDEX_MAP = new HashMap<>();
    static boolean initialized = false;

    static void setup() {
        if (!initialized) {
            initialize();
            initialized = true;
        }
    }

    private static void initialize() {
        for (Item item : Item.itemsList) {
            if (item == null) continue;
            CreativeTabs tab = item.getCreativeTab();
            if (tab == null) continue;
            CreativeInventoryOrder.INDEX_MAP.put(item, tab.getTabIndex() << 20 | item.itemID);// this assumes item id won't exceed 1048576
        }
    }

    static int compare(Item c1, Item c2) {
        Map<Item, Integer> map = INDEX_MAP;
        if (!map.containsKey(c1)) {
            return map.containsKey(c2) ? 1 : 0;
        }
        if (!map.containsKey(c2)) return -1;
        int order1 = map.get(c1);
        int order2 = map.get(c2);
        return Integer.compare(order1, order2);
    }
}
