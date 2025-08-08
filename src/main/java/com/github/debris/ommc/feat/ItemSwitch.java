package com.github.debris.ommc.feat;

import com.github.debris.ommc.config.InventoryConfig;
import net.minecraft.*;

public class ItemSwitch {
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
        switch (InventoryConfig.ToolSwitchMode.getEnumValue()) {
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
