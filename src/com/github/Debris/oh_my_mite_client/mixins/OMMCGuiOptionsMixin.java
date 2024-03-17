package com.github.Debris.oh_my_mite_client.mixins;

import com.github.Debris.oh_my_mite_client.config.OMMCGuiControls;
import com.github.Debris.oh_my_mite_client.config.FieldAccessor;
import net.minecraft.GuiButton;
import net.minecraft.GuiOptions;
import net.minecraft.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiOptions.class)
public abstract class OMMCGuiOptionsMixin extends GuiScreen {
    @Inject(method = "initGui", at = @At("TAIL"))
    private void registerButton(CallbackInfo ci) {
        this.buttonList.add(new GuiButton(111, this.width / 2 + 2, this.height / 6 + 72 - 6, 150, 20, "OMMC"));
    }

    @Inject(method = "actionPerformed", at = @At("TAIL"))
    private void onClick(GuiButton par1GuiButton, CallbackInfo ci) {
        if (par1GuiButton.enabled) {
            if (par1GuiButton.id == 111) {
                this.mc.gameSettings.saveOptions();
                ((FieldAccessor) this.mc.gameSettings).OMMCOptionsProvider().saveOptions();
                this.mc.displayGuiScreen(new OMMCGuiControls(this, ((FieldAccessor) this.mc.gameSettings).OMMCOptionsProvider()));
            }
        }
    }
}
