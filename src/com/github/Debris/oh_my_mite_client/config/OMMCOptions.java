package com.github.Debris.oh_my_mite_client.config;

import net.minecraft.I18n;
import net.minecraft.KeyBinding;
import org.lwjgl.input.Keyboard;

import java.io.*;
import java.util.Arrays;
import java.util.stream.Stream;

public class OMMCOptions {
    private final File optionsFile;
    public int gameMode = 0;
    public KeyBindingBoolean keyBindAutoForward = new KeyBindingBoolean("自动前进", 200, false);
    public KeyBindingBoolean keyBindAutoLeft = new KeyBindingBoolean("自动向左", 203, false);
    public KeyBindingBoolean keyBindAutoBack = new KeyBindingBoolean("自动后退", 208, false);
    public KeyBindingBoolean keyBindAutoRight = new KeyBindingBoolean("自动向右", 205, false);
    public KeyBindingBoolean keyBindAutojump = new KeyBindingBoolean("自动跳跃", 0, false);
    public KeyBindingBoolean keyBindAutoSneak = new KeyBindingBoolean("自动潜行", 0, false);
    public KeyBindingBoolean keyBindAutoAttack = new KeyBindingBoolean("自动攻击", 36, false);//J
    public KeyBindingBoolean keyBindHoldUse = new KeyBindingBoolean("长按右键", 22, false);//U
    public KeyBindingBoolean keyBindAutoCraft = new KeyBindingBoolean("自动合成", 0, false);
    public KeyBinding keyBindCopyTP = new KeyBinding("复制tp坐标", 46);//C
    public KeyBinding keyBindToggleMode = new KeyBinding("切换游戏模式", 62);//F4

    public KeyBindingBoolean[] keyBindingBooleans;
    public KeyBinding[] keyBindingTriggers;
    public KeyBinding[] allKeyBindings;

    public OMMCOptions(File file) {
        this.initKeybindings();
        this.optionsFile = new File(file, "OMMCoptions.txt");
        this.loadOptions();
    }

    public void initKeybindings() {
        this.keyBindingBooleans = new KeyBindingBoolean[]{
                keyBindAutoForward,
                keyBindAutoLeft,
                keyBindAutoBack,
                keyBindAutoRight,
                keyBindAutojump,
                keyBindAutoSneak,
                keyBindAutoAttack,
                keyBindHoldUse,
                keyBindAutoCraft
        };

        this.keyBindingTriggers = new KeyBinding[]{
                keyBindCopyTP,
                keyBindToggleMode
        };

        Stream<KeyBinding> stream1 = Arrays.stream(this.keyBindingBooleans);
        Stream<KeyBinding> stream2 = Arrays.stream(this.keyBindingTriggers);

        this.allKeyBindings = Stream.concat(stream1, stream2).toArray(KeyBinding[]::new);
    }

    public void setKeyBinding(int par1, int par2) {
        this.allKeyBindings[par1].keyCode = par2;
        this.saveOptions();
    }

    public void loadOptions() {
        BufferedReader var1;
        try {
            if (!this.optionsFile.exists()) {
                return;
            }
            var1 = new BufferedReader(new FileReader(this.optionsFile));
            String var2;
            while ((var2 = var1.readLine()) != null) {
                String[] var3 = var2.split(":");
                for (int var4 = 0; var4 < this.allKeyBindings.length; ++var4) {
                    if (var3[0].equals("key_" + this.allKeyBindings[var4].keyDescription)) {
                        this.allKeyBindings[var4].keyCode = Integer.parseInt(var3[1]);
                    }
                }
            }
            KeyBinding.resetKeyBindingArrayAndHash();
            var1.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveOptions() {
        PrintWriter var1;
        try {
            var1 = new PrintWriter(new FileWriter(this.optionsFile));
            for (int var2 = 0; var2 < this.allKeyBindings.length; ++var2) {
                var1.println("key_" + this.allKeyBindings[var2].keyDescription + ":" + this.allKeyBindings[var2].keyCode);
            }
            var1.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public String getOptionDisplayString(int par1) {
        int var2 = this.allKeyBindings[par1].keyCode;
        return getKeyDisplayString(var2);
    }

    public static String getKeyDisplayString(int par0) {
        return Keyboard.getKeyName(par0);
    }

    public String getKeyBindingDescription(int par1) {
        return I18n.getString(this.allKeyBindings[par1].keyDescription);
    }
}
