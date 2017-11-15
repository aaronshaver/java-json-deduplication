package com.aaronshaver;

public class ConsoleOutput {

    public String writeStartMessage() {
        String returnMessage = "Deduplicating...";
        System.out.println(returnMessage);
        return returnMessage;
    }

    public String writeBadArgumentsMessage() {
        String returnMessage = "Please use one argument, after the .jar file and a space, pointing to .JSON file";
        System.out.println(returnMessage);
        return returnMessage;
    }
}
