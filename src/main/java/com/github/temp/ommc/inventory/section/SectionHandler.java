package com.github.temp.ommc.inventory.section;

import com.github.temp.ommc.inventory.InventoryUtil;
import fi.dy.masa.malilib.util.GuiUtils;
import moddedmite.rustedironcore.api.util.LogUtil;
import net.minecraft.*;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SectionHandler {
    private static final Logger LOGGER = LogUtil.getLogger();

    private final List<ContainerSection> unIdentifiedSections = new ArrayList<>();
    private final Map<EnumSection, ContainerSection> sectionMap = new EnumMap<>(EnumSection.class);

    public SectionHandler(GuiContainer guiContainer) {
        this.identifyContainer(guiContainer, InventoryUtil.getContainer(guiContainer));
    }

    public SectionHandler(Container container) {
        this.identifyContainer(null, container);
    }

    private void identifyContainer(@Nullable GuiContainer guiContainer, Container container) {
        List<Slot> slots = InventoryUtil.getSlots(container);
        Map<IInventory, List<Slot>> groupedByInventory = slots.stream().collect(Collectors.groupingBy(x -> x.inventory));
        SectionIdentifier identifier = new SectionIdentifier(this);
        for (Map.Entry<IInventory, List<Slot>> inventoryListEntry : groupedByInventory.entrySet()) {
            IInventory iInventory = inventoryListEntry.getKey();
            List<Slot> partSlots = inventoryListEntry.getValue();
            identifier.identify(guiContainer, container, iInventory, partSlots);
        }
    }

    void handleUnidentified(ContainerSection section) {
        this.sectionMap.putIfAbsent(EnumSection.Unidentified, section);
        this.unIdentifiedSections.add(section);
    }

    void putSection(EnumSection key, ContainerSection section) {
        Map<EnumSection, ContainerSection> sectionMap = this.sectionMap;
        if (sectionMap.containsKey(key)) {
            LOGGER.warn("duplicate section for key {}: {} replacing {}", key, sectionMap.get(key), section);
        }
        sectionMap.put(key, section);
    }

    public static void onClientPlayerInit(Container playerContainer) {
        SectionHandler sectionHandler = new SectionHandler(playerContainer);
        ((IContainer) playerContainer).dc$setSectionHandler(sectionHandler);
    }

    public static void updateSection(GuiContainer guiContainer) {
        Container container = InventoryUtil.getContainer(guiContainer);
        ((IContainer) container).dc$setSectionHandler(new SectionHandler(guiContainer));
    }

    public static SectionHandler getSectionHandler() {
        Container container = InventoryUtil.getCurrentContainer();
        SectionHandler sectionHandler = ((IContainer) container).dc$getSectionHandler();
        if (sectionHandler != null) return sectionHandler;

        GuiScreen screen = GuiUtils.getCurrentScreen();
        if (!(screen instanceof GuiContainer guiContainer)) {
            LOGGER.warn("weird that in a non container screen with non-default container, screen:\n{}", screen);
            return ((IContainer) InventoryUtil.getCurrentContainer()).dc$getSectionHandler();
        }

        SectionHandler newHandler = new SectionHandler(guiContainer);
        ((IContainer) container).dc$setSectionHandler(newHandler);
        return newHandler;
    }

    public static ContainerSection getSection(EnumSection section) {
        ContainerSection ret = getSectionHandler().sectionMap.get(section);
        if (ret == null) {
            LOGGER.warn("no section instance for {}", section);
            return ContainerSection.EMPTY;
        }
        return ret;
    }

    public static boolean hasSection(EnumSection section) {
        return getSectionHandler().sectionMap.containsKey(section);
    }

    public static List<ContainerSection> getUnIdentifiedSections() {
        return getSectionHandler().unIdentifiedSections;
    }

    public static Stream<ContainerSection> streamAllSections() {
        SectionHandler sectionHandler = getSectionHandler();
        return Stream.concat(sectionHandler.unIdentifiedSections.stream(), sectionHandler.sectionMap.values().stream()).distinct();
    }

    public static Optional<ContainerSection> getSectionMouseOver() {
        Optional<Slot> slotMouseOver = InventoryUtil.getSlotMouseOver();
        return slotMouseOver.map(SectionHandler::getSection);
    }

    public static ContainerSection getSection(Slot slot) {
        return streamAllSections().filter(x -> x.hasSlot(slot)).findFirst().orElse(ContainerSection.EMPTY);
    }

    public static ContainerSection getSection(int globalIndex) {
        return streamAllSections()
                .filter(x -> x.slots()
                        .stream()
                        .anyMatch(y -> InventoryUtil.getSlotId(y) == globalIndex)
                )
                .findFirst()
                .orElse(ContainerSection.EMPTY);
    }
}
