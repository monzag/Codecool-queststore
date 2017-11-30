package com.codecool.jlamas.controllers;

import com.codecool.jlamas.database.LevelDAO;
import com.codecool.jlamas.models.level.Level;

import java.util.List;
import java.util.Map;

public class LevelController {
    private LevelDAO levelDAO;

    public LevelController() {
        this.levelDAO = new LevelDAO();
    }



    public void editLevel(Map<String, String> inputs, String oldName) {
        String name = inputs.get("name");
        Integer score = Integer.valueOf(inputs.get("score"));
        Level level = new Level(name, score);
        levelDAO.updateLevel(level, oldName);
    }

    public void createLevel(Map<String, String> inputs) {
        String name = inputs.get("name");
        Integer score = Integer.valueOf(inputs.get("score"));
        Level level = new Level(name, score);
        levelDAO = new LevelDAO();
        levelDAO.insertLevel(level);
    }

    public void deleteLevel(String levelName) {
        levelDAO = new LevelDAO();
        Level level = chooseLevel(levelName);
        levelDAO.deleteLevel(level);
    }

    public List<Level> showAllLevels() {

        return levelDAO.selectAll();
    }

    public Level chooseLevel(String levelName) {
        levelDAO = new LevelDAO();
        Level level = levelDAO.selectLevel(levelName);
        return level;
    }
}
