package com.github.Debris.oh_my_mite_client.mixins;

import com.github.Debris.oh_my_mite_client.config.FishConfig;
import com.github.Debris.oh_my_mite_client.config.TweakToggle;
import net.minecraft.AbstractClientPlayer;
import net.minecraft.ClientPlayer;
import net.minecraft.MovementInput;
import net.minecraft.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayer.class)
public abstract class ClientPlayerMixin extends AbstractClientPlayer {
    @Shadow
    public MovementInput movementInput;

    public ClientPlayerMixin(World par1World, String par2Str) {
        super(par1World, par2Str);
    }

    @Inject(method = "onLivingUpdate", at = @At("TAIL"))
    private void inject(CallbackInfo ci) {
        if (this.capabilities.isFlying && TweakToggle.Tweak_FastFlying.getBooleanValue()) {
            if (this.movementInput.sneak) {
                this.motionY -= FishConfig.FLYING_SPEED_VERTICAL.get();
            }
            if (this.movementInput.jump) {
                this.motionY += FishConfig.FLYING_SPEED_VERTICAL.get();
            }
        }
    }
}
