package com.codecool.jlamas.models.quest;

public class Quest {
    private String name;
    private String description;
    private Integer reward;
    private boolean isDone;

    public Quest(){

    }

    public Quest(String name, String description, Integer reward) {
        this.name = name;
        this.description = description;
        this.reward = reward;
        this.isDone = false;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Integer getReward() {
        return this.reward;
    }

    public boolean getStatus() {
        return this.isDone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReward(Integer reward) {
        this.reward = reward;
    }

    public void changeStatus() {
        if (this.isDone == false) {
            this.isDone = true;

        } else {
            this.isDone = false;
        }

    }

    public String toString() {
        String asString;

        asString = "\n" + this.name + " | " + this.description + " | " + this.reward;
        asString += "\n================================\n";

        return asString;
    }

}
