package com.codecool.jlamas.controllers;

import com.codecool.jlamas.database.TeamPurchaseDAO;
import com.codecool.jlamas.models.account.Student;
import com.codecool.jlamas.models.artifact.Artifact;
import com.codecool.jlamas.models.artifact.TeamPurchase;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class TeamPurchaseController {

    TeamPurchaseDAO teamPurchaseDAO = new TeamPurchaseDAO();

    public TeamPurchaseController() {

    }

    public boolean addTeamPurchase(ArrayList<Student> students, Artifact artifact) {

        Integer price = Math.round(Integer.divideUnsigned(artifact.getPrice(), students.size()));
        Integer id = ThreadLocalRandom.current().nextInt(1, 999999);
        for (Student student : students) {
            teamPurchaseDAO.insert(new TeamPurchase(id, artifact, student, price, false));
            return true;
        }
        return false;
    }

    public boolean acceptPurchaseRequest(Student student, Integer id) {
        ArrayList<TeamPurchase> purchases = teamPurchaseDAO.requestAllBy(student);
        for (TeamPurchase purchase : purchases) {
            if (purchase.getId().equals(id)) {
                purchase.setMarked(true);
                teamPurchaseDAO.update(purchase);
                return true;
            }
        }
        return false;
    }

    public boolean cancelTeamPurchase(Integer id) {
        ArrayList<TeamPurchase> purchases = teamPurchaseDAO.requestAllBy(id);
        for (TeamPurchase purchase : purchases) {
            teamPurchaseDAO.delete(purchase);
            return true;
        }
        return false;
    }
}
