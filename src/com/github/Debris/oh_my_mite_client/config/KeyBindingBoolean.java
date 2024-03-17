package com.github.Debris.oh_my_mite_client.config;

import net.minecraft.KeyBinding;

public class KeyBindingBoolean extends KeyBinding {

    private boolean value;

    public KeyBindingBoolean(String s, int i, boolean defaultValue) {
        super(s, i);
        this.value = defaultValue;
    }

    public boolean getBoolean() {
        return this.value;
    }

    public void invert() {
        this.value = !this.value;
    }
}
