package com.github.Debris.ommc.mixins.gui;

import com.github.Debris.ommc.config.InventoryConfig;
import com.github.Debris.ommc.config.MainConfig;
import com.github.Debris.ommc.inventory.InventoryTweaks;
import com.github.Debris.ommc.inventory.InventoryUtil;
import com.github.Debris.ommc.inventory.section.SectionHandler;
import com.github.Debris.ommc.util.ItemUtil;
import net.minecraft.GuiContainer;
import net.minecraft.GuiScreen;
import net.minecraft.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiContainer.class)
public abstract class GuiContainerMixin extends GuiScreen {
    @Shadow
    public Slot theSlot;

    @Inject(method = "mouseClicked", at = @At(value = "INVOKE", target = "Lnet/minecraft/GuiScreen;mouseClicked(III)V", shift = At.Shift.AFTER), cancellable = true)
    private void preTricks(int par1, int par2, int button, CallbackInfo ci) {
        if (button == 0 && InventoryTweaks.shouldCancelLeftClick(this.theSlot)) {
            ci.cancel();
        }
    }

    @Inject(method = "drawScreen", at = @At("RETURN"))
    private void onRender(int par1, int par2, float par3, CallbackInfo ci) {
        InventoryTweaks.onRender((GuiContainer) (Object) this, par1, par2, this.theSlot);
    }


    @Inject(method = "keyTyped", at = @At("HEAD"))
    private void dropAll(char par1, int par2, CallbackInfo ci) {
        Slot mouseOver = this.theSlot;
        if (InventoryTweaks.shouldDoTrick(mouseOver) && InventoryConfig.DropSimilar.getKeybind().isKeybindHeld()) {
            SectionHandler.getSection(mouseOver).predicateRun(ItemUtil.predicateIDMeta(mouseOver.getStack()), InventoryUtil::dropStack);
        }
    }

    @ModifyConstant(method = "onGuiClosed", constant = @Constant(longValue = 1000L))
    private long removeCD(long constant) {
        if (MainConfig.RemoveGuiContainerCD.getBooleanValue()) {
            return 0L;
        } else {
            return constant;
        }
    }
}
