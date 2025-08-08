package com.github.debris.ommc.mixins.container;

import com.github.debris.ommc.inventory.section.IContainer;
import com.github.debris.ommc.inventory.section.SectionHandler;
import net.minecraft.Container;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Container.class)
public class ContainerMixin implements IContainer {
    @Unique
    private SectionHandler sectionHandler;

    @Override
    public void dc$setSectionHandler(SectionHandler sectionHandler) {
        this.sectionHandler = sectionHandler;
    }

    @Override
    public SectionHandler dc$getSectionHandler() {
        return this.sectionHandler;
    }

    @Override
    public String dc$getTypeString() {
        return "<no type>";
    }
}
