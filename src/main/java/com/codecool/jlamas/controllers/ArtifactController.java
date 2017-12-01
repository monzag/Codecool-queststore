package com.codecool.jlamas.controllers;

import com.codecool.jlamas.database.ArtifactDAO;
import com.codecool.jlamas.database.OwnedArtifactDAO;
import com.codecool.jlamas.database.StudentDAO;
import com.codecool.jlamas.exceptions.ArtifactNameAlreadyUsedException;
import com.codecool.jlamas.models.artifact.Artifact;

import java.util.ArrayList;
import java.util.Map;

public class ArtifactController implements Controller<Artifact> {

    private ArtifactDAO artifactDao;
    private OwnedArtifactDAO ownedArtifactDAO;
    private StudentDAO studentDAO;

    public ArtifactController() {
        this.artifactDao = new ArtifactDAO();
        this.ownedArtifactDAO = new OwnedArtifactDAO();
        this.studentDAO = new StudentDAO();
    }

    public Artifact get(String name) {
        return this.artifactDao.selectArtifact(name);
    }

    public ArrayList<Artifact> getAll() {
        return artifactDao.requestAll();
    }

    public void remove(String name) {
        artifactDao.deleteArtifact(artifactDao.selectArtifact(name));
    }

    public void createFromMap(Map<String, String> inputs) throws ArtifactNameAlreadyUsedException {
        String name = inputs.get("artifactName");
        if (!this.isArtifactNameUnique(name)) {
            throw new ArtifactNameAlreadyUsedException();
        }
        String description = inputs.get("description");
        Integer price = Integer.valueOf(inputs.get("price"));
        String type = inputs.get("type");

        this.artifactDao.insert(new Artifact(name, price, description, type));
    }

    public void editFromMap(Map<String, String> inputs, String name) throws ArtifactNameAlreadyUsedException {
        Artifact artifact = this.get(name);

        String newName = inputs.get("artifactName");
        if (!artifact.hasName(newName)) {
            if (!this.isArtifactNameUnique(newName)) {
                throw new ArtifactNameAlreadyUsedException();
            }
        }
        artifact.setName(newName);
        artifact.setDescription(inputs.get("description"));
        artifact.setPrice(Integer.valueOf(inputs.get("price")));
        artifact.setType(inputs.get("type"));

        this.artifactDao.update(artifact, name);
    }

    public boolean isArtifactNameUnique(String name) {
        for (Artifact artifact : this.getAll()) {
            if (artifact.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }

    @Deprecated
    public void createArtifact(String name, String description, Integer price, String type) {
        Artifact artifact = new Artifact(name, price, description, type);
        this.artifactDao.insert(artifact);
    }

    @Deprecated
    public void editArtifact(String oldName, String name, String description, Integer price, String type) {
        Artifact artifact = new Artifact(name, price, description, type);
        artifactDao.update(artifact, oldName);
    }

    @Deprecated
    public Artifact chooseArtifact(String artifactName) {
        return artifactDao.selectArtifact(artifactName);
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

