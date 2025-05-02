package com.github.Debris.ommc.util;

import com.github.Debris.ommc.inventory.section.IContainer;
import net.minecraft.Container;

public class AccessorUtil {
    public static String getTypeString(Container container) {
        return ((IContainer) container).dc$getTypeString();
    }
}
