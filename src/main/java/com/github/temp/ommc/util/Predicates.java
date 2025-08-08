package com.github.temp.ommc.util;

import net.minecraft.*;

public class Predicates {
    public static boolean notInGame(Minecraft client) {
        return client.theWorld == null || client.thePlayer == null;
    }

    public static boolean notInGuiContainer(Minecraft client) {
        if (notInGame(client)) return true;
        GuiScreen screen = client.currentScreen;
        if (screen instanceof GuiContainer) {
            if (screen instanceof GuiContainerCreative guiContainerCreative && guiContainerCreative.getCurrentTabIndex() != CreativeTabs.tabInventory.getTabIndex())
                return true;
            return false;
        }
        return true;
    }
}
