package com.github.Debris.ommc.config;

import com.github.Debris.ommc.OhMyMiteClient;
import com.github.Debris.ommc.feat.EnumToolSwitchMode;
import com.github.Debris.ommc.inventory.sort.SortCategory;
import fi.dy.masa.malilib.config.SimpleConfigs;
import fi.dy.masa.malilib.config.options.ConfigBase;
import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigEnum;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.hotkeys.KeybindMulti;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;
import org.lwjgl.input.Keyboard;

import java.util.List;

import static com.github.Debris.ommc.config.ConfigFactory.*;

public class InventoryConfig extends SimpleConfigs {
    public static final List<ConfigBase<?>> ALL_VALUES;
    public static final List<ConfigHotkey> ALL_HOTKEYS;


    // inventory tweaks
    public static final ConfigBoolean ShouldTweakInventory = ofBoolean("物品栏功能", "本模组任何物品栏操作都需要这个开启");
    public static final ConfigBoolean ContinuousOperation = ofBoolean("连续操作", "长按Shift和左键时能连续移动物品\n长按Shift和丢弃时能连续丢弃物品");
    public static final ConfigBoolean WheelMoving = ofBoolean("滚轮移动");
    public static final ConfigBoolean WheelMovingInvert = ofBoolean("滚轮移动反转");
    public static final ConfigBoolean BetterQuickMoving = ofBoolean("更好的快速移动", "即Shift移动");
    public static final ConfigEnum<SortCategory> ItemSortingOrder = ofEnum("ommc.item_sorting_order", SortCategory.CREATIVE_INVENTORY);
    public static final ConfigBoolean AutoCrafting = ofBoolean("AutoCrafting");
    public static final ConfigBoolean ToolSwitch = ofBoolean("ToolSwitch");
    public static final ConfigEnum<EnumToolSwitchMode> ToolSwitchMode = ofEnum("ommc.tool_switch_mode", EnumToolSwitchMode.Order);
    public static final ConfigBoolean WeaponSwitch = ofBoolean("WeaponSwitch");


    //hotkeys
    public static final ConfigHotkey SortItem = ofHotkey("整理物品栏", KeybindMulti.fromStorageString("R", KeybindSettings.GUI), "按区域进行, 自动兼容几乎所有模组");
    public static final ConfigHotkey ModifierMoveSimilar = ofHotkey("移动类似物品(修饰键)", createKeyForGui(Keyboard.KEY_LCONTROL), "左键并按下可以移动同类物品");
    public static final ConfigHotkey ModifierMoveAll = ofHotkey("移动全部(修饰键)", createKeyForGui(Keyboard.KEY_SPACE), "左键并按下可以移动全部物品");
    public static final ConfigHotkey DropSimilar = ofHotkey("丢出类似物品", createKeyForGui(Keyboard.KEY_LSHIFT, Keyboard.KEY_Q), null);
    public static final ConfigHotkey ThrowSection = ofHotkey("清空区域", KeybindMulti.fromStorageString("", KeybindSettings.GUI), "全部丢出");
    public static final ConfigHotkey TradingRestock = ofHotkey("交易补货", createKeyForModifierGui(Keyboard.KEY_SPACE), "类似高版本空格补货");

    private static final InventoryConfig Instance;

    public InventoryConfig() {
        super(OhMyMiteClient.MOD_NAME_SIMPLE + "Inventory", ALL_HOTKEYS, ALL_VALUES, "物品栏功能");
    }

    public static SimpleConfigs getInstance() {
        return Instance;
    }

    static {
        ALL_VALUES = List.of(
                ShouldTweakInventory,
                ContinuousOperation,
                WheelMoving,
                WheelMovingInvert,
                BetterQuickMoving,
                ItemSortingOrder,
                AutoCrafting,
                ToolSwitch,
                ToolSwitchMode,
                WeaponSwitch
        );

        ALL_HOTKEYS = List.of(
                SortItem,
                ModifierMoveSimilar,
                ModifierMoveAll,
                DropSimilar,
                ThrowSection,
                TradingRestock
        );

        Instance = new InventoryConfig();
    }
}
