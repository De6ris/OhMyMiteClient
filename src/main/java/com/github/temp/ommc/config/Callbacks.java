package com.github.temp.ommc.config;

import com.github.temp.ommc.feat.SortInventory;
import com.github.temp.ommc.feat.TradingRestock;
import com.github.temp.ommc.inventory.InventoryTweaks;
import com.github.temp.ommc.util.Misc;
import com.github.temp.ommc.util.Predicates;
import fi.dy.masa.malilib.config.interfaces.IValueChangeCallback;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.config.options.ConfigToggle;
import fi.dy.masa.malilib.render.RenderUtils;
import net.minecraft.*;

public class Callbacks {

    public static void init(Minecraft client) {
        MainConfig.OpenWindow.getKeybind().setCallback((keyAction, iKeybind) -> {
            Minecraft.getMinecraft().displayGuiScreen(MainConfig.getInstance().getConfigScreen(null));
            return true;
        });

        MainConfig.OpenModule_Inventory.getKeybind().setCallback((keyAction, iKeybind) -> {
            Minecraft.getMinecraft().displayGuiScreen(InventoryConfig.getInstance().getConfigScreen(null));
            return true;
        });

        MainConfig.HoldUse.setValueChangeCallback(new FeatureCallbackHold(client.gameSettings.keyBindUseItem));
        MainConfig.HoldAttack.setValueChangeCallback(new FeatureCallbackHold(client.gameSettings.keyBindAttack));

        MainConfig.CopyTP.getKeybind().setCallback((keyAction, iKeybind) -> {
            Misc.copyToClipboard("/tp " + client.thePlayer.posX + " " + client.thePlayer.posY + " " + client.thePlayer.posZ);
            RenderUtils.setGuiIngameInfo(I18n.getString("ommc.CopyTP.success"));
            return true;
        });
        MainConfig.ToggleGameMode.getKeybind().setCallback((keyAction, iKeybind) -> {
            if (Minecraft.inDevMode()) {
                client.thePlayer.sendChatMessage("/gamemode " + ((client.thePlayer).isPlayerInCreative() ? 0 : 1));
            } else {
                RenderUtils.setGuiIngameInfo(I18n.getString("ommc.toggleGameMode.fail"));
            }
            return true;
        });
        MainConfig.GammaOverride.setValueChangeCallback(configBoolean -> client.gameSettings.gammaSetting = configBoolean.getBooleanValue() ? 15.0F : 1.0F);
        MainConfig.Test.getKeybind().setCallback((keyAction, iKeybind) -> {
            ItemStack itemStack = new ItemStack(Block.anvilAdamantium);
            client.getNetHandler().addToSendQueue(new Packet5PlayerInventory(client.thePlayer.entityId, client.thePlayer.inventory.currentItem, itemStack));
            RenderUtils.setGuiIngameInfo("已发包");
            return true;
        });

        InventoryConfig.SortItem.getKeybind().setCallback((keyAction, iKeybind) -> {
            if (!InventoryTweaks.isActive()) return false;
            if (Predicates.notInGuiContainer(client)) return false;
            client.sndManager.playSoundFX("random.click", 1.0f, 1.0f);
            return SortInventory.trySort();
        });

        InventoryConfig.TradingRestock.getKeybind().setCallback((keyAction, iKeybind) -> {
            if (!InventoryTweaks.isActive()) return false;
            if (Minecraft.getMinecraft().currentScreen instanceof GuiMerchant guiMerchant) {
                TradingRestock.tryTradingRestock(guiMerchant);
                client.sndManager.playSoundFX("random.click", 1.0f, 1.0f);
                return true;
            }
            return false;
        });

        InventoryConfig.ThrowSection.getKeybind().setCallback((keyAction, iKeybind) -> {
            if (!InventoryTweaks.isActive()) return false;
            if (Predicates.notInGuiContainer(client)) return false;
            return InventoryTweaks.tryThrowSection();
        });
    }

    private static class FeatureCallbackHold implements IValueChangeCallback<ConfigHotkey> {
        KeyBinding keyBinding;

        public FeatureCallbackHold(KeyBinding keyBinding) {
            this.keyBinding = keyBinding;
        }

        @Override
        public void onValueChanged(ConfigHotkey config) {
            if (((ConfigToggle) config).isOn()) {
                this.keyBinding.pressTime = Integer.MAX_VALUE;
                this.keyBinding.pressed = true;
            } else {
                this.keyBinding.pressTime = 0;
                this.keyBinding.pressed = false;
            }
        }
    }
}
