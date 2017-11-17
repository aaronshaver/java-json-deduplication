package com.aaronshaver;

import com.google.gson.Gson;

import java.util.logging.Level;
import java.util.logging.Logger;

public class JsonHelpers {
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public boolean isValidJson(String json) {
        Gson gson = new Gson();
        try {
            Object o = gson.fromJson(json, Object.class);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, LogMessages.Messages.BAD_JSON.toString());
            return false;
        }
        return true;
    }
}