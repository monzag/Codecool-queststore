package com.codecool.jlamas.views;

import java.util.Scanner;
import com.codecool.jlamas.models.quest.Quest;
import java.util.ArrayList;

public class QuestView {
    Scanner input;
    public QuestView() {
        this.input = new Scanner(System.in);

    }

    public String getStrInput() {
        String strInput;

        strInput = input.nextLine();

        return strInput;
    }

    public Integer getIntInput() {
        Integer intInput;

        intInput = input.nextInt();

        return intInput;
    }

    public void printQuestData(ArrayList<Quest> questList) {
        for(Quest quest : questList) {
            System.out.println(quest);
        }
    }
}
