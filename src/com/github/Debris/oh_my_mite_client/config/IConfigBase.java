package com.github.Debris.oh_my_mite_client.config;

import com.google.gson.JsonElement;

public interface IConfigBase {
    String getName();
    void setValueFromJsonElement(JsonElement element);
    JsonElement getAsJsonElement();
}
