package com.github.debris.ommc.event;

import com.github.debris.ommc.config.Callbacks;
import com.github.debris.ommc.config.InventoryConfig;
import com.github.debris.ommc.config.MainConfig;
import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.event.TickHandler;
import fi.dy.masa.malilib.event.WorldLoadHandler;
import fi.dy.masa.malilib.interfaces.IInitializationHandler;
import net.minecraft.Minecraft;

public class OMMCInitHandler implements IInitializationHandler {
    @Override
    public void registerModHandlers() {
        ConfigManager.getInstance().registerConfig(MainConfig.getInstance());
        ConfigManager.getInstance().registerConfig(InventoryConfig.getInstance());
        WorldLoadHandler.getInstance().registerWorldLoadPreHandler(new OMMCWorldLoadListener());
        TickHandler.getInstance().registerClientTickHandler(new OMMCTickHandlers());
        Callbacks.init(Minecraft.getMinecraft());
        if (MainConfig.GammaOverride.getBooleanValue()) MainConfig.GammaOverride.onValueChanged();
    }
}
