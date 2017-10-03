package com.codecool.jlamas.views;

public class GroupTeamView {

    public GroupTeamView() {

    }

    public String getString(String msg) {
        String userInput;

        System.out.print("\n" + msg + ": ");
        userInput = System.console().readLine();

        return userInput;
    }
}