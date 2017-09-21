package com.codecool.jlamas.controllers;

import com.codecool.jlamas.database.MentorDAO;
import com.codecool.jlamas.models.account.Admin;
import com.codecool.jlamas.models.account.Mentor;
import com.codecool.jlamas.views.AdminView;
// import com.codecool.jlamas.models.accountdata.Login;
// import com.codecool.jlamas.models.accountdata.Password;
// import com.codecool.jlamas.models.accountdata.Mail;
// import com.codecool.jlamas.views.CodecoolerView;

import java.util.ArrayList;

public class AdminMenuController {

    public static final String[] options = {"Display mentors", 
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
    private MentorDAO mentorData = new MentorDAO();
    ArrayList<Mentor> mentors;

    public AdminMenuController(Admin admin) {
        this.admin = admin;
        this.mentors = mentorData.loadAll();
    }

    public void menu() {
        adminView.printMenu(options);
        int userChoice = adminView.getMenuOption();

        boolean start = true;
        while (start) {
            switch(userChoice) {
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
                    start = false;
            }
        }
    }

    public void displayAllMentors() {
        ;
    }

    public void createMentor() {
        // Login login = new Login(CodecoolerView.getString("login"));
        // Password password = new Password(CodecoolerView.getString("password"));
        // Mail mail = new Mail(CodecoolerView.getString("mail"));
        // String name = CodecoolerView.getString("name");
        // String surname = CodecoolerView.getString("surname");

        // Mentor mentor = new Mentor(login, password, mail, name, surname);
        // // mentorData.save(mentor);
    }

    public void editMentor() {
        ;
    }

    public void addClass() {
        ;
    }

    public void addLevel() {
        ;
    }

    public void editQuest() {
        ;
    }
}
