package com.aaronshaver;

public class Main {


    public static void main(String[] args) {
        ConsoleOutput console = new ConsoleOutput();
        if(args.length != 1)
        {
            console.writeBadArgumentsMessage();
            System.exit(0);
        }

        System.out.println(String.format("Input file is '%s'", args[0]));
    }
}
