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
            case "" :
                // TODO
                break;
            case "" :
                // TODO
        }

    }


}
