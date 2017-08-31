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

        boolean successful = false;

        while (!successful) {

            String login = CodecoolerView.getString("login");

            if (!adminData.load(login).equals(null)) {
                Admin admin = adminData.load(login);
                return admin;
            }
            else if (!mentorData.load(login).equals(null)) {
                Mentor mentor = mentorData.load(login);
                return mentor;
            }
            else if (!studentData.load(login).equals(null)) {
                Student student = studentData.load(login);
                return student;
            }
        }
        return null;
    }
}
