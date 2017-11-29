package com.codecool.jlamas.models.level;

public class Level {
    private String levelName;
    private Integer levelScore;

    public Level() {
    }

    public Level(String levelName, Integer levelScore) {
        this.levelName = levelName;
        this.levelScore = levelScore;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public Integer getLevelScore() {
        return levelScore;
    }

    public void setLevelScore(Integer levelScore) {
        this.levelScore = levelScore;
    }
}
