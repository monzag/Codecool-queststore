package com.codecool.jlamas.controllers;

import com.codecool.jlamas.database.MentorDAO;
import com.codecool.jlamas.models.account.Admin;
import com.codecool.jlamas.models.account.Mentor;
import com.codecool.jlamas.models.quest.Quest;
import com.codecool.jlamas.views.AdminView;
// import com.codecool.jlamas.models.accountdata.Login;
// import com.codecool.jlamas.models.accountdata.Password;
// import com.codecool.jlamas.models.accountdata.Mail;
// import com.codecool.jlamas.views.CodecoolerView;

import java.util.ArrayList;

public class AdminMenuController {

    public static final String[] OPTIONS = {"Display mentors", 
                                            "Add mentor",
                                            "Edit mentor",
                                            "Add class",
                                            "Add level",
                                            "Edit quest"};

    private static final int DISPLAY_MENTORS = 1;
    private static final int ADD_MENTOR = 2;
    private static final int EDIT_MENTOR = 3;
    private static final int ADD_CLASS = 4;
    private static final int ADD_LEVEL = 5;
    private static final int EDIT_QUEST = 6;
    private static final int EXIT = 0;

    private Admin admin;
    private AdminView adminView = new AdminView();;
    private MentorController mentorController = new MentorController();
    ArrayList<Mentor> mentors;

    public AdminMenuController(Admin admin) {
        this.admin = admin;
    }

    public void start() {
        boolean run = true;
        while (run) {
            adminView.printMenu(OPTIONS);
            Integer option = adminView.getMenuOption();

            switch(option) {
                case DISPLAY_MENTORS: displayAllMentors();
                    break;
                case ADD_MENTOR: createMentor();
                    break;
                case EDIT_MENTOR: editMentor();
                    break;
                case ADD_CLASS: addClass();
                    break;
                case ADD_LEVEL: addLevel();
                    break; 
                case EDIT_QUEST: editQuest();
                    break;
                case EXIT:
                    run = false;
            }
        }
    }

    public void displayAllMentors() {
        mentorController.displayMentors();
    }

    public void createMentor() {
        mentorController.addMentor();
    }

    public void editMentor() {
        mentorController.editMentor();
    }

    public void addClass() {
        ;
    }

    public void addLevel() {
        ;
    }

    public void editQuest() {
        QuestController questController = new QuestController();
        questController.showAllQuests();
        // user choice -> quest
        Quest quest = new Quest();
        questController.editQuest(quest);
    }
}
