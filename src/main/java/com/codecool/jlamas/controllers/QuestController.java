package com.codecool.jlamas.controllers;

import com.codecool.jlamas.database.DoneQuestDAO;
import com.codecool.jlamas.database.StudentDAO;
import com.codecool.jlamas.models.account.Student;
import com.codecool.jlamas.models.quest.Quest;
import com.codecool.jlamas.database.QuestDAO;


import java.util.ArrayList;

public class QuestController {
    private QuestDAO questDAO;
    private DoneQuestDAO doneQuestDAO;
    private StudentDAO studentDAO;

    public QuestController() {
        this.questDAO = new QuestDAO();
        this.doneQuestDAO = new DoneQuestDAO();
        this.studentDAO = new StudentDAO();
    }

    public void editQuest(String oldName, Quest quest) {
        questDAO.updateQuest(quest, oldName);

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

    public ArrayList<Quest> showAllQuests() {
        return this.questDAO.selectAll();
    }

    public Quest chooseQuest(String questName) {
        Quest quest = questDAO.selectQuest(questName);
        return quest;
    }
}
