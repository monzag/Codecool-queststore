package com.codecool.jlamas.controllers;

import com.codecool.jlamas.database.ArtifactDAO;
import com.codecool.jlamas.models.artifact.Artifact;
import com.codecool.jlamas.views.ArtifactView;

import java.util.ArrayList;

public class ArtifactController {

    private static final String EDIT_NAME = "1";
    private static final String EDIT_PRICE = "2";
    private static final String EDIT_DESCRIPTION = "3";

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
            String oldName = artifact.getName();
            artifactView.displayAttribute();
            String option = artifactView.getString("Your choice: ");

            switch(option) {
                case EDIT_NAME:
                    String name = artifactView.getString("New name: ");
                    artifact.setName(name);
                    break;
                case EDIT_PRICE:
                    Integer price = artifactView.getPrice();
                    artifact.setPrice(price);
                    break;
                case EDIT_DESCRIPTION:
                    String description = artifactView.getString("New description: ");
                    artifact.setDescription(description);
                    break;
                default: artifactView.printErrorMessage();
                    break;
            }
            artifacts.update(artifact, oldName);
        } catch (IndexOutOfBoundsException e) {
            e.getMessage();
        }
    }

    public Artifact chooseArtifact() throws IndexOutOfBoundsException {
        ArrayList<Artifact> allArtifacts = artifacts.requestAll();
        artifactView.printArtifacts(allArtifacts);
        Integer record = artifactView.getMenuOption();
        Integer index = record - 1;
        if (index >= allArtifacts.size()) {
            throw new IndexOutOfBoundsException();
        }
        return allArtifacts.get(index);
    }
}

