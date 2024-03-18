package com.github.Debris.oh_my_mite_client.mixins;

import com.github.Debris.oh_my_mite_client.config.FishConfig;
import com.github.Debris.oh_my_mite_client.config.TweakToggle;
import com.github.Debris.oh_my_mite_client.config.TriggerHandler;
import net.minecraft.EntityClientPlayerMP;
import net.minecraft.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.Minecraft.getSystemTime;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Shadow
    protected abstract void clickMouse(int button);

    @Shadow
    public EntityClientPlayerMP thePlayer;

    @Shadow
    public static Minecraft getMinecraft() {
        return null;
    }

    @Inject(method = "runTick", at = @At("TAIL"))
    private void inject(CallbackInfo ci) {
        for (TweakToggle value : TweakToggle.VALUES) {
            if (value.isTrigger()) {
                if (value.getKeybind().isPressed()) {
                    TriggerHandler.getInstance().handle(value, getMinecraft());
                }
            } else {
                if (value.getKeybind().isPressed()) {
                    value.invert();
                    TweakToggle.save();
                    String message = String.format("功能 %s 已切换为: %s", value.getKeybind().keyDescription, value.getBooleanValue() ? "开" : "关");
                    this.thePlayer.addChatMessage(message);
                }
            }
        }


        if (TweakToggle.Tweak_AutoAttack.getBooleanValue() &&
                (getSystemTime() % FishConfig.ATTACK_INTERVAL.get()) <= 50) {
            this.clickMouse(0);
        }
        if (TweakToggle.Tweak_HoldUse.getBooleanValue()) {
            this.clickMouse(1);
        }
    }

}
