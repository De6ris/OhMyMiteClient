package com.github.debris.ommc;

import net.xiaoyu233.fml.FishModLoader;

public class ModReference {
    public static final String EMI = "emi";

    public static boolean hasMod(String modId) {
        return FishModLoader.hasMod(modId);
    }
}
