package com.github.debris.ommc.event;

import com.github.debris.ommc.event.tick.ClickManager;
import com.github.debris.ommc.event.tick.QuitGameManager;
import com.github.debris.ommc.event.tick.TaskManager;
import fi.dy.masa.malilib.interfaces.IClientTickHandler;
import net.minecraft.Minecraft;

import java.util.List;

public class TickListener implements IClientTickHandler {
    private static final TickListener Instance = new TickListener();
    private final List<IClientTickHandler> tickHandlers;

    public static TickListener getInstance() {
        return Instance;
    }

    TickListener() {
        this.tickHandlers = List.of(QuitGameManager.getInstance()
                , ClickManager.getInstance()
                , TaskManager.getInstance()
//                , TestTicker.getInstance()
        );
    }

    @Override
    public void onClientTick(Minecraft minecraft) {
        this.tickHandlers.forEach(iClientTickHandler -> iClientTickHandler.onClientTick(minecraft));
    }
}
