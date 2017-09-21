package com.codecool.jlamas.controllers;

import com.codecool.jlamas.models.account.Mentor;

import com.codecool.jlamas.views.MentorView;


public class MentorController {

    private Mentor user;
    private MentorView view;

    public MentorController(Mentor user) {
        this.user = user;
    }

    public void init() {
        this.view =  new MentorView();
    }

    public void start() {
        String option;

        option = "";
        while (option.equals("Exit")) {
            option = view.getMenuOption();
            this.resolveOption(option);
        }
    }

    private void resolveOption(String option) {
    // adding new option should add also to view MENU

        switch (option) {
            case "Print class" :
                // TODO
                break;
            case "Print team" :
                // TODO
            case "Create team" :
                // TODO
                break;
            case "Add new Student" :
                // TODO
                break;
            case "Add new Quest" :
                // TODO
                break;
            case "Add new Artifat" :
                // TODO
                break;
            case "Edit existing Quest" :
                // TODO
                break;
            case "Edit existing Artifact" :
                // TODO
                break;
            case "Mark Quest as done" :
                // TODO
                break;
            case "Mark Artifact as done" :
                // TODO
                break;
        }

    }


}
