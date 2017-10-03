package com.codecool.jlamas.controllers;

import com.codecool.jlamas.database.ArtifactDAO;
import com.codecool.jlamas.views.ArtifactView;

public class ArtifactController {

    private ArtifactDAO artifacts = new ArtifactDAO();
    private ArtifactView artifactView = new ArtifactView();

    public ArtifactController() {
    }

    public void displayArtifacts() {
        artifactView.printArtifacts(artifacts.requestAll());
    }

    public void editArtifact() {
        try {
            Artifact artifact = chooseArtifact();
            artifactView.displayAttribute();
            String option = mentorView.getString("Your choice: ");
            switch(option) {
                case EDIT_NAME:
                    String name = mentorView.getName();
                    mentor.setName(name);
                    break;
                case EDIT_SURNAME:
                    String surname = mentorView.getSurname();
                    mentor.setSurname(surname);
                    break;
                case EDIT_EMAIL:
                    Mail email = mentorView.getMail();
                    mentor.setEmail(email);
                    break;
                case EDIT_PASSWORD:
                    String passwordText = mentorView.getString("New password: ");
                    mentor.setPassword(new Password(passwordText));
                    break;
                default: mentorView.printErrorMessage();
                    break;
            }
            mentorDao.update(mentor);
        } catch (IndexOutOfBoundsException|InvalidUserDataException e) {
            e.getMessage();
        }
    }
}

