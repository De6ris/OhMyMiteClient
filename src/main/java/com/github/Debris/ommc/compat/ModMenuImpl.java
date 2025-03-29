package com.github.Debris.ommc.compat;

import com.github.Debris.ommc.config.OMMCConfig;
import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;

public class ModMenuImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return screen -> OMMCConfig.getInstance().getConfigScreen(screen);
    }
}
