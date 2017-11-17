package com.aaronshaver;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class JsonHelpers {
    public static boolean isValidJson(String json) {
        Gson gson = new Gson();
        try {
            Object o = gson.fromJson(json, Object.class);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static JsonObject getJsonFromString(String string) {
        return new Gson().fromJson(string, JsonObject.class);
    }
}