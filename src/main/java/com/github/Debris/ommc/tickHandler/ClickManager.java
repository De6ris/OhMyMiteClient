package com.github.Debris.ommc.tickHandler;

import com.github.Debris.ommc.config.OMMCConfig;
import fi.dy.masa.malilib.interfaces.IClientTickHandler;
import net.minecraft.Minecraft;

public class ClickManager implements IClientTickHandler {
    private static final ClickManager Instance = new ClickManager();
    private int leftClickCounter = 0;
    private int rightClickCounter = 0;

    public static ClickManager getInstance() {
        return Instance;
    }

    @Override
    public void onClientTick(Minecraft mc) {
        if (mc.theWorld == null) return;
        if (OMMCConfig.PeriodicAttack.isOn()) {
            this.periodicAttack(mc);
        }
        if (OMMCConfig.PeriodicUse.isOn()) {
            this.periodicUse(mc);
        }
    }

    private void periodicAttack(Minecraft mc) {
        if (this.leftClickCounter >= OMMCConfig.PeriodicAttackInterval.getIntegerValue()) {
            this.leftClick(mc);
            this.leftClickCounter = 0;
        } else {
            this.leftClickCounter++;
        }
    }

    private void periodicUse(Minecraft mc) {
        if (this.rightClickCounter >= OMMCConfig.PeriodicUseInterval.getIntegerValue()) {
            this.rightClick(mc);
            this.rightClickCounter = 0;
        } else {
            this.rightClickCounter++;
        }
    }

    private void leftClick(Minecraft mc) {
        mc.clickMouse(0);
    }

    private void rightClick(Minecraft mc) {
        mc.clickMouse(1);
    }
}
