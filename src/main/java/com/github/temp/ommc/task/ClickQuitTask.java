package com.github.temp.ommc.task;

import net.minecraft.GuiButton;
import net.minecraft.GuiIngameMenu;
import net.minecraft.Minecraft;

public class ClickQuitTask extends AbstractTimedTask {
    public ClickQuitTask(int ticks) {
        super(ticks);
    }

    @Override
    public boolean shouldExecute(Minecraft client) {
        return client.currentScreen instanceof GuiIngameMenu && super.shouldExecute(client);
    }

    @Override
    public Boolean execute(Minecraft client) {
        ((GuiIngameMenu) client.currentScreen).actionPerformed(new GuiButton(1, 0, 0, ""));
        return true;
    }
}
