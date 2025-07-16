package com.github.Debris.ommc.mixins.gui;

import com.github.Debris.ommc.config.MainConfig;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.FontRenderer;
import net.minecraft.GuiIngame;
import net.minecraft.ScoreObjective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GuiIngame.class)
public class GuiIngameMixin {
    @WrapWithCondition(method = "renderGameOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraft/GuiIngame;func_96136_a(Lnet/minecraft/ScoreObjective;IILnet/minecraft/FontRenderer;)V"))
    private boolean cancelScoreBoardRender(GuiIngame instance, ScoreObjective var10, int var11, int var8, FontRenderer var21) {
        return !MainConfig.DisableScoreBoard.getBooleanValue();
    }
}
