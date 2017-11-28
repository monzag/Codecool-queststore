package com.codecool.jlamas.controllers;

import com.codecool.jlamas.database.ArtifactDAO;
import com.codecool.jlamas.database.OwnedArtifactDAO;
import com.codecool.jlamas.database.StudentDAO;
import com.codecool.jlamas.models.artifact.Artifact;

import java.util.ArrayList;

public class ArtifactController {

    private ArtifactDAO artifactDao = new ArtifactDAO();
    private OwnedArtifactDAO ownedArtifactDAO = new OwnedArtifactDAO();
    private StudentDAO studentDAO = new StudentDAO();

    public ArtifactController() {
    }

    public ArrayList<Artifact> displayArtifacts() {
        return artifactDao.requestAll();
    }

    public void removeArtifact(String name) {
        Artifact artifact = artifactDao.selectArtifact(name);
        artifactDao.deleteArtifact(artifact);
    }


    public void createArtifact(String name, String description, Integer price, String type) {
        Artifact artifact = new Artifact(name, price, description, type);
        this.artifactDao.insert(artifact);
    }

    public void editArtifact(String oldName, String name, String description, Integer price, String type) {
        Artifact artifact = new Artifact(name, price, description, type);
        artifactDao.update(artifact, oldName);
    }

    public Artifact chooseArtifact(String artifactName) {
        Artifact artifact = artifactDao.selectArtifact(artifactName);
        return artifact;
    }

//    public boolean useArtifact() throws IndexOutOfBoundsException {
//        StudentController students = new StudentController();
//        try {
//            Student student = students.chooseStudent();
//            Artifact artifact = chooseArtifact();
//            ownedArtifactDAO.delete(artifact, student);
//        } catch (IndexOutOfBoundsException e) {
//            artifactView.printErrorMessage();
//            return false;
//        }
//        return true;
//    }

}

