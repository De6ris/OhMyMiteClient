package com.github.Debris.oh_my_mite_client;

import com.github.Debris.oh_my_mite_client.config.FishConfig;
import com.github.Debris.oh_my_mite_client.mixins.MixinMarker;
import net.xiaoyu233.fml.AbstractMod;
import net.xiaoyu233.fml.classloading.Mod;
import net.xiaoyu233.fml.config.ConfigRegistry;
import net.xiaoyu233.fml.config.InjectionConfig;
import net.xiaoyu233.fml.reload.event.MITEEvents;
import org.spongepowered.asm.mixin.MixinEnvironment;

import javax.annotation.Nonnull;
import java.io.File;

@Mod({MixinEnvironment.Side.CLIENT})
public class OhMyMiteClient extends AbstractMod {

    public static final String MOD_ID = "OhMyMiteClient";

    private static final ConfigRegistry CONFIG_REGISTRY = new ConfigRegistry(FishConfig.ROOT, new File(MOD_ID + ".json"));


    @Override
    public void preInit() {
    }

    @Nonnull
    @Override
    public InjectionConfig getInjectionConfig() {
        return InjectionConfig.Builder.of(MOD_ID, MixinMarker.class.getPackage(), MixinEnvironment.Phase.INIT).setRequired().build();
    }

    @Override
    public String modId() {
        return MOD_ID;
    }

    @Override
    public int modVerNum() {
        return 10;
    }

    @Override
    public String modVerStr() {
        return "v1.0";
    }

    @Override
    public void postInit() {
        MITEEvents.MITE_EVENT_BUS.register(new EventListener());
    }

    @Override
    public ConfigRegistry getConfigRegistry() {
        return CONFIG_REGISTRY;
    }
}
