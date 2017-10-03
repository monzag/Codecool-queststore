package com.codecool.jlamas.models.artifact;

public class Artifact {

    String name;
    Integer price;
    String description;

    public Artifact() {

    }

    public Artifact(String name, Integer price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }


}
