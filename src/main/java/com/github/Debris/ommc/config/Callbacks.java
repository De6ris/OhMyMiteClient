package com.github.Debris.ommc.config;

import com.github.Debris.ommc.feat.SortInventory;
import com.github.Debris.ommc.feat.TradingRestock;
import com.github.Debris.ommc.util.Misc;
import fi.dy.masa.malilib.config.interfaces.IValueChangeCallback;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.config.options.ConfigToggle;
import fi.dy.masa.malilib.render.RenderUtils;
import net.minecraft.*;

public class Callbacks {

    public static void init(Minecraft minecraft) {
        OMMCConfig.OpenWindow.getKeybind().setCallback((keyAction, iKeybind) -> {
            Minecraft.getMinecraft().displayGuiScreen(OMMCConfig.getInstance().getConfigScreen(null));
            return true;
        });

        OMMCConfig.HoldUse.setValueChangeCallback(new FeatureCallbackHold(minecraft.gameSettings.keyBindUseItem));
        OMMCConfig.HoldAttack.setValueChangeCallback(new FeatureCallbackHold(minecraft.gameSettings.keyBindAttack));

        OMMCConfig.CopyTP.getKeybind().setCallback((keyAction, iKeybind) -> {
            Misc.copyToClipboard("/tp " + minecraft.thePlayer.posX + " " + minecraft.thePlayer.posY + " " + minecraft.thePlayer.posZ);
            RenderUtils.setGuiIngameInfo(I18n.getString("ommc.CopyTP.success"));
            return true;
        });
        OMMCConfig.ToggleGameMode.getKeybind().setCallback((keyAction, iKeybind) -> {
            if (Minecraft.inDevMode()) {
                minecraft.thePlayer.sendChatMessage("/gamemode " + ((minecraft.thePlayer).isPlayerInCreative() ? 0 : 1));
            } else {
                RenderUtils.setGuiIngameInfo(I18n.getString("ommc.toggleGameMode.fail"));
            }
            return true;
        });
        OMMCConfig.GammaOverride.setValueChangeCallback(configBoolean -> minecraft.gameSettings.gammaSetting = configBoolean.getBooleanValue() ? 15.0F : 1.0F);
        OMMCConfig.Test.getKeybind().setCallback((keyAction, iKeybind) -> {
            ItemStack itemStack = new ItemStack(Block.anvilAdamantium);
            minecraft.getNetHandler().addToSendQueue(new Packet5PlayerInventory(minecraft.thePlayer.entityId, minecraft.thePlayer.inventory.currentItem, itemStack));
            RenderUtils.setGuiIngameInfo("已发包");
            return true;
        });

        OMMCConfig.SortItem.getKeybind().setCallback((keyAction, iKeybind) -> {
            if (OMMCConfig.ShouldTweakInventory.getBooleanValue() && Minecraft.getMinecraft().currentScreen instanceof GuiContainer) {
                minecraft.sndManager.playSoundFX("random.click", 1.0f, 1.0f);
                return SortInventory.trySort();
            }
            return false;
        });

        OMMCConfig.TradingRestock.getKeybind().setCallback((keyAction, iKeybind) -> {
            if (OMMCConfig.ShouldTweakInventory.getBooleanValue() && Minecraft.getMinecraft().currentScreen instanceof GuiMerchant guiMerchant) {
                TradingRestock.tryTradingRestock(guiMerchant);
                minecraft.sndManager.playSoundFX("random.click", 1.0f, 1.0f);
                return true;
            }
            return false;
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
