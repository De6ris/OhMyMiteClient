package com.github.Debris.ommc.feat;

import com.github.Debris.ommc.config.InventoryConfig;
import com.github.Debris.ommc.inventory.InventoryUtil;
import com.github.Debris.ommc.inventory.section.ContainerSection;
import com.github.Debris.ommc.inventory.section.EnumSection;
import com.github.Debris.ommc.inventory.section.SectionHandler;
import net.minecraft.ContainerPlayer;
import net.minecraft.Slot;
import org.lwjgl.input.Mouse;

import java.util.List;
import java.util.Optional;

public class WheelMoving {
    public static void wheelListener(Slot mouseOver) {
        int wheelStatus = Mouse.getDWheel();
        if (wheelStatus == 0) return;
        boolean scrollDown = wheelStatus < 0;
        if (InventoryConfig.WheelMovingInvert.getBooleanValue()) scrollDown = !scrollDown;

        ContainerSection playerStorage = EnumSection.InventoryStorage.get();
        if (playerStorage == null) return;
        ContainerSection hotBar = EnumSection.InventoryHotBar.get();
        if (hotBar == null) return;

        ContainerSection thisSection = SectionHandler.getSection(mouseOver);

        if (thisSection == hotBar || thisSection == playerStorage) {
            handleInventoryToOther(mouseOver, scrollDown, thisSection, hotBar, playerStorage);
        } else {// move this between hotBar or storage, a little complex
            handleOtherToInventory(mouseOver, scrollDown, hotBar, playerStorage);
        }
    }

    private static void handleInventoryToOther(Slot mouseOver, boolean scrollDown, ContainerSection thisSection, ContainerSection hotBar, ContainerSection playerStorage) {
        ContainerSection otherSection;
        if (InventoryUtil.getCurrentContainer() instanceof ContainerPlayer) {
            otherSection = thisSection == hotBar ? playerStorage : hotBar;
        } else {
            List<ContainerSection> others = SectionHandler.streamAllSections().filter(x -> x != hotBar && x != playerStorage).toList();
            if (others.isEmpty()) {
                otherSection = thisSection == hotBar ? playerStorage : hotBar;
            } else {
                otherSection = others.get(0);
            }
        }
        if (scrollDown) {// decrease this slot
            moveOneToOther(mouseOver, otherSection);
        } else {// increase this slot
            otherSection.providesOneScroll(mouseOver.getStack()).ifPresent(x -> InventoryUtil.moveOneItem(mouseOver, x));
        }
    }

    private static void handleOtherToInventory(Slot mouseOver, boolean scrollDown, ContainerSection hotBar, ContainerSection playerStorage) {
        if (scrollDown) {// decrease this slot; first try merge to hotBar
            Optional<Slot> slot = hotBar.absorbsOneScroll(mouseOver.getStack());
            if (slot.isPresent()) {
                InventoryUtil.moveOneItem(slot.get(), mouseOver);// merge to hotBar
                return;
            }
            slot = playerStorage.absorbsOneScroll(mouseOver.getStack());// merge to storage
            if (slot.isPresent()) {
                InventoryUtil.moveOneItem(slot.get(), mouseOver);// merge to hotBar
                return;
            }
            if (!moveOneToOther(mouseOver, hotBar)) {// first try move to hotBar, then storage
                moveOneToOther(mouseOver, playerStorage);
            }
        } else {// increase this slot
            playerStorage.providesOneScroll(mouseOver.getStack()).ifPresentOrElse(// first absorb from storage
                    x -> InventoryUtil.moveOneItem(mouseOver, x),
                    () -> hotBar.providesOneScroll(mouseOver.getStack()).ifPresent(x -> InventoryUtil.moveOneItem(mouseOver, x))// then absorb from hotBar
            );
        }
    }

    private static boolean moveOneToOther(Slot mouseOver, ContainerSection other) {
        Optional<Slot> slot = other.absorbsOneScroll(mouseOver.getStack());
        if (slot.isPresent()) {
            InventoryUtil.moveOneItem(slot.get(), mouseOver);
            return true;
        } else {
            slot = other.getEmptySlot();
            if (slot.isPresent()) {
                InventoryUtil.moveOneItem(slot.get(), mouseOver);
                return true;
            }
        }
        return false;
    }
}
