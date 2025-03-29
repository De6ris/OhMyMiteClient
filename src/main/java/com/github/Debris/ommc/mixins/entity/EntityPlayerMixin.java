package com.github.Debris.ommc.mixins.entity;

import com.github.Debris.ommc.config.OMMCConfig;
import net.minecraft.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(EntityPlayer.class)
public class EntityPlayerMixin {
    @ModifyConstant(method = "tryPlaceHeldItemAsBlock", constant = @Constant(intValue = 250, ordinal = 0))
    private int modifyRightClickInterval(int constant) {
        return OMMCConfig.OverrideUseInterval.getIntegerValue();
    }
}
