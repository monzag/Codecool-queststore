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
        String name = view.getString("Type quest name");
        String description = view.getString("Type quest description");
        Integer reward = view.getInt("Type reward value");
        Quest quest = new Quest(name, description, reward);

        this.questDAO.insertQuest(quest);

    }

    public void deleteQuest() {
        ArrayList<Quest> questsList = this.questDAO.selectAll();
        QuestView view = new QuestView();
        boolean correctChoice = false;
        Integer questID;
        Integer index = null;
        Quest quest;
        view.printQuestData(questsList);

        while (!correctChoice) {
            questID = view.getInt("Choose number of quest You want to delete or 0 to exit: ");

            if (questID <= questsList.size() & questID > 0) {
                correctChoice = true;
                index = questID - 1;
                quest = questsList.get(index);
                questDAO.deleteQuest(quest);

            } else if (questID == 0) {
                correctChoice = true;
            }
        }
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
