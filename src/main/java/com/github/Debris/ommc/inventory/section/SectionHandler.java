package com.github.Debris.ommc.inventory.section;

import com.github.Debris.ommc.inventory.InventoryUtil;
import net.minecraft.Container;
import net.minecraft.GuiContainer;
import net.minecraft.IInventory;
import net.minecraft.Slot;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SectionHandler {
    static final List<ContainerSection> unIdentifiedSections = new ArrayList<>();

    static final Map<EnumSection, ContainerSection> sectionMap = new EnumMap<>(EnumSection.class);

    public static void updateSection(GuiContainer guiContainer) {
        clear();
        Container container = InventoryUtil.getContainer(guiContainer);
//        Text title = guiContainer.getTitle();
        List<Slot> slots = InventoryUtil.getSlots(container);
        Map<IInventory, List<Slot>> groupedByInventory = slots.stream().collect(Collectors.groupingBy(x -> x.inventory));

        for (Map.Entry<IInventory, List<Slot>> inventoryListEntry : groupedByInventory.entrySet()) {
            IInventory iInventory = inventoryListEntry.getKey();
            List<Slot> partSlots = inventoryListEntry.getValue();
            new SectionIdentifier(iInventory).identify(
//                    title,
                    container, partSlots);
        }
    }

    public static void clear() {
        sectionMap.clear();
        unIdentifiedSections.clear();
    }

    public static ContainerSection getSection(EnumSection section) {
        return sectionMap.get(section);
    }

    public static List<ContainerSection> getUnIdentifiedSections() {
        return unIdentifiedSections;
    }

    public static Stream<ContainerSection> streamAllSections() {
        return Stream.concat(unIdentifiedSections.stream(), sectionMap.values().stream());
    }

    public static Optional<ContainerSection> getSectionMouseOver() {
        Optional<Slot> slotMouseOver = InventoryUtil.getSlotMouseOver();
        return slotMouseOver.map(SectionHandler::getSection);
    }

    public static ContainerSection getSection(Slot slot) {
        return streamAllSections().filter(x -> x.hasSlot(slot)).findFirst().orElseThrow();
    }

    public static ContainerSection getSection(int globalIndex) {
        return streamAllSections()
                .filter(x -> x.slots()
                        .stream()
                        .anyMatch(y -> InventoryUtil.getSlotId(y) == globalIndex)
                )
                .findFirst()
                .orElseThrow();
    }
}
