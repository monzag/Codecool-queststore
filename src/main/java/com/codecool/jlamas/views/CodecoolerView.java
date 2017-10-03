package com.codecool.jlamas.views;

import java.io.IOException;
import java.util.Scanner;

import com.codecool.jlamas.models.accountdata.Login;
import com.codecool.jlamas.models.accountdata.Mail;
import com.codecool.jlamas.exceptions.InvalidUserDataException;



public class CodecoolerView {

    public CodecoolerView() {
    }

    public String getString(String msg) {
        String userInput;

        System.out.println("\n" + msg + ": ");
        userInput = new Scanner(System.in).nextLine();
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

    public Login getLogin(String name, String surname) {
        String userLogin = name + "." + surname;

        while (!Login.isValid(userLogin)) {
            userLogin = getString("Generate login failed. \nPlease enter manually (5-20 chars, unique): ");
        }

        return new Login(userLogin);
    }

    private boolean isName(String name) {
        return name.matches("[A-Z][a-z]{2,17}");
    }

    public void reportWrongLoginData() {
        System.out.println("Wrong login data. Do you want to try again?");
    }

    public void printMenu(String[] options) {
        String output;

        output = "\nMENU\n";
        for (int i = 0; i < options.length; i++) {
            output += String.format("  %d) %s.\n", i+1, options[i]);
        }
        output += "  0) Exit.\n";

        System.out.println(output);
    }

    public int getMenuOption() {
        int option;
        Scanner input = new Scanner(System.in);

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

    public void printIndexError() {
        System.out.println("Bad number - record not exist!");
    }

    public void enterToContinue() {
        try {
            System.out.print("\nChoose an option: ");
            System.in.read();
        } catch (IOException e) {
            System.out.println("INPUT INTERRUPTED");
            e.printStackTrace();
        }
    }

}
