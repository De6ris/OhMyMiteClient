package com.github.debris.ommc;

import com.github.debris.ommc.config.MainConfig;
import com.github.debris.ommc.event.InitListener;
import fi.dy.masa.malilib.event.InitializationHandler;
import net.fabricmc.api.ClientModInitializer;
import net.xiaoyu233.fml.ModResourceManager;

public class OhMyMiteClient implements ClientModInitializer {
    public static final String MOD_ID = "ommc";
    public static final String MOD_NAME = "OhMyMiteClient";
    public static final String MOD_NAME_SIMPLE = "OMMC";

    @Override
    public void onInitializeClient() {
        MainConfig.getInstance().load();
        InitializationHandler.getInstance().registerInitializationHandler(new InitListener());
        ModResourceManager.addResourcePackDomain(MOD_ID);
    }
}