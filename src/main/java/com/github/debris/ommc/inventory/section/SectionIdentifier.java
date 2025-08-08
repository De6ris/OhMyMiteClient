package com.github.debris.ommc.inventory.section;


import com.github.debris.ommc.inventory.InventoryUtil;
import com.github.debris.ommc.util.AccessorUtil;
import net.minecraft.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SectionIdentifier {
    private static final Logger LOGGER = LogManager.getLogger(SectionIdentifier.class);
    private final SectionHandler sectionHandler;

    public SectionIdentifier(SectionHandler sectionHandler) {
        this.sectionHandler = sectionHandler;
    }

    public void identify(@Nullable GuiContainer guiContainer, Container container, IInventory iInventory, List<Slot> slotList) {
        try {
            this.identifyInternal(guiContainer, container, iInventory, slotList);
        } catch (Exception e) {
            LOGGER.warn("Error identifying container {}, stacktrace:", AccessorUtil.getTypeString(container), e);
            this.handleUnidentified(createSection(slotList));
        }
    }

    private void identifyInternal(@Nullable GuiContainer guiContainer, Container container, IInventory iInventory, List<Slot> slotList) {

        ContainerSection theWholeSection = createSection(slotList);

        if (InventoryUtil.isPlayerInventory(iInventory)) {
            int size = slotList.size();
            ContainerSection hotBar, playerStorage;
            switch (size) {
                case 9 ->// only hotBar, happens in creative screen
                        putSection(EnumSection.InventoryHotBar, theWholeSection);
                case 36 -> {// only hotBar and storage
                    Slot sample = slotList.get(0);
                    if (InventoryUtil.getSlotId(sample) == 0) {// this means hotBar then storage
                        hotBar = createSection(slotList.subList(0, 9));
                        playerStorage = createSection(slotList.subList(9, 36));
                    } else {// this means storage then hotBar
                        playerStorage = createSection(slotList.subList(0, 27));
                        hotBar = createSection(slotList.subList(27, 36));
                    }
                    putSection(EnumSection.InventoryHotBar, hotBar);
                    putSection(EnumSection.InventoryStorage, playerStorage);
                }
                case 40 -> {// armor, storage, hotBar
                    ContainerSection armor = createSection(slotList.subList(0, 4));
                    playerStorage = createSection(slotList.subList(4, 31));
                    hotBar = createSection(slotList.subList(31, 40));
                    putSection(EnumSection.InventoryHotBar, hotBar);
                    putSection(EnumSection.InventoryStorage, playerStorage);
                    putSection(EnumSection.Armor, armor);
                }
            }
            return;
        }

        if (iInventory instanceof TileEntityFurnace) {
            putSection(EnumSection.FurnaceIn, createSection(slotList.subList(0, 1)));
            putSection(EnumSection.FurnaceFuel, createSection(slotList.subList(1, 2)));
            putSection(EnumSection.FurnaceOut, createSection(slotList.subList(2, 3)));
            return;
        }

        if (iInventory instanceof InventoryMerchant) {
            putSection(EnumSection.MerchantIn, createSection(slotList.subList(0, 2)));
            putSection(EnumSection.MerchantOut, createSection(slotList.subList(2, 3)));
            return;
        }

        if (iInventory instanceof TileEntityBrewingStand) {
            putSection(EnumSection.BrewingBottles, createSection(slotList.subList(0, 3)));
            putSection(EnumSection.BrewingIngredient, createSection(slotList.subList(3, 4)));
//            putSection(EnumSection.BrewingFuel, createSection(slotList.subList(4, 5)));
            return;
        }

        if (iInventory instanceof InventoryCrafting) {
            putSection(EnumSection.CraftMatrix, theWholeSection);
            return;
        }

        if (iInventory instanceof InventoryCraftResult) {
            putSection(EnumSection.CraftResult, theWholeSection);
            return;
        }

//        if (container instanceof StonecutterScreenHandler) {
//            putSection(EnumSection.StoneCutterIn, theWholeSection);
//            return;
//        }
//
//        if (container instanceof CartographyTableScreenHandler) {
//            putSection(EnumSection.CartographyIn, createSection(slotList.subList(0, 1)));
//            putSection(EnumSection.CartographyIn2, createSection(slotList.subList(1, 2)));
//            return;
//        }

        if (container instanceof ContainerRepair) {
            putSection(EnumSection.AnvilIn1, createSection(slotList.subList(0, 1)));
            putSection(EnumSection.AnvilIn2, createSection(slotList.subList(1, 2)));
            return;
        }

//        if (container instanceof SmithingScreenHandler) {
//            putSection(EnumSection.SmithIn1, createSection(slotList.subList(0, 1)));
//            putSection(EnumSection.SmithIn2, createSection(slotList.subList(1, 2)));
//            putSection(EnumSection.SmithIn3, createSection(slotList.subList(2, 3)));
//            return;
//        }
//
//        if (container instanceof GrindstoneScreenHandler) {
//            putSection(EnumSection.GrindstoneIn, theWholeSection);
//            return;
//        }

//        if (title.getContent() instanceof TranslatableTextContent translatable) {
//            if (translatable.getKey().equals("gca.player.inventory")) {
//                putSection(EnumSection.FakePlayerActions, createSection(createFakePlayerActions(slotList)));
//                putSection(EnumSection.FakePlayerArmor, createSection(slotList.subList(1, 5)));
//                putSection(EnumSection.FakePlayerOffHand, createSection(slotList.subList(7, 8)));
//                putSection(EnumSection.FakePlayerInventoryStorage, createSection(slotList.subList(18, 45)));
//                putSection(EnumSection.FakePlayerInventoryHotBar, createSection(slotList.subList(45, 54)));
//                return;
//            }
//            if (translatable.getKey().equals("gca.player.ender_chest")) {
//                putSection(EnumSection.FakePlayerEnderChestActions, createSection(slotList.subList(0, 27)));
//                putSection(EnumSection.FakePlayerEnderChestInventory, createSection(slotList.subList(27, 54)));
//                return;
//            }
//        }

//        if (container instanceof CreativeInventoryScreen.CreativeScreenHandler) {
//            putSection(EnumSection.CreativeTab, theWholeSection);
//        }

        this.handleUnidentified(theWholeSection);
    }

    private void handleUnidentified(ContainerSection section) {
        this.sectionHandler.handleUnidentified(section);
    }

    private ContainerSection createSection(List<Slot> slots) {
        return new ContainerSection(slots);
    }

    private void putSection(EnumSection key, List<Slot> slots) {
        putSection(key, createSection(slots));
    }

    private void putSection(EnumSection key, ContainerSection section) {
        this.sectionHandler.putSection(key, section);
    }


//    private static List<Slot> createFakePlayerActions(List<Slot> total) {
//        ArrayList<Slot> slots = new ArrayList<>(total.subList(8, 18));
//        slots.add(total.get(0));
//        slots.add(total.get(5));
//        slots.add(total.get(6));
//        return slots;
//    }
}
