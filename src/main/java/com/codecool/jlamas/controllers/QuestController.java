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
        Integer questID;
        Integer index = null;
        Quest quest;
        String questOldName;

        view.printQuestData(questsList);

        while (!correctChoice) {
            questID = view.getInt("Choose number of quest You want to edit: ");

            if (questID <= questsList.size() & questID > 0) {
                correctChoice = true;
                index = questID - 1;
            }
        }

        quest = questsList.get(index);
        questOldName = quest.getName();
        this.dataEdit(quest);
        questDAO.updateQuest(quest, questOldName);


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

    public void dataEdit(Quest quest) {
        QuestView view = new QuestView();
        boolean correctChoice = false;
        Integer optionNumber = null;

        while (!correctChoice) {
            view.printEditMenu();
            optionNumber = view.getInt("Choose data to edit: ");

            if (optionNumber > 0 && optionNumber <= 3) {
                correctChoice = true;
            }
        }

        if (optionNumber == 1) {
            String name = view.getString("Enter new name for quest: ");
            quest.setName(name);

        } else if (optionNumber == 2) {
            String description = view.getString("Enter new description for quest: ");
            quest.setDescription(description);

        } else if (optionNumber == 3) {
            Integer reward = view.getInt("Enter new reward value for quest: ");
            quest.setReward(reward);
        }
    }
}
