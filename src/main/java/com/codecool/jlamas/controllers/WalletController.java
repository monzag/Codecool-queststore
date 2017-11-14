package com.codecool.jlamas.controllers;

import com.codecool.jlamas.database.DoneQuestDAO;
import com.codecool.jlamas.database.OwnedArtifactDAO;
import com.codecool.jlamas.models.account.Student;
import com.codecool.jlamas.models.artifact.Artifact;
import com.codecool.jlamas.models.quest.Quest;
import com.codecool.jlamas.views.ArtifactView;
import com.codecool.jlamas.views.QuestView;
import com.codecool.jlamas.views.StudentView;

import java.util.ArrayList;

import com.codecool.jlamas.models.account.Student;
import com.codecool.jlamas.models.artifact.Artifact;
import com.codecool.jlamas.models.quest.Quest;
import com.codecool.jlamas.views.QuestView;
import com.codecool.jlamas.views.StudentView;

public class WalletController {

    Student student;
    private DoneQuestDAO doneQuestsDAO = new DoneQuestDAO();
    private OwnedArtifactDAO ownedArtifactDAO = new OwnedArtifactDAO();
    private QuestView questView = new QuestView();
    private StudentView studentView = new StudentView();
    private ArtifactController artifactController = new ArtifactController();
    private ArtifactView artifactView = new ArtifactView();

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
        artifactView.printArtifacts(ownedArtifactDAO.requestAllBy(this.student));
    }

    public void addDoneQuest(Quest quest) {
        this.student.getWallet().getDoneQuests().add(quest);
        doneQuestsDAO.insert(this.student, quest);
    }

    public boolean buyArtifact() throws IndexOutOfBoundsException {
        Artifact artifact = artifactController.chooseArtifact();
        if (student.getWallet().take(artifact.getPrice())) {
            student.getWallet().
        }
    }

    public void addOwnedArtifact(Artifact artifact) {
        this.student.getWallet().getOwnedArtifacts().add(artifact);
        ownedArtifactDAO.insert(this.student, artifact);
    }

}
