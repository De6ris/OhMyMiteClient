package com.github.debris.ommc.config;

import com.github.debris.ommc.OhMyMiteClient;
import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.ConfigTab;
import fi.dy.masa.malilib.config.SimpleConfigs;
import fi.dy.masa.malilib.config.options.*;
import fi.dy.masa.malilib.util.KeyCodes;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

import static com.github.debris.ommc.config.ConfigFactory.*;

public class MainConfig extends SimpleConfigs {
    // boolean tweaks
    public static final ConfigBoolean DisableScoreBoard = ofBoolean("禁止渲染计分板");
    public static final ConfigBoolean AutoQuitGame = ofBoolean("低血量退出游戏");
    public static final ConfigBoolean GammaOverride = ofBooleanWithDependency("强制夜视", "gamma_free", "这需要gamma_free模组");
    public static final ConfigBoolean DevClient = ofBoolean("Dev客户端");
    public static final ConfigBoolean FPSUnlocked = ofBoolean("帧数解禁");
    public static final ConfigBoolean RotateFree = ofBoolean("转动自由", "当你有缓慢效果时仍然能自由转动视角");
    public static final ConfigBoolean RemoveGuiContainerCD = ofBoolean("取消容器Gui冷却", "在离开村民交易和马的物品栏时不再有冷却");
    public static final ConfigBoolean CreativeOneHit = ofBoolean("创造模式一击必杀");


    // numerical
    public static final ConfigInteger PeriodicAttackInterval = ofInteger("ommc.periodicAttackInterval", 10, 1, 200, "单位为刻");
    public static final ConfigInteger PeriodicUseInterval = ofInteger("ommc.periodicUseInterval", 10, 1, 200, "单位为刻");
    public static final ConfigDouble FlySpeedLevel = ofDouble("FlySpeedLevel", 0.99d, 0.0d, 1.0d);
    public static final ConfigDouble FlySpeedVertical = ofDouble("FlySpeedVertical", 0.99d, 0.0d, 1.0d);
    public static final ConfigInteger OverrideUseInterval = ofInteger("OverrideUseInterval", 250, 1, 250, "单位毫秒");
    public static final ConfigInteger TasksPerTick = ofInteger("每刻执行任务数", 5, 0, 20, "关于本模组的任务队列");
    public static final ConfigDouble QuitGameThreshold = ofDouble("低血量退出游戏阈值", 2.0D, 0.0D, Double.MAX_VALUE, false, "受到伤害后低于此血量时, 触发退出游戏");
    public static final ConfigInteger QuitGameSpeed = ofInteger("低血量退出速度", 4, 2, 100, "模拟按下Esc之后, 点击退出按钮之前的时间(单位刻), 单机游戏不能调太低, 因为可能导致存档损坏");
    public static final ConfigInteger QuitGameCounter = ofInteger("低血量免退出倒计时", 60, 1, 100, "在这段时间(单位秒)过去之前, 你进入游戏时不会自动退出");


    // press
    public static final ConfigHotkey OpenWindow = ofHotkey("OpenWindow", KeyCodes.getStorageString(Keyboard.KEY_X, Keyboard.KEY_C), "打开本模组的配置");
    public static final ConfigHotkey OpenModule_Inventory = ofHotkey("配置模块:物品栏", KeyCodes.getStorageString(Keyboard.KEY_I, Keyboard.KEY_C));
    public static final ConfigHotkey CopyTP = ofHotkey("CopyTP", KeyCodes.getStorageString(Keyboard.KEY_F3, Keyboard.KEY_C), "按下后复制到剪贴板");
    public static final ConfigHotkey ToggleGameMode = ofHotkey("ToggleGameMode", KeyCodes.getStorageString(Keyboard.KEY_F3, Keyboard.KEY_F4), "在生存和创造之间切换");


    // toggle
    public static final ConfigToggle AutoForward = ofToggle("AutoForward");
    public static final ConfigToggle AutoLeft = ofToggle("AutoLeft");
    public static final ConfigToggle AutoBack = ofToggle("AutoBack");
    public static final ConfigToggle AutoRight = ofToggle("AutoRight");
    public static final ConfigToggle AutoJump = ofToggle("AutoJump");
    public static final ConfigToggle AutoSneak = ofToggle("AutoSneak");
    public static final ConfigToggle HoldAttack = ofToggle("ommc.holdAttack");
    public static final ConfigToggle PeriodicAttack = ofToggle("ommc.periodicAttack");
    public static final ConfigToggle HoldUse = ofToggle("ommc.holdUse");
    public static final ConfigToggle PeriodicUse = ofToggle("ommc.periodicUse");
    public static final ConfigToggle CullEntityRender = ofToggle("CullEntityRender");
    public static final ConfigToggle FastFlying = ofToggle("FastFlying");


    // debug
    public static final ConfigHotkey Test = ofHotkey("测试", Keyboard.KEY_Y, "仅供测试");
    public static final ConfigBoolean InstantCrafting = ofBoolean("InstantCrafting");


    private static final MainConfig Instance;


    public static final List<ConfigBase<?>> booleanTweaks;
    public static final List<ConfigBase<?>> numerical;
    public static final List<ConfigHotkey> press;
    public static final List<ConfigToggle> toggle;


    public static final List<ConfigBase<?>> ALL_VALUES;
    public static final List<ConfigHotkey> ALL_HOTKEYS;
    public static final List<ConfigBase<?>> ALL_CONFIGS;
    public static final List<ConfigTab> configTabs;

    public MainConfig(String name, List<ConfigHotkey> hotkeys, List<?> values, String comment) {
        super(name, hotkeys, values, comment);
    }

    public static MainConfig getInstance() {
        return Instance;
    }

    @Override
    public List<ConfigTab> getConfigTabs() {
        return configTabs;
    }

    static {
        booleanTweaks = List.of(
                DisableScoreBoard,
                AutoQuitGame,
                GammaOverride,
                DevClient,
                FPSUnlocked,
                RotateFree,
                RemoveGuiContainerCD,
                CreativeOneHit
        );

        numerical = List.of(
                PeriodicAttackInterval,
                PeriodicUseInterval,
                FlySpeedLevel,
                FlySpeedVertical,
                OverrideUseInterval,
                TasksPerTick,
                QuitGameThreshold,
                QuitGameSpeed,
                QuitGameCounter
        );

        press = List.of(
                OpenWindow,
                OpenModule_Inventory,
                CopyTP,
                ToggleGameMode
        );

        toggle = List.of(
                AutoForward,
                AutoLeft,
                AutoBack,
                AutoRight,
                AutoJump,
                AutoSneak,
                HoldAttack,
                PeriodicAttack,
                HoldUse,
                PeriodicUse,
                CullEntityRender,
                FastFlying
        );

        ImmutableList.Builder<ConfigBase<?>> valuesBuilder = ImmutableList.builder();
        valuesBuilder.addAll(booleanTweaks);
        valuesBuilder.addAll(numerical);
        valuesBuilder.addAll(press);
        valuesBuilder.addAll(toggle);
        ALL_CONFIGS = valuesBuilder.build();


        ALL_HOTKEYS = new ArrayList<>();
        ALL_VALUES = new ArrayList<>();

        for (ConfigBase<?> config : ALL_CONFIGS) {
            if (config instanceof ConfigHotkey configHotkey) {
                ALL_HOTKEYS.add(configHotkey);
            } else {
                ALL_VALUES.add(config);
            }
        }


        ImmutableList.Builder<ConfigTab> configTabBuilder = ImmutableList.builder();
        configTabBuilder.add(new ConfigTab("generic", ALL_CONFIGS));
        configTabBuilder.add(new ConfigTab("ommc.toggles", booleanTweaks));
        configTabBuilder.add(new ConfigTab("ommc.numerical", numerical));
        configTabBuilder.add(new ConfigTab("ommc.press", press));
        configTabBuilder.add(new ConfigTab("ommc.toggle", toggle));
        configTabs = configTabBuilder.build();

        Instance = new MainConfig(OhMyMiteClient.MOD_NAME_SIMPLE + "Main", ALL_HOTKEYS, ALL_VALUES, "一些生电功能");
    }
}
