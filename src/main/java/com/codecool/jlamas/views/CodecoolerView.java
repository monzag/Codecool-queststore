package com.codecool.jlamas.views;

import java.util.Scanner;


public class CodecoolerView {

    private Scanner input;

    public CodecoolerView() {
        this.input = new Scanner(System.in);
    }

    private String getString(String msg) {
        String userInput;

        System.out.println(msg);
        userInput = input.nextLine();

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

    public void printMenu(String[] options) {
        String output;

        output = "\nMENU";
        for (int i = 0; i < options.length; i++) {
            output += String.format("  %d) %s.\n", i+1, options[i]);
        }
        output += "  0) Exit.\n";

        System.out.println(output);
    }

    public int getMenuOption() {
        int option;

        System.out.print("Choose option: ");
        while (!input.hasNextInt()) {
            printErrorMessage();
            input.next();
        }

        option = input.nextInt();
        return option;
    }

    public void printErrorMessage() {
        System.out.println("It's not a number!");
    }

}
