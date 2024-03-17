package com.github.Debris.oh_my_mite_client.mixins;

import com.github.Debris.oh_my_mite_client.config.Config;
import com.github.Debris.oh_my_mite_client.config.KeyBindingBoolean;
import com.github.Debris.oh_my_mite_client.config.FieldAccessor;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import static net.minecraft.Minecraft.getSystemTime;

@Mixin(Minecraft.class)
public abstract class OMMCMinecraftMixin {
    @Shadow
    public GameSettings gameSettings;

    @Shadow
    protected abstract void clickMouse(int button);

    @Shadow
    public EntityClientPlayerMP thePlayer;

    @Shadow
    public abstract void openChat(GuiChat gui_chat);

    @Inject(method = "runTick", at = @At("TAIL"))
    private void inject(CallbackInfo ci) {
        for (KeyBindingBoolean key : ((FieldAccessor) this.gameSettings).OMMCOptionsProvider().keyBindingBooleans) {
            if (key.isPressed()) key.invert();
        }
        if (((FieldAccessor) this.gameSettings).OMMCOptionsProvider().keyBindCopyTP.isPressed()) {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            String content = "/tp " + thePlayer.posX + " " + thePlayer.posY + " " + thePlayer.posZ;
            StringSelection selection = new StringSelection(content);
            clipboard.setContents(selection, null);
        }
        if (((FieldAccessor) this.gameSettings).OMMCOptionsProvider().keyBindToggleMode.isPressed()) {
            int mode = ((FieldAccessor) this.gameSettings).OMMCOptionsProvider().gameMode ^= 1;
            this.openChat(new GuiChat("/gamemode " + mode));
        }
        if (((FieldAccessor) this.gameSettings).OMMCOptionsProvider().keyBindAutoAttack.getBoolean() &&
                (getSystemTime() % Config.ATTACK_INTERVAL.get()) <= 50) {
            this.clickMouse(0);
        }
        if (((FieldAccessor) this.gameSettings).OMMCOptionsProvider().keyBindHoldUse.getBoolean()) {
            this.clickMouse(1);
        }
    }

}
