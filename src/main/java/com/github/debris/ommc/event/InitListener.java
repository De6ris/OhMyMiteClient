package com.github.debris.ommc.event;

import com.github.debris.ommc.config.Callbacks;
import com.github.debris.ommc.config.InventoryConfig;
import com.github.debris.ommc.config.MainConfig;
import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.event.InputEventHandler;
import fi.dy.masa.malilib.event.TickHandler;
import fi.dy.masa.malilib.event.WorldLoadHandler;
import fi.dy.masa.malilib.interfaces.IInitializationHandler;
import net.minecraft.Minecraft;

public class InitListener implements IInitializationHandler {
    @Override
    public void registerModHandlers() {
        ConfigManager.getInstance().registerConfig(MainConfig.getInstance());
        ConfigManager.getInstance().registerConfig(InventoryConfig.getInstance());
        WorldLoadHandler.getInstance().registerWorldLoadPreHandler(new WorldLoadListener());
        TickHandler.getInstance().registerClientTickHandler(new TickListener());
        Callbacks.init(Minecraft.getMinecraft());
        if (MainConfig.GammaOverride.getBooleanValue()) MainConfig.GammaOverride.onValueChanged();
        InputEventHandler.getInputManager().registerMouseInputHandler(InputListener.getInstance());
    }
}
