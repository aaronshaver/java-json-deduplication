package com.aaronshaver;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class JsonHelpers {

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static boolean isValidJson(String json) {
        Gson gson = new Gson();
        try {
            gson.fromJson(json, Object.class);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static JsonObject getJsonObjectFromString(String string) {
        return new Gson().fromJson(string, JsonObject.class);
    }

    public static JsonArray getJsonArrayFromString(String string) {
        return new Gson().fromJson(string, JsonArray.class);
    }

    public static String getStringFromJson(JsonObject json) {
        return json.toString();
    }

    // 2022-03-30 refactor notes after not having seen this code for years:
    // this is a poorly-named method; what it's doing is grabbing all values for a particular
    // key; should be named something like getAllValuesForKey(..., String keyName)
    // I guess I was using domain-specific naming, but even then it could be better, like:
    // getAllMemberValuesByKey, or... something
    public static ArrayList<String> getAllMembers(JsonObject json, String memberName) {
        ArrayList<String> members = new ArrayList<>();
        for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
            JsonArray array = entry.getValue().getAsJsonArray();
            for (JsonElement element : array) {
                members.add(element.getAsJsonObject().getAsJsonPrimitive(memberName).getAsString());
            }
        }
        return members;
    }

    public static int getEntitiesCount(JsonObject json) {
        Set<Map.Entry<String, JsonElement>> set = json.entrySet();
        int set_size = set.size();
        if (set_size == 0) {
            return 0;
        }
        else {
            JsonArray array = null;
            for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
                array = entry.getValue().getAsJsonArray();
            }
            assert array != null;
            return array.size();
        }
    }

    public static JsonObject convertLeadsArrayListToJsonObject(ArrayList<Leads> leads) {
        Leads[] leadsDeduped = new Leads[leads.size()];
        leadsDeduped = leads.toArray(leadsDeduped);

        JsonArray jsonArray = getJsonArrayFromString(new Gson().toJson(leadsDeduped));
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("leads", jsonArray);
        return jsonObject;
    }
}
