package com.aaronshaver;

public class LogMessages {

    public enum Messages {
        BAD_FILE_PATH("Sorry, there was an error reading from or writing to the file path given"),
        STARTED("Started. Please see deduplicated.json and deduplicated_log_<datetime>.log file upon completion."),
        ARGUMENT_NUMBER("Please use 1 argument after the .jar file and a space pointing to .JSON"),
        BAD_JSON("Sorry, your file does not contain valid JSON"),
        FINISHED("Finished."),
        NO_DUPES("Your input file did not have any duplicate entries.");

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
