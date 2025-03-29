package com.github.Debris.ommc.tickHandler;

import com.github.Debris.ommc.config.OMMCConfig;
import com.github.Debris.ommc.task.ClickQuitTask;
import fi.dy.masa.malilib.interfaces.IClientTickHandler;
import net.minecraft.Minecraft;

public class QuitGameManager implements IClientTickHandler {
    private static final QuitGameManager Instance = new QuitGameManager();
    private boolean drawn;
    private float healthBefore = Float.MAX_VALUE;

    private int nextQuitCounter = OMMCConfig.QuitGameCounter.getIntegerValue() * 20;

    public static QuitGameManager getInstance() {
        return Instance;
    }

    public boolean shouldDrawTip() {
        return this.nextQuitCounter < OMMCConfig.QuitGameCounter.getIntegerValue() * 20 && !this.drawn;
    }

    public void markAsDrawn() {
        this.drawn = true;
    }

    public int getSeconds() {
        return OMMCConfig.QuitGameCounter.getIntegerValue() - (this.nextQuitCounter / 20);
    }

    private void checkDanger(Minecraft minecraftClient, float healthNow) {
        if (healthNow <= OMMCConfig.QuitGameThreshold.getDoubleValue() && OMMCConfig.AutoQuitGame.getBooleanValue() && this.nextQuitCounter >= OMMCConfig.QuitGameCounter.getIntegerValue() * 20) {
            minecraftClient.displayInGameMenu();
            TaskManager.getInstance().addTimedTask(new ClickQuitTask(OMMCConfig.QuitGameSpeed.getIntegerValue()));
            this.nextQuitCounter = 0;
        }
    }

    @Override
    public void onClientTick(Minecraft minecraft) {
        this.nextQuitCounter++;
        if (minecraft.theWorld == null || minecraft.thePlayer.inCreativeMode()) return;
        float healthNow = minecraft.thePlayer.getHealth();
        if (healthNow != this.healthBefore) {
            if (healthNow < this.healthBefore) {
                this.checkDanger(minecraft, healthNow);
            }
            this.healthBefore = healthNow;
        }
    }
}
