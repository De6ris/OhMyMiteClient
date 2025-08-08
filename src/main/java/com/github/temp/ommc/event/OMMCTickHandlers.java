package com.github.temp.ommc.event;

import com.github.temp.ommc.event.tick.ClickManager;
import com.github.temp.ommc.event.tick.QuitGameManager;
import com.github.temp.ommc.event.tick.TaskManager;
import fi.dy.masa.malilib.interfaces.IClientTickHandler;
import net.minecraft.Minecraft;

import java.util.List;

public class OMMCTickHandlers implements IClientTickHandler {
    private static final OMMCTickHandlers Instance = new OMMCTickHandlers();
    private final List<IClientTickHandler> tickHandlers;

    public static OMMCTickHandlers getInstance() {
        return Instance;
    }

    OMMCTickHandlers() {
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
