package com.codecool.jlamas.models.quest;

public class Quest {
    private String name;
    private String description;
    private Integer reward;

    public Quest(){

    }

    public Quest(String name, String description, Integer reward) {
        this.name = name;
        this.description = description;
        this.reward = reward;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReward(Integer reward) {
        this.reward = reward;
    }

    public String toString() {
        String asString;

        asString = this.name + "  |  " + this.description + "  |  " + this.reward;

        asString += "\n================================\n";

        return asString;
    }

}
