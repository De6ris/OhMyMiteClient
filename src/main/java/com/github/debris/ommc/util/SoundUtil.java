package com.github.debris.ommc.util;

import net.minecraft.Minecraft;

public class SoundUtil {

    public static void playClickSound(Minecraft client) {
        client.sndManager.playSoundFX("random.click", 1.0f, 1.0f);
    }
}
