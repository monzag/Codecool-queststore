package com.codecool.jlamas.controllers;

import com.codecool.jlamas.database.ArtifactDAO;
import com.codecool.jlamas.database.QuestDAO;

public class WalletController {

    QuestDAO quests;
    ArtifactDAO artifacts;

    public WalletController() {
        this.quests = new QuestDAO();
        this.artifacts = new ArtifactDAO();
    }


}
