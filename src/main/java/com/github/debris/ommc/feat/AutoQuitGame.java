package com.github.debris.ommc.feat;

import com.github.debris.ommc.config.MainConfig;
import com.github.debris.ommc.event.tick.TaskManager;
import com.github.debris.ommc.task.ClickQuitTask;
import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.render.RenderUtils;
import net.minecraft.Minecraft;

public class AutoQuitGame {
    private static boolean drawn;
    private static float healthBefore = Float.MAX_VALUE;

    private static int nextQuitCounter = MainConfig.QuitGameCounter.getIntegerValue() * 20;

    public static boolean shouldDrawTip() {
        return nextQuitCounter < MainConfig.QuitGameCounter.getIntegerValue() * 20 && !drawn;
    }

    public static int getSeconds() {
        return MainConfig.QuitGameCounter.getIntegerValue() - (nextQuitCounter / 20);
    }

    private static void checkDanger(Minecraft minecraftClient, float healthNow) {
        if (healthNow <= MainConfig.QuitGameThreshold.getDoubleValue() && MainConfig.AutoQuitGame.getBooleanValue() && nextQuitCounter >= MainConfig.QuitGameCounter.getIntegerValue() * 20) {
            minecraftClient.displayInGameMenu();
            TaskManager.getInstance().addTimedTask(new ClickQuitTask(MainConfig.QuitGameSpeed.getIntegerValue()));
            nextQuitCounter = 0;
        }
    }

    public static void onMenuRender() {
        if (shouldDrawTip()) {
            String info = String.format("OhMyMiteClient:\\n 因血量过低, 自动退出了游戏\\n 在%d秒内自动退出功能不再生效", getSeconds());
            RenderUtils.drawCreativeTabHoveringText(info, 5, 25, new DrawContext());
        }
    }

    public static void onMenuClick() {
        if (shouldDrawTip()) {
            drawn = true;
        }
    }

    public static void onClientTick(Minecraft minecraft) {
        nextQuitCounter++;
        if (minecraft.theWorld == null || minecraft.thePlayer.inCreativeMode()) return;
        float healthNow = minecraft.thePlayer.getHealth();
        if (healthNow != healthBefore) {
            if (healthNow < healthBefore) {
                checkDanger(minecraft, healthNow);
            }
            healthBefore = healthNow;
        }
    }
}
