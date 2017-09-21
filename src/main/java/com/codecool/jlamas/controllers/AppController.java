package com.codecool.jlamas.controllers;

import com.codecool.jlamas.views.CodecoolerView;
import com.codecool.jlamas.models.account.Codecooler;
import com.codecool.jlamas.models.account.Student;
import com.codecool.jlamas.models.account.Mentor;
import com.codecool.jlamas.models.account.Admin;
import com.codecool.jlamas.controllers.AdminMenuController;
import com.codecool.jlamas.database.LoginDAO;
import com.codecool.jlamas.database.UserDAO;

public class AppController {

    public AppController() {
        LoginDAO loginData = new LoginDAO();
        UserDAO userData = new UserDAO();
        CodecoolerView view = new CodecoolerView();
    }

    public void login() {

        boolean isLogging = true;
        while (isLogging) {

            String login = view.getString("Login");
            String password = view.getString("Password");

            if (loginData.matchLogin(login, password)) {
                launchUserController(login);
            }

            view.reportWrongLoginData();
            String tryAgain = view.getString("Y or anything else");
            if (!tryAgain.equalsIgnoreCase("y")) {
                isLogging = false;
            }
        }
    }

    public void launchUserController(String login) {

        String userType = userData.getType(login);

        if (userType.equals("admin")) {
            Admin admin = userData.getAdmin(login);
            AdminMenuController adminMenu = new AdminMenuController(admin);
            adminMenu.start();
        } else if (userType.equals("mentor")) {
            Mentor mentor = userData.getMentor(login);
            MentorMenuController mentorMenu = new MentorMenuController(mentor);
            mentorMenu.start();
        } else if (userType.equals("student")) {
            // TODO
        }

    }
}
