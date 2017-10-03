package com.codecool.jlamas.views;

import java.util.ArrayList;
import java.util.Scanner;

public class GroupTeamView {

    public GroupTeamView() {

    }

    public String getString(String msg) {
        String userInput;

        System.out.print("\n" + msg + ": ");
        userInput = System.console().readLine();

        return userInput;
    }

    public <T> void printAll(ArrayList<T> objectList) {
        System.out.print("\nGroups: ");
        for (T object : objectList) {
            System.out.print(object);
        }
    }

    public Integer getInt(String msg) {
        Scanner input = new Scanner(System.in);
        Integer intInput;

        System.out.print("\n" + msg + ": ");
        while (! input.hasNextInt()) {
            System.out.println("This is not a number!");
            input.next();
        }
        intInput = input.nextInt();

        return intInput;
    }
}