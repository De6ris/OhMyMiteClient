package com.github.Debris.oh_my_mite_client.config;

import com.google.gson.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;

public class JsonUtils {
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Nullable
    public static JsonObject getNestedObject(JsonObject parent, String key, boolean create) {
        if (!parent.has(key) || !parent.get(key).isJsonObject()) {
            if (!create) {
                return null;
            }
            JsonObject obj = new JsonObject();
            parent.add(key, obj);
            return obj;
        } else {
            return parent.get(key).getAsJsonObject();
        }
    }

    public static boolean hasBoolean(JsonObject obj, String name) {
        JsonElement el = obj.get(name);

        if (el != null && el.isJsonPrimitive()) {
            try {
                el.getAsBoolean();
                return true;
            } catch (Exception ignore) {
            }
        }

        return false;
    }

    public static boolean hasInteger(JsonObject obj, String name) {
        JsonElement el = obj.get(name);

        if (el != null && el.isJsonPrimitive()) {
            try {
                el.getAsInt();
                return true;
            } catch (Exception ignore) {
            }
        }

        return false;
    }

    public static boolean hasLong(JsonObject obj, String name) {
        JsonElement el = obj.get(name);

        if (el != null && el.isJsonPrimitive()) {
            try {
                el.getAsLong();
                return true;
            } catch (Exception ignore) {
            }
        }

        return false;
    }

    public static boolean hasFloat(JsonObject obj, String name) {
        JsonElement el = obj.get(name);

        if (el != null && el.isJsonPrimitive()) {
            try {
                el.getAsFloat();
                return true;
            } catch (Exception ignore) {
            }
        }

        return false;
    }

    public static boolean hasDouble(JsonObject obj, String name) {
        JsonElement el = obj.get(name);

        if (el != null && el.isJsonPrimitive()) {
            try {
                el.getAsDouble();
                return true;
            } catch (Exception ignore) {
            }
        }

        return false;
    }

    public static boolean hasString(JsonObject obj, String name) {
        JsonElement el = obj.get(name);

        if (el != null && el.isJsonPrimitive()) {
            try {
                el.getAsString();
                return true;
            } catch (Exception ignore) {
            }
        }

        return false;
    }

    public static boolean hasObject(JsonObject obj, String name) {
        JsonElement el = obj.get(name);
        return el != null && el.isJsonObject();
    }

    public static boolean hasArray(JsonObject obj, String name) {
        JsonElement el = obj.get(name);
        return el != null && el.isJsonArray();
    }

    public static boolean getBooleanOrDefault(JsonObject obj, String name, boolean defaultValue) {
        if (obj.has(name) && obj.get(name).isJsonPrimitive()) {
            try {
                return obj.get(name).getAsBoolean();
            } catch (Exception ignore) {
            }
        }

        return defaultValue;
    }

    public static int getIntegerOrDefault(JsonObject obj, String name, int defaultValue) {
        if (obj.has(name) && obj.get(name).isJsonPrimitive()) {
            try {
                return obj.get(name).getAsInt();
            } catch (Exception ignore) {
            }
        }

        return defaultValue;
    }

    public static long getLongOrDefault(JsonObject obj, String name, long defaultValue) {
        if (obj.has(name) && obj.get(name).isJsonPrimitive()) {
            try {
                return obj.get(name).getAsLong();
            } catch (Exception ignore) {
            }
        }

        return defaultValue;
    }

    public static float getFloatOrDefault(JsonObject obj, String name, float defaultValue) {
        if (obj.has(name) && obj.get(name).isJsonPrimitive()) {
            try {
                return obj.get(name).getAsFloat();
            } catch (Exception ignore) {
            }
        }

        return defaultValue;
    }

    public static double getDoubleOrDefault(JsonObject obj, String name, double defaultValue) {
        if (obj.has(name) && obj.get(name).isJsonPrimitive()) {
            try {
                return obj.get(name).getAsDouble();
            } catch (Exception ignore) {
            }
        }

        return defaultValue;
    }

    public static String getStringOrDefault(JsonObject obj, String name, String defaultValue) {
        if (obj.has(name) && obj.get(name).isJsonPrimitive()) {
            try {
                return obj.get(name).getAsString();
            } catch (Exception ignore) {
            }
        }

        return defaultValue;
    }

    public static boolean getBoolean(JsonObject obj, String name) {
        return getBooleanOrDefault(obj, name, false);
    }

    public static int getInteger(JsonObject obj, String name) {
        return getIntegerOrDefault(obj, name, 0);
    }

    public static long getLong(JsonObject obj, String name) {
        return getLongOrDefault(obj, name, 0);
    }

    public static float getFloat(JsonObject obj, String name) {
        return getFloatOrDefault(obj, name, 0);
    }

    public static double getDouble(JsonObject obj, String name) {
        return getDoubleOrDefault(obj, name, 0);
    }

    @Nullable
    public static String getString(JsonObject obj, String name) {
        return getStringOrDefault(obj, name, null);
    }

    @Nonnull
    public static JsonObject deepCopy(@Nonnull JsonObject jsonObject) {
        JsonObject result = new JsonObject();

        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            result.add(entry.getKey(), deepCopy(entry.getValue()));
        }

        return result;
    }

    @Nonnull
    public static JsonArray deepCopy(@Nonnull JsonArray jsonArray) {
        JsonArray result = new JsonArray();

        for (JsonElement e : jsonArray) {
            result.add(deepCopy(e));
        }

        return result;
    }

    @Nonnull
    public static JsonElement deepCopy(@Nonnull JsonElement jsonElement) {
        if (jsonElement.isJsonPrimitive() || jsonElement.isJsonNull()) {
            return jsonElement; // these are immutable anyway
        } else if (jsonElement.isJsonObject()) {
            return deepCopy(jsonElement.getAsJsonObject());
        } else if (jsonElement.isJsonArray()) {
            return deepCopy(jsonElement.getAsJsonArray());
        } else {
            throw new UnsupportedOperationException("Unsupported element: " + jsonElement);
        }
    }

    @Nullable
    public static JsonElement parseJsonFile(File file) {
        if (file != null && file.exists() && file.isFile() && file.canRead()) {
            try (InputStreamReader reader = new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8)) {
                return new JsonParser().parse(reader);
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    public static void writeJsonToFile(JsonObject root, File file) {
        try (OutputStreamWriter writer = new OutputStreamWriter(Files.newOutputStream(file.toPath()), StandardCharsets.UTF_8)) {
            writer.write(GSON.toJson(root));
        } catch (Exception ignored) {
        }
    }
}

