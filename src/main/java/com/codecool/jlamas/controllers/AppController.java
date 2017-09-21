package com.codecool.jlamas.controllers;

import com.codecool.jlamas.views.CodecoolerView;
import com.codecool.jlamas.models.account.Codecooler;
import com.codecool.jlamas.models.account.Student;
import com.codecool.jlamas.models.account.Mentor;
import com.codecool.jlamas.models.account.Admin;
import com.codecool.jlamas.controllers.AdminMenuController;
import com.codecool.jlamas.database.StudentDAO;
import com.codecool.jlamas.database.MentorDAO;
import com.codecool.jlamas.database.AdminDAO;

public class AppController {

    public static Codecooler login() {

        LoginDAO loginData = new LoginDAO();
        CodecoolerView view = new CodecoolerView();

        boolean isLogging = true;
        while (isLogging) {

            String login = CodecoolerView.getString("Login");
            String password = CodecoolerView.getString("Password");

            matchLogin(login, password);

            CodecoolerView.reportWrongLoginData();
            String tryAgain = CodecoolerView.getString("Y or anything else");
            if (!tryAgain.equalsIgnoreCase("y")) {
                isLogging = false;
            }
        }
        return null;
    }

    public boolean matchLogin(String login, String password) {
        
    }
}
