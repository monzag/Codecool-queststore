package com.codecool.jlamas.views;

import java.util.Scanner;
import com.codecool.jlamas.models.quest.Quest;
import java.util.ArrayList;

public class QuestView {

    public QuestView() {

    }

    public String getString(String msg) {
        String userInput;

        System.out.print("\n" + msg + ": ");
        userInput = System.console().readLine();

        return userInput;
    }

    public Integer getInt(String msg) {
        Scanner input = new Scanner();
        Integer intInput;

        System.out.print("\n" + msg + ": ");
        while (! input.hasNextInt()) {
            System.out.println("This is not a number!");
            input.next();
        }
        intInput = input.nextInt();

        return intInput;
    }

    public void printQuestData(ArrayList<Quest> questList) {
        for(Quest quest : questList) {
            System.out.println(quest);
        }
    }

    public void printEditMenu() {
        String[] menuOptions = {"1.Edit name.", "2.Edit description.", "3.Edit reward."};

        for (String option : menuOptions) {
            System.out.println(option);
        }
    }
}
