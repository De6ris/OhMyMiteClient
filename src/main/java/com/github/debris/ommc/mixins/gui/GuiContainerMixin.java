package com.github.debris.ommc.mixins.gui;

import com.github.debris.ommc.config.MainConfig;
import net.minecraft.GuiContainer;
import net.minecraft.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(GuiContainer.class)
public abstract class GuiContainerMixin extends GuiScreen {
    @ModifyConstant(method = "onGuiClosed", constant = @Constant(longValue = 1000L))
    private long removeCD(long constant) {
        if (MainConfig.RemoveGuiContainerCD.getBooleanValue()) {
            return 0L;
        } else {
            return constant;
        }
    }
}
