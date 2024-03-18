package com.github.Debris.oh_my_mite_client.config;

import com.google.common.collect.ImmutableList;
import com.google.gson.*;
import net.minecraft.KeyBinding;
import org.lwjgl.input.Keyboard;

import java.io.*;

public enum TweakToggle implements IConfigBase {
    Tweak_AutoForward("自动前进", 200, false),
    Tweak_AutoLeft("自动向左", 203, false),
    Tweak_AutoBack("自动后退", 208, false),
    Tweak_AutoRight("自动向右", 205, false),
    Tweak_AutoJump("自动跳跃", 35, false),//H
    Tweak_AutoSneak("自动潜行", 54, false),//RShift
    Tweak_AutoAttack("自动攻击", 36, false),//J
    Tweak_HoldUse("长按右键", 22, false),//U
    Tweak_AutoCraft("自动合成(开发中)", 13, false),//= TODO
    Tweak_RenderLivings("禁止渲染生物", 64, false),//F6
    Tweak_FastFlying("快速飞行", 48, false),//B


    Tweak_CopyTP("复制tp坐标", 47),//V
    Tweak_ToggleMode("切换游戏模式", 62),//F4
    ;
    public static File optionsFile;
    public static final ImmutableList<TweakToggle> VALUES = ImmutableList.copyOf(values());
    private final String name;
    private final KeyBinding keybind;
    private boolean valueBoolean;
    private final boolean isTrigger;

    TweakToggle(String name, int defaultKey) {
        this.name = name;
        this.keybind = new KeyBinding(name, defaultKey);
        this.isTrigger = true;
        this.valueBoolean = false;
    }

    TweakToggle(String name, int defaultKey, boolean defaultValueBoolean) {
        this.name = name;
        this.keybind = new KeyBinding(name, defaultKey);
        this.isTrigger = false;
        this.valueBoolean = defaultValueBoolean;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public KeyBinding getKeybind() {
        return this.keybind;
    }

    public boolean getBooleanValue() {
        return this.valueBoolean;
    }

    public JsonElement getAsJsonElement() {
        JsonObject obj = new JsonObject();
        obj.add("enabled", new JsonPrimitive(this.valueBoolean));
        if (this.keybind.keyCode < 0) {
            this.keybind.keyCode = 0;
        }
        obj.add("hotkey", new JsonPrimitive(Keyboard.getKeyName(this.keybind.keyCode)));
        return obj;
    }

    @Override
    public void setValueFromJsonElement(JsonElement element) {
        JsonObject obj = element.getAsJsonObject();
        if (JsonUtils.hasBoolean(obj, "enabled")) {
            this.valueBoolean = obj.get("enabled").getAsBoolean();
        }
        if (JsonUtils.hasString(obj, "hotkey")) {
            this.keybind.keyCode = Keyboard.getKeyIndex(obj.get("hotkey").getAsString());
        }
    }

    public boolean isTrigger() {
        return this.isTrigger;
    }

    public void invert() {
        this.valueBoolean = !this.valueBoolean;
    }

    public static void save() {
        JsonObject configRoot = new JsonObject();
        ConfigUtils.writeConfigBase(configRoot, "FeatureToggles", VALUES);
        JsonUtils.writeJsonToFile(configRoot, optionsFile);
    }

    public static void load() {
        if (!optionsFile.exists()) {
            return;
        }
        JsonElement jsonElement = JsonUtils.parseJsonFile(optionsFile);
        if (jsonElement != null && jsonElement.isJsonObject()) {
            JsonObject obj = jsonElement.getAsJsonObject();
            ConfigUtils.readConfigBase(obj, "FeatureToggles", VALUES);
        }
        KeyBinding.resetKeyBindingArrayAndHash();
    }


    public static void setPath(File parent) {
        optionsFile = new File(parent, "configs/OMMCTweaks.json");
    }

}
