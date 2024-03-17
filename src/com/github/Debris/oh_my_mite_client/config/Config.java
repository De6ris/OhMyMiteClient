package com.github.Debris.oh_my_mite_client.config;

import net.xiaoyu233.fml.config.ConfigCategory;
import net.xiaoyu233.fml.config.ConfigEntry;
import net.xiaoyu233.fml.util.FieldReference;

public class Config {
    public static final FieldReference<Integer> ATTACK_INTERVAL = new FieldReference<>(500);

    public static final ConfigCategory ROOT = ConfigCategory.of("NoClickCD")
            .addEntry(ConfigEntry.of("auto_attack_interval", ATTACK_INTERVAL).withComment("自动攻击间隔, 单位毫秒"));
}
