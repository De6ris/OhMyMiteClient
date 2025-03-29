package com.github.Debris.ommc.util;

import com.github.Debris.ommc.config.OMMCConfig;
import com.github.Debris.ommc.inventory.InventoryTweaks;
import com.github.Debris.ommc.inventory.InventoryUtil;
import com.github.Debris.ommc.inventory.section.ContainerSection;
import com.github.Debris.ommc.inventory.section.EnumSection;
import com.github.Debris.ommc.inventory.section.SectionHandler;
import fi.dy.masa.malilib.gui.GuiBase;
import net.minecraft.*;
import org.lwjgl.input.Mouse;

import java.util.List;
import java.util.Optional;

public class InventoryHandler {
    public static boolean shouldDoTrick(Slot mouseOver) {
        return mouseOver != null && mouseOver.getHasStack() && OMMCConfig.InventoryTweaks.getBooleanValue() && !(InventoryUtil.getGuiContainer() instanceof GuiContainerCreative);
    }

    public static boolean shouldCancelLeftClick(Slot mouseOver) {
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
        if (OMMCConfig.ContinuousClick.getBooleanValue() && GuiBase.isLeftClicking()) {
            if (GuiBase.isShiftDown()) {
                guiContainer.mouseClicked(mouseX, mouseY, 0);
            }
            tryMoveSimilar(SectionHandler.getSection(mouseOver), mouseOver);// TODO why crash
        }
        if (OMMCConfig.WheelMoving.getBooleanValue()) {
            wheelListener(mouseOver);
        }
    }

    private static boolean tryMoveSimilar(ContainerSection section, Slot mouseOver) {
        if (OMMCConfig.ModifierMoveSimilar.getKeybind().isKeybindHeld()) {
            InventoryUtil.putHeldItemDown(section);
            section.predicateRun(ItemUtil.predicateIDMeta(mouseOver.getStack()), InventoryUtil::quickMove);
            return true;
        }
        return false;
    }

    public static void wheelListener(Slot mouseOver) {
        int wheelStatus = Mouse.getDWheel();
        if (wheelStatus == 0) return;
        boolean scrollDown = wheelStatus < 0;
        if (OMMCConfig.WheelMovingInvert.getBooleanValue()) scrollDown = !scrollDown;

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

    public static void tryTradingRestock(GuiMerchant guiMerchant) {
        InventoryTweaks.makeSureNotHoldingItem(EnumSection.InventoryStorage.get());
        ContainerSection merchantIn = EnumSection.MerchantIn.get();
        ItemStack[] required = getRequiredItems(guiMerchant);
        ItemStack firstItem = required[0];
        List<Slot> slots = merchantIn.slots();
        if (firstItem != null) {
            trySupplySlot(slots.get(0), firstItem);
        }
        ItemStack secondItem = required[1];
        if (secondItem != null) {
            trySupplySlot(slots.get(1), secondItem);
        }

    }

    private static void trySupplySlot(Slot slot, ItemStack itemStack) {
        if (slot.getHasStack()) {
            if (slot.getStack().stackSize < slot.getStack().getMaxStackSize()) {
                supplyNonEmptySlot(slot);// double click to gather
            }
        } else {
            supplyEmptySlot(slot, itemStack);// try make it not empty
            if (slot.getHasStack() && slot.getStack().stackSize < slot.getStack().getMaxStackSize()) {
                supplyNonEmptySlot(slot);// if not empty, double click to gather
            }
        }
    }

    private static void supplyNonEmptySlot(Slot slot) {
        InventoryUtil.leftClick(slot);// pick item up
        InventoryUtil.gatherItems(slot);// gather
        InventoryUtil.leftClick(slot);// put item down
    }

    private static void supplyEmptySlot(Slot emptySlot, ItemStack itemStack) {
        Optional<Slot> optionalSlot = EnumSection.InventoryStorage.get().hasItem(itemStack);
        if (optionalSlot.isPresent()) {
            InventoryUtil.moveToEmpty(optionalSlot.get(), emptySlot);
        } else {
            optionalSlot = EnumSection.InventoryHotBar.get().hasItem(itemStack);
            optionalSlot.ifPresent(providerSlot -> InventoryUtil.moveToEmpty(providerSlot, emptySlot));
        }
    }

    public static void runBetterShiftClick(int index, int button) {
        if (button == 0 && OMMCConfig.BetterInventoryMoving.getBooleanValue()) {

            GuiContainer guiContainer = InventoryUtil.getGuiContainer();
            Slot slot = InventoryUtil.getSlots().get(index);

            if (!slot.getHasStack()) return;

            if (guiContainer instanceof GuiCrafting) {

                ContainerSection craftMatrix = EnumSection.CraftMatrix.get();
                if (craftMatrix.hasSlot(slot)) {
                    EnumSection.InventoryWhole.get().moveToEmpty(slot);
                } else {// player inventory
                    craftMatrix.moveToEmpty(slot);
                }

                return;
            }

            if (guiContainer instanceof GuiEnchantment) {

                if (slot.getStack().isEnchantable()) return;// for vanilla clicking
                ContainerSection inventoryStorage = EnumSection.InventoryStorage.get();
                if (inventoryStorage.hasSlot(slot)) {
                    EnumSection.InventoryHotBar.get().moveToEmpty(slot);
                } else {
                    inventoryStorage.moveToEmpty(slot);
                }

                return;
            }

            if (guiContainer instanceof GuiMerchant) {

                ContainerSection merchantInSection = EnumSection.MerchantIn.get();
                if (merchantInSection.hasSlot(slot) || EnumSection.MerchantOut.get().hasSlot(slot)) {
                    return;// for vanilla clicking
                }
                List<Slot> slots = merchantInSection.slots();
                Slot emptyMerchantSlot = slots.get(0);
                if (emptyMerchantSlot.getHasStack()) {
                    emptyMerchantSlot = slots.get(1);
                    if (emptyMerchantSlot.getHasStack()) {
                        return;
                    }
                }
                InventoryUtil.moveToEmpty(slot, emptyMerchantSlot);

            }

        }
    }

    private static ItemStack[] getRequiredItems(GuiMerchant guiMerchant) {
        MerchantRecipeList recipes = guiMerchant.getIMerchant().getRecipes(Minecraft.getMinecraft().thePlayer);
        if (recipes != null && !recipes.isEmpty()) {
            int currentRecipeIndex = guiMerchant.currentRecipeIndex;
            MerchantRecipe merchantRecipe = (MerchantRecipe) recipes.get(currentRecipeIndex);
            ItemStack itemToBuy = merchantRecipe.getItemToBuy();
            ItemStack secondItemToBuy = merchantRecipe.getSecondItemToBuy();
            return new ItemStack[]{itemToBuy, secondItemToBuy};
        }
        return new ItemStack[]{null, null};
    }

    public static void trySwitchToolOrWeapon(boolean isTool) {
        Minecraft minecraft = Minecraft.getMinecraft();
        InventoryPlayer inventory = minecraft.thePlayer.inventory;
        ItemStack heldItemStack = minecraft.thePlayer.getHeldItemStack();
        int slotIndex;
        if (isTool) {
            Block blockHit = minecraft.objectMouseOver.getBlockHit();
            int blockHitMetadata = minecraft.objectMouseOver.block_hit_metadata;
            if (heldItemStack != null && heldItemStack.getItem().isEffectiveAgainstBlock(blockHit, blockHitMetadata)) {
                return;
            }
            slotIndex = getSuitableToolIndex(inventory.mainInventory, blockHit, blockHitMetadata);
        } else {
            if (heldItemStack != null && heldItemStack.getItem() instanceof ItemTool) {
                return;
            }
            slotIndex = getWeaponIndex(inventory.mainInventory);
        }
        PlayerControllerMP player_controller = minecraft.playerController;
        if (slotIndex == -1) return;
        if (slotIndex == inventory.currentItem) return;
        if (slotIndex < InventoryPlayer.getHotbarSize()) {
            inventory.currentItem = slotIndex;
            player_controller.syncCurrentPlayItem();
        } else {
            tryPlaceTargetAtEmptyBar(inventory, slotIndex);
        }
    }

    private static int getSuitableToolIndex(ItemStack[] mainInventory, Block block, int metadata) {
        switch (OMMCConfig.ToolSwitchMode.getEnumValue()) {
            case Order -> {
                for (int i = 0; i < 36; i++) {
                    if (mainInventory[i] == null) continue;
                    if (mainInventory[i].getItem().isEffectiveAgainstBlock(block, metadata)) {
                        return i;
                    }
                }
            }
            case Durability -> {
                int index = -1;
                int durability = 0;
                for (int i = 0; i < 36; i++) {
                    ItemStack itemStack = mainInventory[i];
                    if (itemStack == null) continue;
                    if (itemStack.getItem().isEffectiveAgainstBlock(block, metadata)) {
                        int durability1 = itemStack.getMaxDamage() - itemStack.getItemDamage();
                        if (durability1 > durability) {
                            index = i;
                            durability = durability1;
                        }
                    }
                }
                return index;
            }
            case Material -> {// material
                int index = -1;
                float materialDurability = 0.0F;
                for (int i = 0; i < 36; i++) {
                    ItemStack itemStack = mainInventory[i];
                    if (itemStack == null) continue;
                    if (itemStack.getItem().isEffectiveAgainstBlock(block, metadata)) {
                        float durability1 = itemStack.getMaterialForRepairs().durability;
                        if (durability1 > materialDurability) {
                            index = i;
                            materialDurability = durability1;
                        }
                    }
                }
                return index;
            }
            default -> {
                return -1;
            }
        }
        return -1;
    }


    private static int getWeaponIndex(ItemStack[] mainInventory) {
        for (int i = 0; i < 36; i++) {
            if (mainInventory[i] == null) continue;
            if (mainInventory[i].getItem() instanceof ItemTool) {
                return i;
            }
        }
        return -1;
    }

    private static void tryPlaceTargetAtEmptyBar(InventoryPlayer inventory, int toolIndex) {
        int destination = inventory.currentItem;
        if (inventory.mainInventory[destination] != null) {
            for (int i = 8; i >= 0; i--) {
                if (inventory.mainInventory[i] == null) {
                    destination = i;
                }
            }
        }
        NetClientHandler netHandler = Minecraft.getMinecraft().getNetHandler();
        ItemStack currentItemstack = inventory.getStackInSlot(destination);
        ItemStack toolItemstack = inventory.getStackInSlot(toolIndex);
        inventory.setInventorySlotContents(destination, toolItemstack);
        inventory.setInventorySlotContents(toolIndex, currentItemstack);
        netHandler.addToSendQueue(new Packet5PlayerInventory(inventory.player.entityId, destination, toolItemstack));
        netHandler.addToSendQueue(new Packet5PlayerInventory(inventory.player.entityId, toolIndex, currentItemstack));
    }
}
