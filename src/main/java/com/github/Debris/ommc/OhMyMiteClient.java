package com.github.Debris.ommc;

import com.github.Debris.ommc.config.OMMCConfig;
import com.github.Debris.ommc.event.OMMCInitHandler;
import fi.dy.masa.malilib.event.InitializationHandler;
import net.fabricmc.api.ModInitializer;
import net.xiaoyu233.fml.ModResourceManager;

public class OhMyMiteClient implements ModInitializer {
    public static final String MOD_ID = "OhMyMiteClient";

    @Override
    public void onInitialize() {
        OMMCConfig.getInstance().load();
        InitializationHandler.getInstance().registerInitializationHandler(new OMMCInitHandler());
        ModResourceManager.addResourcePackDomain("ommc");
    }
}