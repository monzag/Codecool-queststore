package com.codecool.jlamas.views;

import java.util.ArrayList;

public class GroupTeamView {

    public GroupTeamView() {

    }

    public String getString(String msg) {
        String userInput;

        System.out.print("\n" + msg + ": ");
        userInput = System.console().readLine();

        return userInput;
    }

    public <T> void printObjects(ArrayList<T> objectList) {
        System.out.print("\nGroups: ");
        for (T object : objectList) {
            System.out.print(object);
        }
    }
}