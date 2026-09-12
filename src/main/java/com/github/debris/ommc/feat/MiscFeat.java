package com.github.debris.ommc.feat;

import com.github.debris.ommc.ModReference;
import com.github.debris.ommc.unsafe.EmiAccess;
import fi.dy.masa.malilib.render.RenderUtils;
import net.minecraft.I18n;
import net.minecraft.ItemStack;
import net.minecraft.Minecraft;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class MiscFeat {
    public static boolean copyItemID(Minecraft client) {
        if (ModReference.hasMod(ModReference.EMI)) {
            ItemStack itemStack = EmiAccess.getHoverItemStack();
            if (itemStack == null) return false;

            String name = itemStack.getItem().getUnlocalizedName();
            copyToClipboard(name);
            return true;
        }

        return false;
    }

    public static void toggleGameMode(Minecraft client) {
        if (Minecraft.inDevMode()) {
            client.thePlayer.sendChatMessage("/gamemode " + ((client.thePlayer).isPlayerInCreative() ? 0 : 1));
        } else {
            RenderUtils.setGuiIngameInfo(I18n.getString("ommc.toggleGameMode.fail"));
        }
    }

    public static void copyToClipboard(String content) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection selection = new StringSelection(content);
        clipboard.setContents(selection, null);
    }
}
