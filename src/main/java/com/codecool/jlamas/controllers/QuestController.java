package com.codecool.jlamas.controllers;

import com.codecool.jlamas.models.quest.Quest;
import com.codecool.jlamas.database.QuestDAO;

public class QuestController {
    private QuestDAO questDAO;

    public QuestController() {
        this.questDAO = new QuestDAO();
    }

    public void editQuest(Quest quest) {

    }

    public void createQuest() {
        this.questDAO.insertQuest("janusz", "kwasniewski", 10000);

    }

    public void deleteQuest() {

    }

    public void markQuestAsDone() {

    }

    public void showQuest() {

    }

    public void showAllQuests() {

    }
}
