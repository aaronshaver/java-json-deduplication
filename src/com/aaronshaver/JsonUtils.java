package com.aaronshaver;

import com.google.gson.Gson;

import java.util.logging.Level;
import java.util.logging.Logger;

public class JsonUtils {
    private static final Logger logger = Logger.getLogger(Logging.class.getName());

    public boolean isValidJson(String json) {
        Gson gson = new Gson();
        try {
            Object o = gson.fromJson(json, Object.class);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Sorry, your file does not contain valid JSON");
            return false;
        }
        return true;
    }
}