package com.codecool.jlamas.views;

import com.codecool.jlamas.models.artifact.Artifact;

import java.util.ArrayList;

public class ArtifactView {

    public ArtifactView() {

    }

    public void printQuestData(ArrayList<Artifact> artifactList) {
        Integer i = 1;
        for(Artifact artifact : artifactList) {
            System.out.print(i + ". ");
            System.out.println(artifact);
            i++;
        }
    }
}
