package com.github.debris.ommc.feat;

import com.github.debris.ommc.inventory.InventoryUtil;
import com.github.debris.ommc.inventory.section.ContainerSection;
import com.github.debris.ommc.inventory.section.EnumSection;
import com.github.debris.ommc.inventory.section.SectionHandler;
import com.github.debris.ommc.inventory.sort.SortCategory;
import net.minecraft.ItemStack;
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

    public static boolean trySort() {
        Optional<ContainerSection> optional = SectionHandler.getSectionMouseOver();
        if (optional.isEmpty()) return false;
        ContainerSection section = optional.get();
        if (!shouldSort(section)) return false;
        int before = InventoryUtil.getChangeCount();
        makeSureNotHoldingItem(section);
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

    // try to put held item to this section, if fail then drop
    public static void makeSureNotHoldingItem(ContainerSection section) {
        ItemStack heldItem = InventoryUtil.getHeldStack();
        if (heldItem == null) return;
        Optional<Slot> mergeSlot = section.absorbsOneScroll(heldItem);
        while (mergeSlot.isPresent()) {
            InventoryUtil.leftClick(mergeSlot.get());
            heldItem = InventoryUtil.getHeldStack();
            if (heldItem == null) {
                return;// merge success
            } else {
                mergeSlot = section.absorbsOneScroll(heldItem);// try merge to other slot
            }
        }
        if (InventoryUtil.isHoldingItem()) {// if still
            Optional<Slot> emptySlot = section.getEmptySlot();
            if (emptySlot.isPresent()) {
                InventoryUtil.leftClick(emptySlot.get());// put held to empty
            } else {
                InventoryUtil.dropHeldItem();// just drop
            }
        }
    }

    // assume no holding item, all slots are well merged, but still blanks between
    private static void sortInternal(ContainerSection section) {
        Comparator<ItemStack> itemStackSorter = SortCategory.getItemStackSorter();

        Comparator<Slot> slotSorter = (x, y) -> itemStackSorter.compare(x.getStack(), y.getStack());

        BiConsumer<Slot, Slot> swapAction = InventoryUtil::swapSlots;
//                    DebrisClient.logger.info("swapping the {} and {}", j, j + 1);

//        if (DCCommonConfig.SortingBoxesLast.getBooleanValue()) {
//            putBoxesLast(section);
//
//            Map<Boolean, List<Slot>> grouped = section.slots().stream().filter(Slot::hasStack).collect(Collectors.partitioningBy(x -> isShulkerBox(x.getStack())));
//
//            Slot[] nonBoxes = grouped.get(false).toArray(Slot[]::new);
//            Slot[] boxes = grouped.get(true).toArray(Slot[]::new);
//
//            runSorting(nonBoxes, slotSorter, swapAction);
//            runSorting(boxes, slotSorter, swapAction);
//        } else {
        section.fillBlanks();
        section.mergeSlots();
        section.fillBlanks();
        Slot[] nonEmptySlots = section.slots().stream().filter(Slot::getHasStack).toArray(Slot[]::new);
        runSorting(nonEmptySlots, slotSorter, swapAction);
//        }
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
