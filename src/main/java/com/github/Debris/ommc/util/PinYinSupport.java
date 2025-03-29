package com.github.Debris.ommc.util;

import java.util.function.IntSupplier;

public class PinYinSupport {
    public static void tryInit() {
    }

    public static boolean available() {
        return false;
    }

    public static int compareString(String s1, String s2, IntSupplier fallback) {
        return fallback.getAsInt();
    }
}
