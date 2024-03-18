package com.github.Debris.oh_my_mite_client.config;

import com.google.gson.JsonObject;

import java.util.List;

public class ConfigUtils {
    public static void readConfigBase(JsonObject root, String category, List<TweakToggle> options) {
        JsonObject obj = JsonUtils.getNestedObject(root, category, false);

        if (obj != null) {
            for (IConfigBase option : options) {
                String name = option.getName();

                if (obj.has(name)) {
                    option.setValueFromJsonElement(obj.get(name));
                }
            }
        }
    }

    public static void writeConfigBase(JsonObject root, String category, List<TweakToggle> options) {
        JsonObject obj = JsonUtils.getNestedObject(root, category, true);
        for (TweakToggle option : options) {
            obj.add(option.getName(), option.getAsJsonElement());
        }
    }
}
