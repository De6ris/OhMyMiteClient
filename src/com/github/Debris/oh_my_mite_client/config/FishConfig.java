package com.github.Debris.oh_my_mite_client.config;

import net.xiaoyu233.fml.config.ConfigCategory;
import net.xiaoyu233.fml.config.ConfigEntry;
import net.xiaoyu233.fml.util.FieldReference;

public class FishConfig {
    public static final FieldReference<Integer> ATTACK_INTERVAL = new FieldReference<>(500);
    public static final FieldReference<Float> FLYING_SPEED_LEVEL = new FieldReference<>(0.99F);
    public static final FieldReference<Float> FLYING_SPEED_VERTICAL = new FieldReference<>(0.99F);

    public static int gameMode = 0;

    public static final ConfigCategory ROOT = ConfigCategory.of("OMMC")
            .addEntry(ConfigEntry.of("auto_attack_interval", ATTACK_INTERVAL).withComment("自动攻击间隔, 单位毫秒"))
            .addEntry(ConfigEntry.of("flying_speed_level", FLYING_SPEED_LEVEL).withComment("水平飞行速度"))
            .addEntry(ConfigEntry.of("flying_speed_vertical", FLYING_SPEED_LEVEL).withComment("竖直飞行速度"));
}
