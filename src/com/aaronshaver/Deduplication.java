package com.aaronshaver;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Deduplication {

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static boolean hasDupes(JsonObject json) {
        ArrayList<String> emails = JsonHelpers.getAllMembers(json, "email");
        Set<String> emails_set = new HashSet<>(emails);
        if (emails_set.size() < emails.size()) { // there are dupes if set of uniques is less than original list
            return true;
        }

        ArrayList<String> ids = JsonHelpers.getAllMembers(json, "_id");
        Set<String> ids_set = new HashSet<>(ids);
        if (ids_set.size() < ids.size()) {
            return true;
        }

        return false;
    }

    private static ArrayList<Leads> processDupes(ArrayList<Leads> leads) {
        ArrayList<Leads> listCopy = new ArrayList<>(leads);

        for (int i = 0; i < leads.size(); i++) {
            // create a subset of the entries to compare value-under-test against
            ArrayList<Leads> sublist = new ArrayList<>(leads.subList(i + 1, leads.size()));

            String outerEmail = leads.get(i).getEmail();
            String outerId = leads.get(i).get_id();

            boolean dupeFlag = false;
            for (int j = 0; j < sublist.size(); j++) {

                String innerEmail = sublist.get(j).getEmail();
                String innerId = sublist.get(j).get_id();

                if (outerEmail.equals(innerEmail) || outerId.equals(innerId)) {

                    Date outerDate = DateHelpers.getDateFromUTCString(leads.get(i).getEntryDate());
                    Date innerDate = DateHelpers.getDateFromUTCString(sublist.get(j).getEntryDate());

                    int keepIndex;
                    int removeIndex;
                    if(outerDate.after(innerDate)) {
                        // value-under-test (VUT) is newer date, therefore remove "inner", older value
                        // also: sublist is offset by one, because it excludes the value-under-test
                        keepIndex = i;
                        removeIndex = i + j + 1;
                    }
                    else {
                        // Otherwise, either: value-under-test (VUT) is older and should be removed
                        // Or: value-under-test (VUT) and our inner/comparison value have the same datetime
                        // If they have the same datetime: remove "outer"/VUT entry: it's earlier in the list of entries
                        keepIndex = i + j + 1;
                        removeIndex = i;
                    }

                    writeDupeDifferencesToLog(listCopy, keepIndex, removeIndex);
                    listCopy.remove(removeIndex);
                    dupeFlag = true;
                    break;
                }
            }
            if (dupeFlag) {
                break;
            }
        }
        return listCopy;
    }

    private static void writeDupeDifferencesToLog(ArrayList<Leads> leads, int keepIndex, int removeIndex) {
        LOGGER.log(Level.INFO, "[Deduplication] START duplicate logging ");
        Leads keep = leads.get(keepIndex);
        Leads remove = leads.get(removeIndex);
        LOGGER.log(Level.INFO, "[Deduplication] Keeping preferred record: " + keep.toString());
        LOGGER.log(Level.INFO, "[Deduplication] Removing duplicate record: " + remove.toString());

        String fieldMsg = "[Deduplication][Field] 'Field name': 'Remove val' -> 'Keep val' | ";
        LOGGER.log(Level.INFO, fieldMsg + "'_id': '" + remove.get_id() + "' -> '" + keep.get_id() + "'");
        LOGGER.log(Level.INFO, fieldMsg + "'email': '" + remove.getEmail() + "' -> '" + keep.getEmail() + "'");
        LOGGER.log(Level.INFO, fieldMsg + "'firstName': '" + remove.getFirstName() + "' -> '" + keep.getFirstName() + "'");
        LOGGER.log(Level.INFO, fieldMsg + "'lastName': '" + remove.getLastName() + "' -> '" + keep.getLastName() + "'");
        LOGGER.log(Level.INFO, fieldMsg + "'address': '" + remove.getAddress() + "' -> '" + keep.getAddress() + "'");
        LOGGER.log(Level.INFO, fieldMsg + "'entryDate': '" + remove.getEntryDate() + "' -> '" + keep.getEntryDate() + "'");
        LOGGER.log(Level.INFO, "[Deduplication] END duplicate logging ");
    }

    public static JsonObject dedupe(JsonObject json) {
        JsonArray array = null;
        for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
            array = entry.getValue().getAsJsonArray();
        }
        // take advantage of GSON by converting to array of a POJO, so we don't have to parse JSON directly
        Leads[] leadsArray = new Gson().fromJson(array, Leads[].class);
        ArrayList<Leads> leads = new ArrayList<>(Arrays.asList(leadsArray));

        while (hasDupes(JsonHelpers.convertLeadsArrayListToJsonObject(leads))) {
            leads = processDupes(leads);
        }
        return JsonHelpers.convertLeadsArrayListToJsonObject(leads);
    }
}
