package com.codecool.jlamas.controllers;

import com.codecool.jlamas.models.account.Student;
import com.codecool.jlamas.views.StudentView;

public class StudentMenuController {

    public static final String[] MENU = {"Display wallet",
                                         "Buy artifact",
                                         "Display level"};

    private static final int DISPLAY_WALLET = 1;
    private static final int BUY_ARTIFACT = 2;
    private static final int DISPLAY_LEVEL = 3;
    private static final int EXIT = 0;

    private StudentView studentView;
    private Student student;
    private WalletController walletController;

    public StudentMenuController(Student student) {
        this.student = student;
        this.studentView = new StudentView();
        this.walletController = new WalletController(student);
    }

    public void start() {
        Integer option = 1;
        while (!option.equals(EXIT)) {
            studentView.printMenu(MENU);
            option = studentView.getMenuOption();

            switch(option) {
                case DISPLAY_WALLET: displayWallet();
                    break;
                case BUY_ARTIFACT: buyArtifact();
                    break;
                case DISPLAY_LEVEL: displayLevel();
                    break;
            }
        }
    }

    public void displayWallet() {
        this.walletController.displayBalance();
        //walletController.displayDoneQuests();
        //walletController.displayOwnedArtifacts();
    }

    public void buyArtifact() {
        ;
    }

    public void displayLevel() {
        ;
    }
}