package com.codecool.jlamas.models.accountdata;


import com.codecool.jlamas.models.quest.Quest;

import java.util.ArrayList;

public class Wallet {

    private Integer balance;
    private ArrayList<Quest> doneQuests;
    private ArrayList<Artifact> ownedArtifacts;

    public Wallet() {
        this.balance = 0;
    }

    public Wallet(Integer balance) {
        this.balance = balance;
    }

    public Integer getBalance() {
        return this.balance;
    }

    public void put(Integer amount) {
        this.balance += amount;
    }

    private boolean has(Integer amount) {
        return this.balance >= amount;
    }

    public boolean take(Integer amount) {
        if (this.has(amount)) {
            this.balance -= amount;
            // if transaction was done return true
            return true;
        }
        // if transaction failed return false
        return false;
    }

    public void setDoneQuests(ArrayList<Quest> doneQuests) {
        this.doneQuests = doneQuests;
    }

    public void setOwnedArtifacts(ArrayList<Artifact> ownedArtifacts) {
        this.ownedArtifacts = ownedArtifacts;
    }

}
