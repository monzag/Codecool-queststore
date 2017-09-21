package com.codecool.jlamas.views;

import java.util.Scanner;
import com.codecool.jlamas.models.quest.Quest;
import java.util.ArrayList;

public class QuestView {
    Scanner input;
    public QuestView() {
        this.input = new Scanner(System.in);

    }

    public String getString(String msg) {
        String userInput;

        System.out.print("\n" + msg + ": ");
        userInput = this.input.nextLine();

        return userInput;
    }

    public Integer getInt(String msg) {
        Integer intInput;

        System.out.print("\n" + msg + ": ");
        intInput = input.nextInt();

        return intInput;
    }

    public void printQuestData(ArrayList<Quest> questList) {
        for(Quest quest : questList) {
            System.out.println(quest);
        }
    }
}
