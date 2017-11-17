package com.aaronshaver;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

    public static String getStringFromJson(JsonObject json) {
        return json.toString();
    }

    public static boolean hasDupes(JsonObject json) {
        ArrayList<String> emails = new ArrayList<>();
        for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
            JsonArray array = entry.getValue().getAsJsonArray();
            for (JsonElement holder : array) {
                emails.add(holder.getAsJsonObject().getAsJsonPrimitive("email").getAsString());
            }
        }

        Set<String> emails_set = new HashSet<>(emails);

        if (emails_set.size() < emails.size()) { // there are dupes if set of uniques is less than original list
            return true;
        }

        ArrayList<String> ids = new ArrayList<>();
        for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
            JsonArray array = entry.getValue().getAsJsonArray();
            for (JsonElement holder : array) {
                ids.add(holder.getAsJsonObject().getAsJsonPrimitive("_id").getAsString());
            }
        }
        Set<String> ids_set = new HashSet<>(ids);

        if (ids_set.size() < ids.size()) { // there are dupes if set of uniques is less than original list
            return true;
        }

        return false;
    }
}
