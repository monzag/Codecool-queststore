package com.codecool.jlamas.controllers;

import com.codecool.jlamas.models.quest.Quest;
import com.codecool.jlamas.database.QuestDAO;
import com.codecool.jlamas.views.QuestView;
import java.util.ArrayList;

public class QuestController {
    private QuestDAO questDAO;

    public QuestController() {
        this.questDAO = new QuestDAO();
    }

    public void editQuest() {
        ArrayList<Quest> questsList = this.questDAO.selectAll();
        QuestView view = new QuestView();
        boolean correctChoice = false;
        Integer index = null;
        view.printQuestData(questsList);

        while (!correctChoice) {
            Integer questID = view.getInt("Choose number of quest You want to edit: ");

            if (questID <= questsList.size() & questID > 0) {
                correctChoice = true;
                index = questID - 1;
            }
        }

        Quest quest = questsList.get(index);
        System.out.println(quest);
    }

    public void createQuest() {
        QuestView view = new QuestView();
        String name = view.getString("Type quest name");
        String description = view.getString("Type quest description");
        Integer reward = view.getInt("Type reward value");
        Quest quest = new Quest(name, description, reward);

        this.questDAO.insertQuest(quest);

    }

    public void deleteQuest() {

    }

    public void markQuestAsDone() {

    }

    public void showQuest() {

    }

    public void showAllQuests() {
        QuestView view = new QuestView();
        view.printQuestData(this.questDAO.selectAll());

    }
}
