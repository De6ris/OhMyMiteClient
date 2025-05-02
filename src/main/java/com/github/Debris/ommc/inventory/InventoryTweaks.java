package com.github.Debris.ommc.inventory;

import com.github.Debris.ommc.config.OMMCConfig;
import com.github.Debris.ommc.feat.ContinuousOperation;
import com.github.Debris.ommc.feat.WheelMoving;
import com.github.Debris.ommc.inventory.section.ContainerSection;
import com.github.Debris.ommc.inventory.section.EnumSection;
import com.github.Debris.ommc.inventory.section.SectionHandler;
import com.github.Debris.ommc.util.ItemUtil;
import fi.dy.masa.malilib.util.GuiUtils;
import net.minecraft.*;

public class InventoryTweaks {
    public static boolean isActive() {
        return OMMCConfig.ShouldTweakInventory.getBooleanValue();
    }

    public static boolean shouldDoTrick(Slot mouseOver) {
        return mouseOver != null && mouseOver.getHasStack() && isActive() && !(InventoryUtil.getGuiContainer() instanceof GuiContainerCreative);
    }

    public static void tryMoveSimilar() {
        InventoryUtil.getSlotMouseOver().ifPresent(slot -> {
            if (slot.getHasStack()) {
                ItemStack template = slot.getStack().copy();
                ContainerSection section = SectionHandler.getSection(slot);
                section = expandSectionIfPossible(section);
                section.predicateRun(ItemUtil.predicateIDMeta(template), InventoryUtil::quickMove);
            }
        });
    }

    private static ContainerSection expandSectionIfPossible(ContainerSection section) {
        if (GuiUtils.getCurrentScreen() instanceof GuiInventory) return section;
        if (EnumSection.InventoryHotBar.isOf(section) || EnumSection.InventoryStorage.isOf(section))
            return EnumSection.InventoryWhole.get();
        return section;
    }

    public static boolean shouldCancelLeftClick(Slot mouseOver) {
        if (!shouldDoTrick(mouseOver)) return false;

        ContainerSection section = SectionHandler.getSection(mouseOver);
        if (tryMoveSimilar(section, mouseOver)) {
            return true;
        }
        if (OMMCConfig.ModifierMoveAll.getKeybind().isKeybindHeld()) {
            InventoryUtil.putHeldItemDown(section);
            section.notEmptyRun(InventoryUtil::quickMove);
            return true;
        }
        return false;
    }

    public static void onRender(GuiContainer guiContainer, int mouseX, int mouseY, Slot mouseOver) {
        if (!shouldDoTrick(mouseOver)) return;

        if (OMMCConfig.ContinuousOperation.getBooleanValue()) {
            ContinuousOperation.quickMoving(guiContainer, mouseX, mouseY, mouseOver);
        }

        if (OMMCConfig.WheelMoving.getBooleanValue()) {
            WheelMoving.wheelListener(mouseOver);
        }
    }

    public static boolean tryMoveSimilar(ContainerSection section, Slot mouseOver) {
        if (OMMCConfig.ModifierMoveSimilar.getKeybind().isKeybindHeld()) {
            InventoryUtil.putHeldItemDown(section);
            section.predicateRun(ItemUtil.predicateIDMeta(mouseOver.getStack()), InventoryUtil::quickMove);
            return true;
        }
        return false;
    }
}
