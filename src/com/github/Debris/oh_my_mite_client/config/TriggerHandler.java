package com.github.Debris.oh_my_mite_client.config;

import net.minecraft.GuiChat;
import net.minecraft.Minecraft;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class TriggerHandler {

    private static final TriggerHandler INSTANCE = new TriggerHandler();

    public static TriggerHandler getInstance() {
        return INSTANCE;
    }

    public void handle(TweakToggle tweakToggle, Minecraft minecraft) {
        switch (tweakToggle) {
            case Tweak_CopyTP: {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                String content = "/tp " + minecraft.thePlayer.posX + " " + minecraft.thePlayer.posY + " " + minecraft.thePlayer.posZ;
                StringSelection selection = new StringSelection(content);
                clipboard.setContents(selection, null);
                minecraft.thePlayer.addChatMessage("tp命令已复制到剪贴板");
                return;
            }
            case Tweak_ToggleMode: {
                int mode = FishConfig.gameMode ^= 1;
                minecraft.openChat(new GuiChat("/gamemode " + mode));
                return;
            }
            default: {
            }
        }
    }
}
