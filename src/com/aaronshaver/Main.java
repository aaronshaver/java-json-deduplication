package com.aaronshaver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    private static Logging logging = new Logging();
    private static JsonUtils json = new JsonUtils();

    public static void main(String[] args) {
        logging.logStartedMessage();
        validateArguments(args);
        String filePath = args[0];
        validateFilePath(filePath);
        validateJson(filePath);
        exit();
    }

    private static void validateJson(String filePath) {
        String content = null;
        try {
            content = new Scanner(new File(filePath)).useDelimiter("\\Z").next();
        }
        catch (FileNotFoundException e) {
            logging.logBadFilePathMessage();
        }
        if (!json.isValidJson(content)) {
            exit();
        }
    }

    private static void validateFilePath(String filePath) {
        FileValidator fileValidator = new FileValidator();
        if(!fileValidator.isValidPath(filePath)) {
            logging.logBadFilePathMessage();
            exit();
        }
    }

    private static void validateArguments(String[] args) {
        if(args.length != 1)
        {
            logging.logBadArgumentsMessage();
            exit();
        }
    }

    private static void exit() {
        logging.logFinishedMessage();
        System.exit(0);
    }
}