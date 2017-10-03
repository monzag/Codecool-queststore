package com.codecool.jlamas.controllers;

import com.codecool.jlamas.database.ArtifactDAO;

public class ArtifactController {

    private ArtifactDAO artifacts = new ArtifactDAO();
    private ArtifactView artifactView = new ArtifactView();

    public ArtifactController() {
    }

    public void displayArtifacts() {
        artifactView.printArtifacts(artifacts.requestAll());
    }

    public void editArtifact() {

    }
}
