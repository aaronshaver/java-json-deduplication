package com.aaronshaver;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Logging {
    private static final Logger logger = Logger.getLogger(Logging.class.getName());

    public void logStartedMessage() {
        logger.log(Level.INFO, "Started. Please see _deduplicated.json and .log file upon completion.");
    }

    public void logFinishedMessage() {
        logger.log(Level.INFO, "Finished.");
    }

    public void logBadArgumentsMessage() {
        logger.log(Level.WARNING, "Please use 1 argument after the .jar file and a space pointing to .JSON file");
    }

    public void logBadFilePathMessage() {
        logger.log(Level.WARNING, "Please point to a valid path for the .JSON file");
    }
}
