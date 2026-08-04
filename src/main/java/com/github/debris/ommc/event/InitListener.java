package com.github.debris.ommc.event;

import com.github.debris.ommc.config.Callbacks;
import com.github.debris.ommc.config.MainConfig;
import com.github.debris.ommc.event.tick.ClickManager;
import com.github.debris.ommc.feat.AutoQuitGame;
import com.github.debris.ommc.event.tick.TaskManager;
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
        WorldLoadHandler.getInstance().registerWorldLoadPreHandler(new WorldLoadListener());
        TickHandler.getInstance().registerClientTickHandler(AutoQuitGame::onClientTick);
        TickHandler.getInstance().registerClientTickHandler(ClickManager.getInstance());
        TickHandler.getInstance().registerClientTickHandler(TaskManager.getInstance());
        Callbacks.init(Minecraft.getMinecraft());
        if (MainConfig.GammaOverride.getBooleanValue()) MainConfig.GammaOverride.onValueChanged();
        InputEventHandler.getInputManager().registerMouseInputHandler(InputListener.getInstance());
    }
}
