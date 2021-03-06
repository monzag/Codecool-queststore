package com.codecool.jlamas.controllers;

import com.codecool.jlamas.database.*;
import com.codecool.jlamas.models.account.Student;
import com.codecool.jlamas.models.artifact.Artifact;
import com.codecool.jlamas.models.quest.Quest;

import java.util.List;

public class WalletController {

    Student student;
    private StudentDAO studentDAO = new StudentDAO();
    private OwnedArtifactDAO ownedArtifactDAO = new OwnedArtifactDAO();
    public WalletController(Student student) {
        this.student = student;
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
