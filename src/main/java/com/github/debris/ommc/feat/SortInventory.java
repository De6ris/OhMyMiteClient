package com.github.debris.ommc.feat;

import com.github.debris.ommc.inventory.InventoryTweaks;
import com.github.debris.ommc.inventory.InventoryUtil;
import com.github.debris.ommc.inventory.section.ContainerSection;
import com.github.debris.ommc.inventory.section.EnumSection;
import com.github.debris.ommc.inventory.section.SectionHandler;
import com.github.debris.ommc.inventory.sort.SortCategory;
import com.github.debris.ommc.util.Predicates;
import com.github.debris.ommc.util.SoundUtil;
import net.minecraft.ItemStack;
import net.minecraft.Minecraft;
import net.minecraft.Slot;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.Optional;
import java.util.function.BiConsumer;

public class SortInventory {
    private static final EnumSet<EnumSection> SortBlackList = EnumSet.of(
            EnumSection.FakePlayerActions,
            EnumSection.FakePlayerEnderChestActions
    );

    public static boolean onKey(Minecraft client) {
        if (!InventoryTweaks.isActive()) return false;
        if (Predicates.notInGuiContainer(client)) return false;

        Optional<ContainerSection> optional = SectionHandler.getSectionMouseOver();
        if (optional.isEmpty()) return false;

        ContainerSection section = optional.get();
        if (!shouldSort(section)) return false;

        SoundUtil.playClickSound(client);
        return SortInventory.sortSection(section);// this will block other click consumers
    }

    public static boolean sortSection(ContainerSection section) {
        int before = InventoryUtil.getChangeCount();
        InventoryTweaks.clearCursor(section);
        sortInternal(section);
        int after = InventoryUtil.getChangeCount();
        return after != before;// seen as sort success
    }

    private static boolean shouldSort(ContainerSection section) {
        for (EnumSection enumSection : SortBlackList) {
            if (section.isOf(enumSection)) return false;
        }
        return true;
    }

    // assume no holding item, all slots are well merged, but still blanks between
    private static void sortInternal(ContainerSection section) {
        Comparator<ItemStack> itemStackSorter = SortCategory.getItemStackSorter();
        Comparator<Slot> slotSorter = (x, y) -> itemStackSorter.compare(x.getStack(), y.getStack());
        BiConsumer<Slot, Slot> swapAction = InventoryUtil::swapSlots;

//        if (DCCommonConfig.SortingContainersLast.getBooleanValue()) {
//            putContainersLast(section);
//            splitByContainer(section).forEach(x -> process(x, slotSorter, swapAction));
//        } else {
            process(section, slotSorter, swapAction);
//        }
    }

    private static void process(ContainerSection section, Comparator<Slot> sorter, BiConsumer<Slot, Slot> swapAction) {
        section.fillBlanks();
        section.mergeSlots();
        section.fillBlanks();
        Slot[] nonEmptySlots = section.slots().stream().filter(Slot::getHasStack).toArray(Slot[]::new);
        runSorting(nonEmptySlots, sorter, swapAction);
    }

    /*
     * sorter: if j is bigger than j+1, I will swap them
     * */
    private static <T> void runSorting(T[] slots, Comparator<T> sorter, BiConsumer<T, T> swapAction) {
        int length = slots.length;
        if (length <= 1) return;

//        if (OMMCConfig.CachedSorting.getBooleanValue()) {
//            List<PermutationUtil.Transposition> optimal = PermutationUtil.getOptimalProcess(slots, sorter);
//            optimal.forEach(x -> x.operate(slots, swapAction));
//        } else {
        // direct sorting
        for (int i = 1; i < length; i++) {
            boolean flag = true;
            for (int j = 0; j < length - i; j++) {
                T slotJ = slots[j];
                T slotJ_1 = slots[j + 1];
                int compare = sorter.compare(slotJ, slotJ_1);
                if (compare > 0) {
                    swapAction.accept(slotJ, slotJ_1);
                    flag = false;
                }
            }
            if (flag) break;
        }
//        }
    }
}
