package com.codecool.jlamas.controllers;

import com.codecool.jlamas.models.account.Student;

public class StudentMenuController {

    public static final String[] MENU = {"Display wallet",
                                         "Buy artifact",
                                         "Display level"};

    public static final String DISPLAY_WALLET = "1";
    public static final String BUY_ARTIFACT = "2";
    public static final String DISPLAY_LEVEL = "3";
    

    Student student;

    public StudentMenuController(Student student) {
        this.student = student;
    }

    public void start() {
        
    }
}