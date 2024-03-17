package com.github.Debris.oh_my_mite_client.mixins;

import com.github.Debris.oh_my_mite_client.config.Config;
import com.github.Debris.oh_my_mite_client.config.FeatureToggle;
import net.minecraft.PlayerCapabilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerCapabilities.class)
public class OMMCPlayerAbilitiesMixin {
    @Inject(method = "getFlySpeed", at = @At("HEAD"), cancellable = true)
    private void inject(CallbackInfoReturnable<Float> cir) {
        if (FeatureToggle.Tweak_FastFlying.getBooleanValue()) {
            cir.setReturnValue(Config.FLYING_SPEED.get());
        }
    }
}
