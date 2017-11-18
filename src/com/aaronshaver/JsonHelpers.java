package com.aaronshaver;

import com.google.gson.*;

import java.util.*;
import java.util.logging.Level;
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

    private static ArrayList<Leads> processDupes(ArrayList<Leads> leads) {
        ArrayList<Leads> listCopy = new ArrayList<>(leads);

        for (int i = 0; i < leads.size(); i++) {
            // create a subset of the entries to compare value-under-test against
            ArrayList<Leads> sublist = new ArrayList<>(leads.subList(i + 1, leads.size()));
            boolean dupeFlag = false;
            String outerEmail = leads.get(i).getEmail();
            String outerId = leads.get(i).get_id();
            for (int j = 0; j < sublist.size(); j++) {
                String innerEmail = sublist.get(j).getEmail();
                String innerId = sublist.get(j).get_id();

                if (outerEmail.equals(innerEmail) || outerId.equals(innerId)) {
                    // sublist is offset by one, because it excludes value-under-test
                    int index = i + j + 1;
                    LOGGER.log(Level.INFO, "Found a dupe at index " + index);
                    dupeFlag = true;
                    listCopy.remove(index);
                    break;
                }
            }
            if (dupeFlag) {
                break;
            }
        }
        return listCopy;
    }

    public static JsonObject dedupe(JsonObject json) {
        JsonArray array = null;
        for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
            array = entry.getValue().getAsJsonArray();
        }
        // convert to an array of instances of our custom POJO class so we don't have to parse JSON directly
        Leads[] leadsArray = new Gson().fromJson(array, Leads[].class);
        ArrayList<Leads> leads = new ArrayList<>(Arrays.asList(leadsArray));

        while (hasDupes(convertLeadsArrayListToJsonObject(leads))) {
            leads = processDupes(leads);
        }

        return convertLeadsArrayListToJsonObject(leads);
    }

    private static JsonObject convertLeadsArrayListToJsonObject(ArrayList<Leads> leads) {
        Leads[] leadsDeduped = new Leads[leads.size()];
        leadsDeduped = leads.toArray(leadsDeduped);

        JsonArray jsonArray = getJsonArrayFromString(new Gson().toJson(leadsDeduped));
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("leads", jsonArray);
        return jsonObject;
    }
}
