package com.codecool.jlamas.views;

import java.util.ArrayList;
import java.util.Scanner;

import com.codecool.jlamas.models.account.Codecooler;
import com.codecool.jlamas.models.accountdata.Mail;

import com.codecool.jlamas.exceptions.InvalidUserDataException;



public class CodecoolerView {

    private Scanner input;

    public CodecoolerView() {
        this.input = new Scanner(System.in);
    }

    public String getString(String msg) {
        String userInput;

        System.out.print("\n" + msg + ": ");
        userInput = this.input.nextLine();

        return userInput;

    }

    public String getName() throws InvalidUserDataException {
        String err = "Name should consist of only letters(between 3 and 18) first should be capital.";
        String msg = "Provide name";
        String name;

        name = getString(msg);

        if (!this.isName(name)) {
            throw new InvalidUserDataException(err);
        }
        return name;
    }

    public String getSurname() throws InvalidUserDataException {
        String err = "Surame should consist of only letters(between 3 and 18) first should be capital.";
        String msg = "Provide surname";
        String surname;

        surname = getString(msg);

        if (!this.isName(surname)) {
            throw new InvalidUserDataException(err);
        }
        return surname;
    }

    public Mail getMail() throws InvalidUserDataException {
        String err = "Invalid email format";
        String mail;

        mail = this.getString("Provide email");
        if (!Mail.isValid(mail)) {
            throw new InvalidUserDataException(err);
        }
        return new Mail(mail);
    }

    private boolean isName(String name) {
        return name.matches("[A-Z][a-z]{2,17}");
    }

    public void reportWrongLoginData() {
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
