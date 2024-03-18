package com.github.Debris.oh_my_mite_client.mixins.gui;

import com.github.Debris.oh_my_mite_client.config.TweakToggle;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GuiControls.class)
public abstract class GuiControlsMixin extends GuiScreen {
    @Shadow
    private GameSettings options;
    @Shadow
    protected String screenTitle;

    @Shadow
    protected abstract boolean isKeybindButtonVisible(int index);

    @Shadow
    private int buttonId;

    @Shadow
    protected abstract int getKeybindButtonPosX(int index);

    @Shadow
    protected abstract int getKeybindButtonPosY(int index);
//    @Overwrite
    public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 20, 16777215);
        int var5 = 0;

        while (true) {
            while (var5 < this.options.keyBindings.length) {
                if (!this.isKeybindButtonVisible(var5)) {
                    ++var5;
                } else {
                    boolean var6 = false;

                    for (int var7 = 0; var7 < this.options.keyBindings.length; ++var7) {
                        if (var7 != var5 && this.options.keyBindings[var5].keyCode == this.options.keyBindings[var7].keyCode) {
                            var6 = true;
                            break;
                        }
                    }
                    for (TweakToggle value : TweakToggle.VALUES) {
                        if (value.getKeybind().keyCode == this.options.keyBindings[var5].keyCode) var6 = true;
                        break;
                    }

                    if (this.buttonId == var5) {
                        ((GuiButton) this.buttonList.get(var5)).displayString = "" + EnumChatFormatting.WHITE + "> " + EnumChatFormatting.YELLOW + "??? " + EnumChatFormatting.WHITE + "<";
                    } else if (var6) {
                        ((GuiButton) this.buttonList.get(var5)).displayString = EnumChatFormatting.RED + this.options.getOptionDisplayString(var5);
                    } else {
                        ((GuiButton) this.buttonList.get(var5)).displayString = this.options.getOptionDisplayString(var5);
                    }

                    this.drawString(this.fontRenderer, this.options.getKeyBindingDescription(var5), this.getKeybindButtonPosX(var5) + 70 + 6, this.getKeybindButtonPosY(var5) + 7, -1);
                    ++var5;
                }
            }

            super.drawScreen(par1, par2, par3);
            return;
        }
    }
}
