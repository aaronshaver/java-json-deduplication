package com.aaronshaver;

import com.google.gson.*;

import java.util.*;

public class JsonHelpers {
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

    public static boolean hasDupes(JsonObject json) {
        ArrayList<String> emails = getAllMembers(json, "email");
        Set<String> emails_set = new HashSet<>(emails);
        if (emails_set.size() < emails.size()) { // there are dupes if set of uniques is less than original list
            return true;
        }

        ArrayList<String> ids = getAllMembers(json, "_id");
        Set<String> ids_set = new HashSet<>(ids);
        if (ids_set.size() < ids.size()) {
            return true;
        }

        return false;
    }

    private static ArrayList<String> getAllMembers(JsonObject json, String memberName) {
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

    public static JsonObject dedupe(JsonObject json) {
        JsonArray array = null;
        for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
            array = entry.getValue().getAsJsonArray();
        }
        Leads[] leadsArray = new Gson().fromJson(array, Leads[].class);
        ArrayList<Leads> leads = new ArrayList<>(Arrays.asList(leadsArray));

        // TODO: stub; actual logic to remove dupes goes here ------------------------
        for (int i = 0; i < leads.size(); i++) {
            System.out.println(leads.get(i).getAddress());
        }
        leads.remove(0);
        // TODO: ---------------------------------------------------------------

        Leads[] leadsDeduped = new Leads[leads.size()];
        leadsDeduped = leads.toArray(leadsDeduped);

        JsonArray jsonArray = getJsonArrayFromString(new Gson().toJson(leadsDeduped));
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("leads", jsonArray);
        return jsonObject;
    }
}
