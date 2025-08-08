package com.github.temp.ommc.event.tick;

import com.github.temp.ommc.config.MainConfig;
import com.github.temp.ommc.task.ClickQuitTask;
import fi.dy.masa.malilib.interfaces.IClientTickHandler;
import net.minecraft.Minecraft;

public class QuitGameManager implements IClientTickHandler {
    private static final QuitGameManager Instance = new QuitGameManager();
    private boolean drawn;
    private float healthBefore = Float.MAX_VALUE;

    private int nextQuitCounter = MainConfig.QuitGameCounter.getIntegerValue() * 20;

    public static QuitGameManager getInstance() {
        return Instance;
    }

    public boolean shouldDrawTip() {
        return this.nextQuitCounter < MainConfig.QuitGameCounter.getIntegerValue() * 20 && !this.drawn;
    }

    public void markAsDrawn() {
        this.drawn = true;
    }

    public int getSeconds() {
        return MainConfig.QuitGameCounter.getIntegerValue() - (this.nextQuitCounter / 20);
    }

    private void checkDanger(Minecraft minecraftClient, float healthNow) {
        if (healthNow <= MainConfig.QuitGameThreshold.getDoubleValue() && MainConfig.AutoQuitGame.getBooleanValue() && this.nextQuitCounter >= MainConfig.QuitGameCounter.getIntegerValue() * 20) {
            minecraftClient.displayInGameMenu();
            TaskManager.getInstance().addTimedTask(new ClickQuitTask(MainConfig.QuitGameSpeed.getIntegerValue()));
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
