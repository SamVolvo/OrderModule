package com.samvolvo.utils;

import com.samvolvo.Main;

public class Logger {
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String CYAN = "\u001B[36m";
    private static final String ORANGE = "\u001B[38;5;214m";

    public static void loading(String message){
        System.out.println(CYAN + "[loading]: " + RESET + message);
    }
    public static void error(String message){
        System.out.println(RED + "[ERROR]: " + RESET + message);
    }
    public static void succes(String message){
        System.out.println(GREEN + "[SUCCES]: " + RESET + message);
    }
    public static void info(String message){
        System.out.println(BLUE + "[INFO]: " + RESET + message);
    }
    public static void warning(String message){
        System.out.println(ORANGE + "[WARNING]: " + RESET + message);
    }
    public static void debug(String message){
        if (Main.isDebugMode()){
            System.out.println(YELLOW + "[DEBUG]: " + RESET + message);
            return;
        }
        return;
    }
}
