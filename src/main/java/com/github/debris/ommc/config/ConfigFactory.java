package com.github.debris.ommc.config;

import com.github.debris.ommc.config.options.ConfigBooleanWithDependency;
import fi.dy.masa.malilib.config.options.*;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeybindMulti;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;
import fi.dy.masa.malilib.util.KeyCodes;

public class ConfigFactory {
    public static ConfigBoolean ofBoolean(String name) {
        return new ConfigBoolean(name);
    }

    public static ConfigBoolean ofBoolean(String name, String comment) {
        return new ConfigBoolean(name, comment);
    }

    public static ConfigInteger ofInteger(String name, int defaultValue, int minValue, int maxValue) {
        return ofInteger(name, defaultValue, minValue, maxValue, null);
    }

    public static ConfigInteger ofInteger(String name, int defaultValue, int minValue, int maxValue, String comment) {
        return new ConfigInteger(name, defaultValue, minValue, maxValue, comment);
    }

    public static ConfigDouble ofDouble(String name, double defaultValue, double minValue, double maxValue) {
        return ofDouble(name, defaultValue, minValue, maxValue, null);
    }

    public static ConfigDouble ofDouble(String name, double defaultValue, double minValue, double maxValue, String comment) {
        return ofDouble(name, defaultValue, minValue, maxValue, true, comment);
    }

    public static ConfigDouble ofDouble(String name, double defaultValue, double minValue, double maxValue, boolean useSlider, String comment) {
        return new ConfigDouble(name, defaultValue, minValue, maxValue, useSlider, comment);
    }

    public static ConfigToggle ofToggle(String name) {
        return new ConfigToggle(name);
    }

    public static ConfigHotkey ofHotkey(String name) {
        return ofHotkey(name, KeybindSettings.DEFAULT, null);
    }

    public static ConfigHotkey ofHotkey(String name, int defaultKey, String comment) {
        return ofHotkey(name, KeyCodes.getNameForKey(defaultKey), comment);
    }

    public static ConfigHotkey ofHotkey(String name, String storageString) {
        return ofHotkey(name, storageString, null);
    }

    public static ConfigHotkey ofHotkey(String name, String storageString, String comment) {
        return new ConfigHotkey(name, storageString, comment);
    }

    public static ConfigHotkey ofHotkey(String name, KeybindSettings settings, String comment) {
        return new ConfigHotkey(name, KeybindMulti.fromStorageString("", settings), comment);
    }

    public static ConfigHotkey ofHotkey(String name, IKeybind keybind, String comment) {
        return new ConfigHotkey(name, keybind, comment);
    }

    public static <T extends Enum<T>> ConfigEnum<T> ofEnum(String name, T defaultValue) {
        return ofEnum(name, defaultValue, null);
    }

    public static <T extends Enum<T>> ConfigEnum<T> ofEnum(String name, T defaultValue, String comment) {
        return new ConfigEnum<>(name, defaultValue, comment);
    }

    public static KeybindMulti createKeyForGui(int... keyCodes) {
        return KeybindMulti.fromStorageString(KeyCodes.getStorageString(keyCodes), KeybindSettings.GUI);
    }

    public static KeybindMulti createKeyForModifierGui(int... keyCodes) {
        return KeybindMulti.fromStorageString(KeyCodes.getStorageString(keyCodes), KeybindSettings.MODIFIER_GUI);
    }

    public static ConfigBoolean ofBooleanWithDependency(String name, String modId, String comment) {
        return new ConfigBooleanWithDependency(name, modId, comment);
    }
}
