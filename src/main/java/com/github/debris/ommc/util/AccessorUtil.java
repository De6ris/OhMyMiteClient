package com.github.debris.ommc.util;

import com.github.debris.ommc.inventory.section.IContainer;
import net.minecraft.Container;

public class AccessorUtil {
    public static String getTypeString(Container container) {
        return ((IContainer) container).dc$getTypeString();
    }
}
