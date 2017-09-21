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

        StudentDAO studentData = new StudentDAO();
        MentorDAO mentorData = new MentorDAO();
        AdminDAO adminData = new AdminDAO();

        boolean isLogging = true;

        while (isLogging) {

            String login = CodecoolerView.getString("login");
            String password = CodecoolerView.getString("password");

            if (adminData.load(login) != null) {
                Admin admin = adminData.load(login);
                if (admin.getPassword().getValue().equals(password)) {
                    return admin;
                }
            }
            else if (mentorData.load(login) != null) {
                CodecoolerView.inConstruction();
                // Mentor mentor = mentorData.load(login);
                // if (mentor.getPassword().getValue().equals(password)) {
                //     return mentor;
                // }
            }
            else if (studentData.load(login) != null) {
                CodecoolerView.inConstruction();
                // Student student = studentData.load(login);
                // if (student.getPassword().getValue().equals(password)) {
                //     return student;
                // }
            }
            CodecoolerView.reportWrongLoginData();
            String tryAgain = CodecoolerView.getString("Y or anything else");
            if (!tryAgain.equalsIgnoreCase("y")) {
                isLogging = false;
            }
        }
        return null;
    }
}
