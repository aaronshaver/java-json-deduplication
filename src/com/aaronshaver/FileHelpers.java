package com.aaronshaver;

import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileHelpers {

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static boolean isValidPath(String path) {
        File f = new File(path);
        return(f.exists() && !f.isDirectory());
    }

    public static String GetStringFromFile(String filePath) {
        String data = null;
        try {
            data = new Scanner(new File(filePath)).useDelimiter("\\Z").next();
        }
        catch (FileNotFoundException e) {
            LOGGER.log(Level.WARNING, LogMessages.Messages.BAD_FILE_PATH.toString());
        }
        return data;
    }

    public static void WriteFileFromJson(JsonObject json) {
        String text = JsonHelpers.getStringFromJson(json);
        try (PrintWriter out = new PrintWriter("deduplicated.json")) {
            out.println(text);
        }
        catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, LogMessages.Messages.BAD_FILE_PATH.toString());
        }
    }
}
