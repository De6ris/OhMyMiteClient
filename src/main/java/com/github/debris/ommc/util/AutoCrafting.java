package com.github.debris.ommc.util;

import com.github.debris.ommc.event.tick.TaskManager;
import com.github.debris.ommc.inventory.section.ContainerSection;
import com.github.debris.ommc.inventory.section.EnumSection;
import com.github.debris.ommc.task.ClickCraftTask;
import com.github.debris.ommc.task.SupplyTask;
import net.minecraft.ItemStack;
import net.minecraft.Slot;

import java.util.List;

public class AutoCrafting {
    private static ItemStack[] RECIPE = null;

    public static void recordRecipe() {
        ContainerSection section = EnumSection.CraftMatrix.get();
        List<Slot> slots = section.slots();
        RECIPE = new ItemStack[slots.size()];
        for (int i = 0; i < slots.size(); i++) {
            ItemStack stack = slots.get(i).getStack();
            RECIPE[i] = stack == null ? null : stack.copy();
        }
    }

    public static void tryAutoCraft() {
        if (RECIPE == null) {
            System.out.println("why auto craft before recipe is recorded");
            return;
        }
        int tick = 1;
        ContainerSection section = EnumSection.CraftMatrix.get();
        List<Slot> slots = section.slots();
        for (int i = 0; i < slots.size(); i++) {
            ItemStack demand = RECIPE[i];
            if (demand == null) continue;// no demand
            Slot slot = slots.get(i);
            if (slot.getHasStack()) continue;// satisfied
            TaskManager.getInstance().addTimedTask(new SupplyTask(tick, demand, slot.slotNumber));
            tick++;
        }
        TaskManager.getInstance().addTimedTask(new ClickCraftTask(tick));
    }
}
