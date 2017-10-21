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

    public void printQuestData(ArrayList<Quest> questList) {
        Integer i = 1;
        for(Quest quest : questList) {
            System.out.print(i + ". ");
            System.out.println(quest);
            i++;
        }
    }

    public void printEditMenu() {
        String[] menuOptions = {"\n1.Edit name.", "2.Edit description.", "3.Edit reward."};

        for (String option : menuOptions) {
            System.out.println(option);
        }
    }

    public int getMenuOption() {
        int option;
        Scanner input = new Scanner(System.in);

        System.out.print("Choose option: ");
        while (!input.hasNextInt()) {
            System.out.println("This is not a number!");
            input.next();
        }

        option = input.nextInt();
        return option;
    }

    public void printIndexError() {
        System.out.println("Bad number - record not exist!");
    }
}
