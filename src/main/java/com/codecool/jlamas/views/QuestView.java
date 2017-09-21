package com.codecool.jlamas.views;

import java.util.Scanner;

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

        intInput = input.nextLine();

        return intInput;
    }

    public void printQuestData(Quest quest) {
        System.out.println(quest);
    }
}
