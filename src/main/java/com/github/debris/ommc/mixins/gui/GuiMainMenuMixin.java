package com.github.debris.ommc.mixins.gui;

import com.github.debris.ommc.event.tick.QuitGameManager;
import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.render.RenderUtils;
import net.minecraft.GuiMainMenu;
import net.minecraft.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMainMenu.class)
public abstract class GuiMainMenuMixin extends GuiScreen {
    @Shadow
    public abstract void drawScreen(int par1, int par2, float par3);

    @Inject(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/Minecraft;getVersionDescriptor(Z)Ljava/lang/String;", shift = At.Shift.BEFORE))
    private void drawOMMCQuitInfo(int par1, int par2, float par3, CallbackInfo ci) {
        QuitGameManager quitGameManager = QuitGameManager.getInstance();
        if (quitGameManager.shouldDrawTip()) {
            String info = String.format("OhMyMiteClient:\\n 因血量过低, 自动退出了游戏\\n 在%d秒内自动退出功能不再生效", quitGameManager.getSeconds());
            RenderUtils.drawCreativeTabHoveringText(info, 5, 25, new DrawContext());
        }
    }

    @Inject(method = "mouseClicked", at = @At("RETURN"))
    private void cancelDrawFlag(int par1, int par2, int par3, CallbackInfo ci) {
        QuitGameManager quitGameManager = QuitGameManager.getInstance();
        if (quitGameManager.shouldDrawTip()) {
            quitGameManager.markAsDrawn();
        }
    }
}
