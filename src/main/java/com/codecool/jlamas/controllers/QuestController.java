package com.codecool.jlamas.controllers;

import com.codecool.jlamas.models.quest.Quest;
import com.codecool.jlamas.database.QuestDAO;
import com.codecool.jlamas.views.QuestView;

public class QuestController {
    private QuestDAO questDAO;

    public QuestController() {
        this.questDAO = new QuestDAO();
    }

    public void editQuest(Quest quest) {

    }

    public void createQuest() {
        QuestView view = new QuestView();
        String name = view.getStrInput();
        String description = view.getStrInput();
        Integer reward = view.getIntInput();
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
