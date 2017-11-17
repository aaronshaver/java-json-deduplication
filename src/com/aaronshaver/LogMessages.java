package com.aaronshaver;

public class LogMessages {

    public enum Messages {
        BAD_FILE_PATH("Please point to a valid path for the .JSON file"),
        STARTED("Started. Please see deduplicated.json and deduplication_log_datetime.log file upon completion."),
        ARGUMENT_NUMBER("Please use 1 argument after the .jar file and a space pointing to .JSON"),
        BAD_JSON("Sorry, your file does not contain valid JSON"),
        FINISHED("Finished.");

        private final String message;

        Messages(final String message) {
            this.message = message;
        }

        @Override
        public String toString() { // TODO: seems goofy to have to call .toString() every time
            return message;
        }
    }
}
