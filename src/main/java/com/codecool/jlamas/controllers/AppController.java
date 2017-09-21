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

    public void login() {

        LoginDAO loginData = new LoginDAO();
        UserDAO userData = new UserDAO();
        CodecoolerView view = new CodecoolerView();

        boolean isLogging = true;
        while (isLogging) {

            String login = view.getString("Login");
            String password = view.getString("Password");

            if (loginData.matchLogin(login, password)) {
                Codecooler user = userData.getUser(login);
            }

            view.reportWrongLoginData();
            String tryAgain = view.getString("Y or anything else");
            if (!tryAgain.equalsIgnoreCase("y")) {
                isLogging = false;
            }
        }
    }
}
