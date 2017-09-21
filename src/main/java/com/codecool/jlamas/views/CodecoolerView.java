package com.codecool.jlamas.views;

import java.util.Scanner;


public class CodecoolerView {

    private Scanner input;

    public CodecoolerView() {
        this.input = new Scanner(System.in);
    }

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

    private void printMenu(String[] options) {
        String output;

        output = "\nMENU";
        for (int i = 0; i < options.length; i++) {
            output += String.format("  %d) %s.\n", i+1, options[i]);
        }
        output += "  0) Exit.\n";

        System.out.println(output);
    }

    public String getMenuOption(String[] options) {
        Integer option;

        this.printMenu(options);
        System.out.print("Choose option: ");

        try {
            option = Integer.parseInt(this.input.nextLine());
            return options[option-1];
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {

        }
        return "";
    }

}
