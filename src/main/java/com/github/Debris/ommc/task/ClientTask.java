package com.github.Debris.ommc.task;

import net.minecraft.Minecraft;

public interface ClientTask<T> {
    boolean shouldExecute(Minecraft client);

    T execute(Minecraft client);
}
