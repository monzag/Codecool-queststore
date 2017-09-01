package controllers;

import views.CodecoolerView;
import models.account.Codecooler;
import models.account.Student;
import models.account.Mentor;
import models.account.Admin;
import controllers.AdminController;
import database.StudentDAO;
import database.MentorDAO;
import database.AdminDAO;

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
                Mentor mentor = mentorData.load(login);
                if (mentor.getPassword().getValue().equals(password)) {
                    return mentor;
                }
            }
            else if (studentData.load(login) != null) {
                Student student = studentData.load(login);
                if (student.getPassword().getValue().equals(password)) {
                    return student;
                }
            }
            System.out.println("Wrong login data. Do you want to try again?");
            String tryAgain = CodecoolerView.getString("Y or anything else");
            if (!tryAgain.equalsIgnoreCase("y")) {
                isLogging = false;
            }
        }
        return null;
    }
}
