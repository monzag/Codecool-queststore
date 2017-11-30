package com.codecool.jlamas.controllers;

import com.codecool.jlamas.database.DoneQuestDAO;
import com.codecool.jlamas.database.StudentDAO;
import com.codecool.jlamas.exceptions.QuestNameAlreadyUsedException;
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

    public void createFromMap(Map<String, String> inputs) throws QuestNameAlreadyUsedException {
        String name = inputs.get("questName");
        if (!this.isNameUnique(name)) {
            throw new QuestNameAlreadyUsedException();
        }
        String description = inputs.get("description");
        Integer reward = Integer.valueOf(inputs.get("reward").toString());

        questDAO.insertQuest(new Quest(name, description, reward));
    }

    public void editFromMap(Map<String, String > inputs, String name)  throws QuestNameAlreadyUsedException {
        Quest quest = this.get(name);

        String newName = inputs.get("questName");
        if (!quest.hasName(newName)) {
            if (!this.isNameUnique(name)) {
                throw new QuestNameAlreadyUsedException();
            }
        }
        quest.setName(newName);
        quest.setDescription(inputs.get("description"));
        quest.setReward(Integer.valueOf(inputs.get("reward")));

        questDAO.updateQuest(quest, name);
    }

    public void remove(String name) {
        this.questDAO.deleteQuest(this.get(name));
    }

    public boolean isNameUnique(String name) {
        for (Quest quest : this.getAll()) {
            if (quest.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }

    @Deprecated
    public void createQuest(Quest quest) {

        this.questDAO.insertQuest(quest);

    }

    @Deprecated
    public void deleteQuest(Quest quest) {
        questDAO.deleteQuest(quest);
    }

    public void markQuestAsDone(Student student, Quest quest) {
        doneQuestDAO.insert(student, quest);
        student.getWallet().put(quest.getReward());
        studentDAO.update(student);
    }

    @Deprecated
    public Quest chooseQuest(String questName) {
        Quest quest = questDAO.selectQuest(questName);
        return quest;
    }
}
