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
        this.boolean = false;
    }

}
