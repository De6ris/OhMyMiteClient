package com.github.Debris.oh_my_mite_client.config;

import com.google.common.collect.ImmutableList;
import net.minecraft.KeyBinding;

import java.io.*;

public enum FeatureToggle {
    Tweak_AutoForward("自动前进", 200, false),
    Tweak_AutoLeft("自动向左", 203, false),
    Tweak_AutoBack("自动后退", 208, false),
    Tweak_AutoRight("自动向右", 205, false),
    Tweak_Autojump("自动跳跃", 0, false),
    Tweak_AutoSneak("自动潜行", 54, false),//RShift
    Tweak_AutoAttack("自动攻击", 36, false),//J
    Tweak_HoldUse("长按右键", 22, false),//U
    Tweak_AutoCraft("自动合成(开发中)", 13, false),//= TODO
    Tweak_RenderEntities("渲染实体(开发中)", 64, false),//F6//TODO
    Tweak_FastFlying("快速飞行", 48, false),//B


    Tweak_CopyTP("复制tp坐标", 47),//V
    Tweak_ToggleMode("切换游戏模式", 62),//F4
    ;
    public static File optionsFile;
    public static final ImmutableList<FeatureToggle> VALUES = ImmutableList.copyOf(values());
    private final KeyBinding keybind;
    private boolean valueBoolean;
    private final boolean isTrigger;

    FeatureToggle(String description, int defaultKey) {
        this.keybind = new KeyBinding(description, defaultKey);
        this.isTrigger = true;
        this.valueBoolean = false;
    }
    FeatureToggle(String description, int defaultKey, boolean defaultValueBoolean) {
        this.keybind = new KeyBinding(description, defaultKey);
        this.isTrigger = false;
        this.valueBoolean = defaultValueBoolean;
    }

    public KeyBinding getKeybind() {
        return this.keybind;
    }
    public boolean getBooleanValue() {
        return this.valueBoolean;
    }
    public boolean isTrigger() {
        return isTrigger;
    }
    public void invert() {
        this.valueBoolean = !this.valueBoolean;
    }

    public static void saveOptions() {
        PrintWriter var1;
        try {
            var1 = new PrintWriter(new FileWriter(optionsFile));
            for (int var2 = 0; var2 < values().length; ++var2) {
                var1.println("key_" + values()[var2].getKeybind().keyDescription + ":" + values()[var2].getKeybind().keyCode);
            }
            var1.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadOptions() {
        BufferedReader var1;
        try {
            if (!optionsFile.exists()) {
                return;
            }
            var1 = new BufferedReader(new FileReader(optionsFile));
            String var2;
            while ((var2 = var1.readLine()) != null) {
                String[] var3 = var2.split(":");
                for (int var4 = 0; var4 < values().length; ++var4) {
                    if (var3[0].equals("key_" + values()[var4].getKeybind().keyDescription)) {
                        values()[var4].getKeybind().keyCode = Integer.parseInt(var3[1]);
                    }
                }
            }
            KeyBinding.resetKeyBindingArrayAndHash();
            var1.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setPath(File parent) {
        optionsFile = new File(parent, "OMMCoptions.txt");
    }

}
