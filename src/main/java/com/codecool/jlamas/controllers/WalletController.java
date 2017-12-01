package com.codecool.jlamas.controllers;

import com.codecool.jlamas.database.*;
import com.codecool.jlamas.models.account.Student;
import com.codecool.jlamas.models.artifact.Artifact;
import com.codecool.jlamas.models.artifact.TeamPurchase;
import com.codecool.jlamas.models.quest.Quest;

import java.util.List;

public class WalletController {

    Student student;
    private StudentDAO studentDAO = new StudentDAO();
    private ArtifactDAO artifactDAO = new ArtifactDAO();
    private DoneQuestDAO doneQuestsDAO = new DoneQuestDAO();
    private OwnedArtifactDAO ownedArtifactDAO = new OwnedArtifactDAO();
    private TeamPurchaseDAO teamPurchaseDAO = new TeamPurchaseDAO();
    private ArtifactController artifactController = new ArtifactController();

    public WalletController(Student student) {
        this.student = student;
    }

    public void addDoneQuest(Quest quest) {
        this.student.getWallet().getDoneQuests().add(quest);
        doneQuestsDAO.insert(this.student, quest);
    }

    public boolean buyArtifact(Artifact artifact) {
        try {
            if (student.getWallet().take(artifact.getPrice())) {
                studentDAO.update(student);
                return addOwnedArtifact(artifact);
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }



    public void addTeamPurchase(TeamPurchase purchase) {
        this.student.getWallet().getPendingPurchases().add(purchase);
        teamPurchaseDAO.insert(purchase);
    }

    public boolean addOwnedArtifact(Artifact artifact) {
        this.student.getWallet().getOwnedArtifacts().add(artifact);
        return ownedArtifactDAO.insert(this.student, artifact);
    }

    public Integer getDoneQuestsValue() {
        List<Quest> doneQuests = student.getWallet().getDoneQuests();
        Integer totalValue = 0;

        for (Quest quest : doneQuests) {
            totalValue += quest.getReward();
        }

        return totalValue;
    }
}
