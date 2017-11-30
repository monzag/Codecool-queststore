package com.codecool.jlamas.controllers;

import com.codecool.jlamas.database.DoneQuestDAO;
import com.codecool.jlamas.database.StudentDAO;
import com.codecool.jlamas.models.account.Student;
import com.codecool.jlamas.models.quest.Quest;
import com.codecool.jlamas.database.QuestDAO;

import java.util.ArrayList;
import java.util.Map;


public class QuestController implements Controller<Quest> {

    private QuestDAO questDAO;
    private DoneQuestDAO doneQuestDAO;
    private StudentDAO studentDAO;

    public QuestController() {
        this.questDAO = new QuestDAO();
        this.doneQuestDAO = new DoneQuestDAO();
        this.studentDAO = new StudentDAO();
    }

    public Quest get(String name) {
        return this.questDAO.selectQuest(name);
    }

    public ArrayList<Quest> getAll() {
        return this.questDAO.selectAll();
    }

    public void createFromMap(Map<String, String> inputs) {

    }

    public void editFromMap(Map<String, String > inputs, String name) {

    }

    public void remove(String name) {
        this.questDAO.deleteQuest(this.get(name));
    }

    public void markQuestAsDone(Student student, Quest quest) {
        doneQuestDAO.insert(student, quest);
        student.getWallet().put(quest.getReward());
        studentDAO.update(student);
    }
}
