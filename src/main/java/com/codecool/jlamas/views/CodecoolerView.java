package com.codecool.jlamas.views;

import java.util.Scanner;


public class CodecoolerView {

    public static String getString(String keyword) {
        System.out.println("Provide " + keyword + ": ");
        String userInput = System.console().readLine();

        return userInput;

    }

    public static void reportWrongLoginData() {
        System.out.println("Wrong login data. Do you want to try again?");
    }

    public static void reportResult(boolean state) {
        if (state) {
            System.out.println("Success!");
        } else {
            System.out.println("Failed!");
        }
    }

    public static void inConstruction() {
        System.out.println("\nMentor and student features in construction!\n");
    }

}
