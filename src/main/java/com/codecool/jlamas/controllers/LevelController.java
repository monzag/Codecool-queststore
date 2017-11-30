package com.codecool.jlamas.controllers;

import com.codecool.jlamas.database.LevelDAO;
import com.codecool.jlamas.models.level.Level;

import java.util.List;
import java.util.Map;

public class LevelController {
    private LevelDAO levelDAO;

    public LevelController() {
    }

    public LevelController(LevelDAO levelDAO) {
        this.levelDAO = levelDAO;
    }

    public void editLevel(Map<String, String> inputs, String oldName) {
        String name = inputs.get("name").toString();
        Integer score = Integer.valueOf(inputs.get("score").toString());
        Level level = new Level(name, score);
        levelDAO.updateLevel(level, oldName);
    }

    public void createLevel(Map<String, String> inputs) {
        String name = inputs.get("name").toString();
        Integer score = Integer.valueOf(inputs.get("score"));
        Level level = new Level(name, score);
        levelDAO.insertLevel(level);
    }

    public void deleteLevel(Level level) {
        levelDAO.deleteLevel(level);
    }

    public List<Level> showAllLevels() {
        return levelDAO.selectAll();
    }

    public Level chooseLevel(String levelName) {
        Level level = levelDAO.selectLevel(levelName);
        return level;
    }
}
