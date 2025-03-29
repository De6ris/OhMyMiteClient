package com.github.Debris.ommc.event;

import com.github.Debris.ommc.config.Callbacks;
import com.github.Debris.ommc.config.OMMCConfig;
import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.event.TickHandler;
import fi.dy.masa.malilib.event.WorldLoadHandler;
import fi.dy.masa.malilib.interfaces.IInitializationHandler;
import net.minecraft.Minecraft;

public class OMMCInitHandler implements IInitializationHandler {
    @Override
    public void registerModHandlers() {
        ConfigManager.getInstance().registerConfig(OMMCConfig.getInstance());
        WorldLoadHandler.getInstance().registerWorldLoadPreHandler(new OMMCWorldLoadListener());
        TickHandler.getInstance().registerClientTickHandler(new OMMCTickHandlers());
        Callbacks.init(Minecraft.getMinecraft());
        if (OMMCConfig.GammaOverride.getBooleanValue()) OMMCConfig.GammaOverride.onValueChanged();
    }
}
