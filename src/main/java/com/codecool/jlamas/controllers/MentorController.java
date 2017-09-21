package com.codecool.jlamas.controllers;

import com.codecool.jlamas.models.account.Mentor;

import com.codecool.jlamas.views.MentorView;


public class MentorController {

    private Mentor user;
    private MentorView view;

    public MentorController(Mentor user) {
        this.user = user;
        this.view =  new MentorView();
    }

    public void start() {
        String option;

        option = "";
        while (option.equals("Exit")) {
            view.printMenu();
            option = view.getMenuOption();
        }
    }


}
