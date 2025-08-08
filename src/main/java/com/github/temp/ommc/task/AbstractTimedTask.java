package com.github.temp.ommc.task;

import fi.dy.masa.malilib.interfaces.IClientTickHandler;
import net.minecraft.Minecraft;

public abstract class AbstractTimedTask implements ClientTask<Boolean>, IClientTickHandler {
    private int ticks;

    public AbstractTimedTask(int ticks) {
        this.ticks = ticks;
    }

    @Override
    public void onClientTick(Minecraft mc) {
        if (this.ticks > 0) this.ticks--;
    }

    @Override
    public boolean shouldExecute(Minecraft client) {
        return this.ticks == 0;
    }
}
