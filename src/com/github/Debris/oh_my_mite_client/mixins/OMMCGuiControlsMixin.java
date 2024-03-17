package com.github.Debris.oh_my_mite_client.mixins;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GuiControls.class)
public abstract class OMMCGuiControlsMixin extends GuiScreen {
//    @Shadow
//    private GameSettings options;
//
//    @Inject(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/GuiControls;drawString(Lnet/minecraft/FontRenderer;Ljava/lang/String;III)V"), locals = LocalCapture.CAPTURE_FAILSOFT)
//    private void inject(int var5) {
//        boolean conflict = false;
//        for (int var7 = 0; var7 < ((FieldAccessor) this).OMMCOptionsProvider().allKeyBindings.length; ++var7) {
//            if (this.options.keyBindings[var5].keyCode == ((FieldAccessor) this).OMMCOptionsProvider().allKeyBindings[var7].keyCode) {
//                conflict = true;
//                break;
//            }
//        }
//        if (conflict) {
//            ((GuiButton) this.buttonList.get(var5)).displayString = EnumChatFormatting.RED + this.options.getOptionDisplayString(var5);
//        }
//    }
}
