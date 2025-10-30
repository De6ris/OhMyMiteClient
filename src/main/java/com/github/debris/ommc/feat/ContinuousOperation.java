package com.github.debris.ommc.feat;

import com.github.debris.ommc.inventory.InventoryTweaks;
import com.github.debris.ommc.inventory.InventoryUtil;
import com.github.debris.ommc.unsafe.ShopAccess;
import fi.dy.masa.malilib.gui.GuiBase;
import net.minecraft.GuiContainer;
import net.minecraft.Minecraft;
import net.minecraft.Slot;
import org.lwjgl.input.Keyboard;

public class ContinuousOperation {
    public static void quickMoving(GuiContainer guiContainer, int mouseX, int mouseY, Slot mouseOver) {
        if (ShopAccess.isShopSlot(mouseOver)) return;

        if (GuiBase.isLeftClicking()) {
            if (GuiBase.isShiftDown()) {
                guiContainer.mouseClicked(mouseX, mouseY, 0);
            }
            InventoryTweaks.tryMoveSimilar();// TODO why crash
        }
        if (GuiBase.isCtrlDown() && Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindDrop.keyCode)) {
            InventoryUtil.getSlotMouseOver().ifPresent(InventoryUtil::dropStack);
        }
    }
}
