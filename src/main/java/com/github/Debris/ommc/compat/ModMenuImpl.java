package com.github.Debris.ommc.compat;

import com.github.Debris.ommc.config.MainConfig;
import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;

public class ModMenuImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return screen -> MainConfig.getInstance().getConfigScreen(screen);
    }
}
