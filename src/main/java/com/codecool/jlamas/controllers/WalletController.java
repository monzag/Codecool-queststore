package com.codecool.jlamas.controllers;

import com.codecool.jlamas.database.DoneQuestDAO;
import com.codecool.jlamas.models.account.Student;
import com.codecool.jlamas.models.artifact.Artifact;
import com.codecool.jlamas.models.quest.Quest;
import com.codecool.jlamas.views.QuestView;
import com.codecool.jlamas.views.StudentView;

public class WalletController {

    Student student;
    private DoneQuestDAO doneQuestsDAO = new DoneQuestDAO();
    // private OwnedArtifactDAO ownedArtifactDAO = new OwnedArtifactDAO();
    private QuestView questView = new QuestView();
    private StudentView studentView = new StudentView();

    public WalletController(Student student) {
        this.student = student;
    }

    public void displayBalance() {
        this.studentView.showBalance(this.student.getWallet().getBalance());
    }

    public void displayDoneQuests() {
        questView.printQuestData(this.student.getWallet().getDoneQuests());
    }

    public void displayOwnedArtifacts() {
        // TODO;
    }

    public void addDoneQuest(Quest quest) {
        this.student.getWallet().getDoneQuests().add(quest);
        doneQuestsDAO.insert(this.student, quest);
    }

    public void addOwnedArtifact(Artifact artifact) {
    //     this.student.getWallet().getDoneQuests().add(artifact);
    //     ownedArtifactDAO.insert(this.student.getLogin(), artifact);
    }

}