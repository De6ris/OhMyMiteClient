package com.github.debris.ommc.inventory;

import com.github.debris.ommc.config.InventoryConfig;
import com.github.debris.ommc.feat.ContinuousOperation;
import com.github.debris.ommc.feat.WheelMoving;
import com.github.debris.ommc.inventory.section.ContainerSection;
import com.github.debris.ommc.inventory.section.EnumSection;
import com.github.debris.ommc.inventory.section.SectionHandler;
import com.github.debris.ommc.util.ItemUtil;
import fi.dy.masa.malilib.util.GuiUtils;
import net.minecraft.*;

import java.util.Optional;

public class InventoryTweaks {
    public static boolean isActive() {
        return InventoryConfig.ShouldTweakInventory.getBooleanValue();
    }

    public static boolean shouldDoTrick(Slot mouseOver) {
        return mouseOver != null && mouseOver.getHasStack() && isActive() && !(InventoryUtil.getGuiContainer() instanceof GuiContainerCreative);
    }

    private static ContainerSection expandSectionIfPossible(ContainerSection section) {
        if (GuiUtils.getCurrentScreen() instanceof GuiInventory) return section;
        if (section.isOf(EnumSection.InventoryHotBar) || section.isOf(EnumSection.InventoryStorage))
            return EnumSection.InventoryWhole.get();
        return section;
    }

    public static boolean shouldCancelLeftClick(Slot mouseOver) {
        if (!shouldDoTrick(mouseOver)) return false;

        ContainerSection section = SectionHandler.getSection(mouseOver);
        if (tryMoveSimilar()) {
            return true;
        }
        if (InventoryConfig.ModifierMoveAll.getKeybind().isKeybindHeld()) {
            InventoryUtil.putHeldItemDown(section);
            section.notEmptyRun(InventoryUtil::quickMove);
            return true;
        }
        return false;
    }

    public static void onRender(GuiContainer guiContainer, int mouseX, int mouseY, Slot mouseOver) {
        if (!shouldDoTrick(mouseOver)) return;

        if (InventoryConfig.ContinuousOperation.getBooleanValue()) {
            ContinuousOperation.quickMoving(guiContainer, mouseX, mouseY, mouseOver);
        }

        if (InventoryConfig.WheelMoving.getBooleanValue()) {
            WheelMoving.wheelListener(mouseOver);
        }
    }

    public static boolean tryMoveSimilar() {
        if (InventoryConfig.ModifierMoveSimilar.getKeybind().isKeybindHeld()) {
            InventoryUtil.getSlotMouseOver().ifPresent(slot -> {
                if (slot.getHasStack()) {
                    ItemStack template = slot.getStack().copy();
                    ContainerSection section = SectionHandler.getSection(slot);
                    section = expandSectionIfPossible(section);
                    section.predicateRun(ItemUtil.predicateIDMeta(template), InventoryUtil::quickMove);
                }
            });
            return true;
        }
        return false;
    }

    public static boolean tryThrowSection() {
        Optional<ContainerSection> section = SectionHandler.getSectionMouseOver();
        if (section.isEmpty()) return false;
        section.get().notEmptyRun(InventoryUtil::dropStack);
        return true;
    }
}
