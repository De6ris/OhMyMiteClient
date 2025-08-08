package com.github.debris.ommc.mixins.gui;

import com.github.debris.ommc.inventory.section.SectionHandler;
import net.minecraft.Container;
import net.minecraft.CreativeTabs;
import net.minecraft.GuiContainerCreative;
import net.minecraft.InventoryEffectRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GuiContainerCreative.class, priority = 999)
public abstract class GuiContainerCreativeMixin extends InventoryEffectRenderer {
    public GuiContainerCreativeMixin(Container par1Container) {
        super(par1Container);
    }

    @Inject(method = "setCurrentCreativeTab", at = @At("RETURN"))
    private void update(CreativeTabs par1CreativeTabs, CallbackInfo ci) {
        SectionHandler.updateSection(this);
    }
}
