package com.github.debris.ommc.config.options;

import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.util.Color4f;
import net.minecraft.EnumChatFormatting;
import net.xiaoyu233.fml.FishModLoader;

public class ConfigBooleanWithDependency extends ConfigBoolean {
    private final String modId;

    public ConfigBooleanWithDependency(String name, String modId, String comment) {
        super(name, comment);
        this.modId = modId;
    }

    @Override
    public Color4f getDisplayColor() {
        return this.getBooleanValue() && !FishModLoader.hasMod(this.modId) ? Color4f.fromColor(EnumChatFormatting.RED.rgb) : super.getDisplayColor();
    }
}
