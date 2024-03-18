package com.github.Debris.oh_my_mite_client.mixins;

import com.github.Debris.oh_my_mite_client.config.TweakToggle;
import net.minecraft.GameSettings;
import net.minecraft.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(GameSettings.class)
public class GameSettingsMixin {
    @Inject(method = "<init>(Lnet/minecraft/Minecraft;Ljava/io/File;)V", at = @At("TAIL"))
    private void loadConfig(Minecraft par1Minecraft, File par2File, CallbackInfo ci) {
        TweakToggle.setPath(par2File);
        TweakToggle.load();
    }
}
