package com.github.Debris.ommc.config;

import com.github.Debris.ommc.inventory.sort.SortCategory;
import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.ConfigTab;
import fi.dy.masa.malilib.config.SimpleConfigs;
import fi.dy.masa.malilib.config.options.*;
import fi.dy.masa.malilib.hotkeys.KeybindMulti;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;
import fi.dy.masa.malilib.util.KeyCodes;
import org.lwjgl.input.Keyboard;

import java.util.List;

import static com.github.Debris.ommc.OhMyMiteClient.MOD_ID;

public class OMMCConfig extends SimpleConfigs {

    // common
    public static final ConfigHotkey OpenWindow = new ConfigHotkey("OpenWindow", KeyCodes.getStorageString(Keyboard.KEY_X, Keyboard.KEY_C), "直接打开本模组的配置");
    public static final ConfigToggle AutoForward = new ConfigToggle("AutoForward");
    public static final ConfigToggle AutoLeft = new ConfigToggle("AutoLeft");
    public static final ConfigToggle AutoBack = new ConfigToggle("AutoBack");
    public static final ConfigToggle AutoRight = new ConfigToggle("AutoRight");
    public static final ConfigToggle AutoJump = new ConfigToggle("AutoJump");
    public static final ConfigToggle AutoSneak = new ConfigToggle("AutoSneak");
    public static final ConfigToggle HoldAttack = new ConfigToggle("ommc.holdAttack");
    public static final ConfigToggle PeriodicAttack = new ConfigToggle("ommc.periodicAttack");
    public static final ConfigToggle HoldUse = new ConfigToggle("ommc.holdUse");
    public static final ConfigToggle PeriodicUse = new ConfigToggle("ommc.periodicUse");
    public static final ConfigToggle CancelEntityRender = new ConfigToggle("CancelEntityRender");
    public static final ConfigToggle FastFlying = new ConfigToggle("FastFlying");
    public static final ConfigHotkey CopyTP = new ConfigHotkey("CopyTP", KeyCodes.getStorageString(Keyboard.KEY_F3, Keyboard.KEY_C), "按下后复制到剪贴板");
    public static final ConfigHotkey ToggleGameMode = new ConfigHotkey("ToggleGameMode", KeyCodes.getStorageString(Keyboard.KEY_F3, Keyboard.KEY_F4), "在生存和创造之间切换");

    public static final ConfigHotkey SortItem = new ConfigHotkey("整理物品", KeybindMulti.fromStorageString("R", KeybindSettings.GUI), null);
    public static final ConfigHotkey ModifierMoveSimilar = new ConfigHotkey("移动类似物品(修饰键)", createKeyForGui(Keyboard.KEY_LCONTROL), "左键并按下可以移动同类物品");
    public static final ConfigHotkey ModifierMoveAll = new ConfigHotkey("移动全部(修饰键)", createKeyForGui(Keyboard.KEY_SPACE), "左键并按下可以移动全部物品");
    public static final ConfigHotkey DropSimilar = new ConfigHotkey("丢出类似物品", createKeyForGui(Keyboard.KEY_LSHIFT, Keyboard.KEY_Q), null);
    public static final ConfigHotkey TradingRestock = new ConfigHotkey("交易补货", createKeyForModifierGui(Keyboard.KEY_SPACE), null);

    // boolean tweaks
    public static final ConfigBoolean AutoCrafting = new ConfigBoolean("AutoCrafting");
    public static final ConfigBoolean ToolSwitch = new ConfigBoolean("ToolSwitch");
    public static final ConfigBoolean WeaponSwitch = new ConfigBoolean("WeaponSwitch");
    public static final ConfigBoolean DisableScoreBoard = new ConfigBoolean("禁止渲染计分板");
    public static final ConfigBoolean AutoQuitGame = new ConfigBoolean("低血量退出游戏");
    public static final ConfigBoolean GammaOverride = new ConfigBooleanWithPre("强制夜视", "gamma_free", "这需要gamma_free模组");
    public static final ConfigBoolean DevClient = new ConfigBoolean("Dev客户端");
    public static final ConfigBoolean FPSUnlocked = new ConfigBoolean("帧数解禁");
    public static final ConfigBoolean RotateFree = new ConfigBoolean("转动自由", "当你有缓慢效果时仍然能自由转动视角");
    public static final ConfigBoolean RemoveGuiContainerCD = new ConfigBoolean("取消容器Gui冷却", "在离开村民交易和马的物品栏时不再有冷却");
    public static final ConfigBoolean CreativeOneHit = new ConfigBoolean("创造模式一击必杀");
    public static final ConfigBoolean ContinuousClick = new ConfigBoolean("连续点击", "在长按Shift和左键时能连续移动物品");
    public static final ConfigBoolean WheelMoving = new ConfigBoolean("滚轮移动");
    public static final ConfigBoolean WheelMovingInvert = new ConfigBoolean("滚轮移动反转");
    public static final ConfigBoolean BetterInventoryMoving = new ConfigBoolean("更好的Shift点击");
    public static final ConfigBoolean InventoryTweaks = new ConfigBoolean("物品栏功能", "本模组任何物品栏操作都需要这个开启");

    // config numerical

    public static final ConfigInteger PeriodicAttackInterval = new ConfigInteger("ommc.periodicAttackInterval", 10, 1, 200, "单位为刻");
    public static final ConfigInteger PeriodicUseInterval = new ConfigInteger("ommc.periodicUseInterval", 10, 1, 200, "单位为刻");
    public static final ConfigDouble FlySpeedLevel = new ConfigDouble("FlySpeedLevel", 0.99d, 0.0d, 1.0d);
    public static final ConfigDouble FlySpeedVertical = new ConfigDouble("FlySpeedVertical", 0.99d, 0.0d, 1.0d);
    public static final ConfigInteger OverrideUseInterval = new ConfigInteger("OverrideUseInterval", 250, 1, 250, "单位毫秒");
    public static final ConfigEnum<EnumToolSwitchMode> ToolSwitchMode = new ConfigEnum<>("ommc.toolSwitchMode", EnumToolSwitchMode.Order);
    public static final ConfigInteger TasksPerTick = new ConfigInteger("每刻执行任务数", 5, 0, 20, "关于本模组的任务队列");
    public static final ConfigDouble QuitGameThreshold = new ConfigDouble("低血量退出游戏阈值", 2.0D, 0.0D, Double.MAX_VALUE, false, "受到伤害后低于此血量时, 触发退出游戏");
    public static final ConfigInteger QuitGameSpeed = new ConfigInteger("低血量退出速度", 4, 2, 100, "模拟按下Esc之后, 点击退出按钮之前的时间(单位刻), 单机游戏不能调太低, 因为可能导致存档损坏");
    public static final ConfigInteger QuitGameCounter = new ConfigInteger("低血量免退出倒计时", 60, 1, 100, "在这段时间(单位秒)过去之前, 你进入游戏时不会自动退出");
    public static final ConfigEnum<SortCategory> ItemSortingOrder = new ConfigEnum<>("物品整理顺序", SortCategory.ITEM_ID, "1.翻译键顺序\n2.按创造模式物品栏顺序\n3.按翻译后名称顺序\n4.按拼音顺序(需要前置)");


    // debug

    public static final ConfigHotkey Test = new ConfigHotkey("测试", Keyboard.KEY_Y, "仅供测试");

    public static final ConfigBoolean InstantCrafting = new ConfigBoolean("InstantCrafting");

    private static final OMMCConfig Instance;


    //    public static List<ConfigHotkey> debugHotkeys = new ArrayList<>();
    public static final List<ConfigBase<?>> booleanTweaks;
    public static final List<ConfigBase<?>> numerical;
//    public static List<ConfigBase<?>> testTweaks = new ArrayList<>();


    public static final List<ConfigHotkey> hotkeys;
    public static final List<ConfigBase<?>> values;
    public static final List<ConfigBase<?>> generic;
    public static final List<ConfigTab> configTabs;

    public OMMCConfig(String name, List<ConfigHotkey> hotkeys, List<?> values, String comment) {
        super(name, hotkeys, values, comment);
    }

    public static OMMCConfig getInstance() {
        return Instance;
    }

    @Override
    public List<ConfigTab> getConfigTabs() {
        return configTabs;
    }

    private static KeybindMulti createKeyForGui(int... keyCodes) {
        return KeybindMulti.fromStorageString(KeyCodes.getStorageString(keyCodes), KeybindSettings.GUI);
    }

    private static KeybindMulti createKeyForModifierGui(int... keyCodes) {
        return KeybindMulti.fromStorageString(KeyCodes.getStorageString(keyCodes), KeybindSettings.MODIFIER_GUI);
    }

    static {
        hotkeys = List.of(OpenWindow, AutoForward, AutoBack, AutoJump, AutoSneak, PeriodicAttack, PeriodicUse, CancelEntityRender, FastFlying, CopyTP, ToggleGameMode, SortItem, ModifierMoveSimilar, ModifierMoveAll, DropSimilar, TradingRestock);
//        hotkeys.addAll(commonHotkeys);

        booleanTweaks = List.of(AutoCrafting, ToolSwitch, WeaponSwitch, DisableScoreBoard, AutoQuitGame, GammaOverride, DevClient, FPSUnlocked, RotateFree, ContinuousClick, WheelMoving, WheelMovingInvert, BetterInventoryMoving, RemoveGuiContainerCD, CreativeOneHit, InventoryTweaks);
        numerical = List.of(PeriodicAttackInterval, PeriodicUseInterval, FlySpeedLevel, FlySpeedVertical, OverrideUseInterval, ToolSwitchMode, TasksPerTick, QuitGameThreshold, QuitGameSpeed, QuitGameCounter, ItemSortingOrder);

//        debugHotkeys = List.of(Test);
//        hotkeys.addAll(debugHotkeys);
//        testTweaks = List.of(InstantCrafting);
//        values.addAll(testTweaks);
//        configTabs.add(new ConfigTab("ommc.debug", testTweaks));


        ImmutableList.Builder<ConfigBase<?>> valuesBuilder = ImmutableList.builder();
        valuesBuilder.addAll(booleanTweaks);
        valuesBuilder.addAll(numerical);
        values = valuesBuilder.build();

        ImmutableList.Builder<ConfigBase<?>> genericBuilder = ImmutableList.builder();
        genericBuilder.addAll(values);
        genericBuilder.addAll(hotkeys);
        generic = genericBuilder.build();

        ImmutableList.Builder<ConfigTab> configTabBuilder = ImmutableList.builder();
        configTabBuilder.add(new ConfigTab("generic", generic));
        configTabBuilder.add(new ConfigTab("ommc.toggles", booleanTweaks));
        configTabBuilder.add(new ConfigTab("ommc.numerical", numerical));
        configTabBuilder.add(new ConfigTab("hotkey", hotkeys));
        configTabs = configTabBuilder.build();

        Instance = new OMMCConfig(MOD_ID, hotkeys, values, "一些生电功能");
    }
}
