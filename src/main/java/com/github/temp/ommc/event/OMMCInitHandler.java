package com.github.temp.ommc.event;

import com.github.temp.ommc.config.Callbacks;
import com.github.temp.ommc.config.InventoryConfig;
import com.github.temp.ommc.config.MainConfig;
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
