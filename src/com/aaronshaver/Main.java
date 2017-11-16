package com.aaronshaver;

public class Main {

    private static Logging logging = new Logging();

    private static void exit() {
        logging.logFinishedMessage();
        System.exit(0);
    }

    public static void main(String[] args) {
        logging.logStartedMessage();

        if(args.length != 1)
        {
            logging.logBadArgumentsMessage();
            exit();
        }

        String filePath = args[0];
        FileValidator fileValidator = new FileValidator();
        if(!fileValidator.isValidPath(filePath)) {
            logging.logBadFilePathMessage();
            exit();
        }

        exit();
    }
}
