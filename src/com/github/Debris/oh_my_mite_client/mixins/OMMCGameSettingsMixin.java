package com.github.Debris.oh_my_mite_client.mixins;

import com.github.Debris.oh_my_mite_client.config.OMMCOptions;
import com.github.Debris.oh_my_mite_client.config.FieldAccessor;
import net.minecraft.GameSettings;
import net.minecraft.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(GameSettings.class)
public class OMMCGameSettingsMixin implements FieldAccessor {

    @Unique
    OMMCOptions ohMyMiteClient$OMMCOptions;

    @Inject(method = "<init>(Lnet/minecraft/Minecraft;Ljava/io/File;)V", at = @At("TAIL"))
    private void loadOMMCOptions(Minecraft par1Minecraft, File par2File, CallbackInfo ci) {
        this.ohMyMiteClient$OMMCOptions = new OMMCOptions(par2File);
    }

    @Override
    public OMMCOptions OMMCOptionsProvider() {
        return this.ohMyMiteClient$OMMCOptions;
    }
}
