package com.aaronshaver;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String[] args) {
        addFileHandlerToLogger();

        LOGGER.log(Level.INFO, LogMessages.Messages.STARTED.toString());

        // 2022-03-30 note upon re-reading this code again years later:
        // This is violating single responsibility principle, doing way too much
        // I would abstract away a lot of the stuff in this file into separate classes
        // so that it would read more cleanly, among other benefits (like only updating
        // a small file for error-handling changes instead of a big Main.java)
        if (!isRequiredNumArguments(args)) {
            exit();
        }

        String filePath = args[0];
        if(!FileHelpers.isValidPath(filePath)) {
            LOGGER.log(Level.WARNING, LogMessages.Messages.BAD_FILE_PATH.toString());
            exit();
        }

        String data = FileHelpers.GetStringFromFile(filePath);
        if (!JsonHelpers.isValidJson(data)) {
            LOGGER.log(Level.WARNING, LogMessages.Messages.BAD_JSON.toString());
            exit();
        }

        JsonObject json = JsonHelpers.getJsonObjectFromString(data);

        JsonObject deduplicated_json;
        if (!Deduplication.hasDupes(json)) {
            LOGGER.log(Level.INFO, LogMessages.Messages.NO_DUPES.toString());
            deduplicated_json = json;
        }
        else {
            deduplicated_json = Deduplication.dedupe(json);
        }

        FileHelpers.WriteFileFromJson(deduplicated_json);

        exit();
    }

    private static void addFileHandlerToLogger() {
        TimeZone tz = TimeZone.getTimeZone("PST");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH-mm");
        df.setTimeZone(tz);
        String nowAsISO = df.format(new Date());

        FileHandler fileHandler;
        try {
            fileHandler = new FileHandler( "deduplicated_log_" + nowAsISO + ".log");
            LOGGER.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isRequiredNumArguments(String[] args) {
        if(args.length != 1)
        {
            LOGGER.log(Level.WARNING, LogMessages.Messages.ARGUMENT_NUMBER.toString());
            return false;
        }
        return true;
    }

    private static void exit() {
        LOGGER.log( Level.INFO, LogMessages.Messages.FINISHED.toString());
        System.exit(0);
    }
}
