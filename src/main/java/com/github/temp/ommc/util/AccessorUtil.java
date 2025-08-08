package com.github.temp.ommc.util;

import com.github.temp.ommc.inventory.section.IContainer;
import net.minecraft.Container;

public class AccessorUtil {
    public static String getTypeString(Container container) {
        return ((IContainer) container).dc$getTypeString();
    }
}
