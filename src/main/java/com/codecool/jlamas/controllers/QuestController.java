package com.codecool.jlamas.controllers;

import com.codecool.jlamas.models.quests.Quest;

public class QuestController {
    private Quest quest;
    private QuestView view;

    public QuestController(Quest quest) {
        this.quest = quest;
        this.view = new QuestView();
    }
}
