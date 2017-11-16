package com.codecool.jlamas.controllers;

import com.codecool.jlamas.database.DoneQuestDAO;
import com.codecool.jlamas.database.StudentDAO;
import com.codecool.jlamas.models.account.Student;
import com.codecool.jlamas.models.quest.Quest;
import com.codecool.jlamas.database.QuestDAO;
import com.codecool.jlamas.views.QuestView;

import java.util.ArrayList;

public class QuestController {
    private QuestDAO questDAO;
    private QuestView view;
    private DoneQuestDAO doneQuestDAO;
    private StudentDAO studentDAO;

    public QuestController() {
        this.questDAO = new QuestDAO();
        this.view = new QuestView();
        this.doneQuestDAO = new DoneQuestDAO();
        this.studentDAO = new StudentDAO();
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

    public void createQuest(Quest quest) {

        this.questDAO.insertQuest(quest);

    }

    public void deleteQuest(Quest quest) {
        questDAO.deleteQuest(quest);
    }

    public void markQuestAsDone(Student student, Quest quest) {
        doneQuestDAO.insert(student, quest);
        student.getWallet().put(quest.getReward());
        studentDAO.update(student);
    }

    public void showQuest() {
    }

    public ArrayList<Quest> showAllQuests() {
        ArrayList<Quest> questsList = new ArrayList<>();
        questsList = this.questDAO.selectAll();

        return questsList;
    }

    public Quest chooseQuest(String questName) {
        Quest quest = questDAO.selectQuest(questName);
        return quest;
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
