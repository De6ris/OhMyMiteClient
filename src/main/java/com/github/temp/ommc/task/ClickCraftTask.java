package com.github.temp.ommc.task;

import com.github.temp.ommc.inventory.InventoryUtil;
import com.github.temp.ommc.inventory.section.EnumSection;
import net.minecraft.GuiCrafting;
import net.minecraft.Minecraft;
import net.minecraft.Slot;

public class ClickCraftTask extends AbstractTimedTask {
    public ClickCraftTask(int ticks) {
        super(ticks);
    }

    @Override
    public boolean shouldExecute(Minecraft client) {
        return client.currentScreen instanceof GuiCrafting && super.shouldExecute(client);
    }

    @Override
    public Boolean execute(Minecraft client) {
        Slot slot = EnumSection.CraftResult.get().slots().get(0);
        InventoryUtil.leftClick(slot);
        return true;
    }
}
