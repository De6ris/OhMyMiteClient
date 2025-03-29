package com.github.Debris.ommc.event;

import com.github.Debris.ommc.config.OMMCConfig;
import fi.dy.masa.malilib.interfaces.IWorldLoadListener;
import net.minecraft.Minecraft;
import net.minecraft.WorldClient;
import org.jetbrains.annotations.Nullable;

public class OMMCWorldLoadListener implements IWorldLoadListener {
    @Override
    public void onWorldLoadPre(@Nullable WorldClient worldBefore, @Nullable WorldClient worldAfter, Minecraft mc) {
        if (worldBefore != null && worldAfter == null) {
            OMMCConfig.getInstance().save();
        }
    }
}
