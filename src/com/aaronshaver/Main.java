package com.aaronshaver;

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
    private static JsonHelpers json = new JsonHelpers();

    public static void main(String[] args) {
        addFileHandlerToLogger();
        LOGGER.log(Level.INFO, LogMessages.Messages.STARTED.toString());
        validateArgumentNumber(args);
        String filePath = args[0];
        validateFilePath(filePath);
        validateJson(filePath);
        exit();
    }

    private static void addFileHandlerToLogger() {
        TimeZone tz = TimeZone.getTimeZone("PST");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH-mm");
        df.setTimeZone(tz);
        String nowAsISO = df.format(new Date());

        FileHandler fileHandler;
        try {
            fileHandler = new FileHandler( "dedupe_" + nowAsISO + ".log");
            LOGGER.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void validateJson(String filePath) {
        String data = FileHelpers.GetStringFromFile(filePath);
        if (!json.isValidJson(data)) {
            exit();
        }
    }

    private static void validateFilePath(String filePath) {
        FileHelpers fileHelpers = new FileHelpers();
        if(!fileHelpers.isValidPath(filePath)) {
            LOGGER.log(Level.WARNING, LogMessages.Messages.BAD_FILE_PATH.toString());
            exit();
        }
    }

    private static void validateArgumentNumber(String[] args) {
        if(args.length != 1)
        {
            LOGGER.log(Level.WARNING, LogMessages.Messages.ARGUMENT_NUMBER.toString());
            exit();
        }
    }

    private static void exit() {
        LOGGER.log( Level.INFO, LogMessages.Messages.FINISHED.toString());
        System.exit(0);
    }
}