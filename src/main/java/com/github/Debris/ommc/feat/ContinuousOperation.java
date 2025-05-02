package com.github.Debris.ommc.feat;

import com.github.Debris.ommc.inventory.InventoryTweaks;
import com.github.Debris.ommc.inventory.InventoryUtil;
import com.github.Debris.ommc.inventory.section.SectionHandler;
import fi.dy.masa.malilib.gui.GuiBase;
import net.minecraft.GuiContainer;
import net.minecraft.Minecraft;
import net.minecraft.Slot;
import org.lwjgl.input.Keyboard;

public class ContinuousOperation {
    public static void quickMoving(GuiContainer guiContainer, int mouseX, int mouseY, Slot mouseOver) {
        if (GuiBase.isLeftClicking()) {
            if (GuiBase.isShiftDown()) {
                guiContainer.mouseClicked(mouseX, mouseY, 0);
            }
            InventoryTweaks.tryMoveSimilar(SectionHandler.getSection(mouseOver), mouseOver);// TODO why crash
        }
        if (GuiBase.isCtrlDown() && Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindDrop.keyCode)) {
            InventoryUtil.getSlotMouseOver().ifPresent(InventoryUtil::dropStack);
        }
    }
}
