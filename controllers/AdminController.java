package controllers;

import models.account.Admin;
import models.account.Mentor;

import models.accountdata.Login;
import models.accountdata.Password;
import models.accountdata.Email;

import models.containers.MentorList;

import views.CodecoolerView;
import views.AdminView;

import java.util.Scanner;


public class AdminController {

    private Admin admin;
    private MentorList<Mentor> mentors;

    public AdminController(Admin admin) {
        this.admin = admin;
        this.mentors = new MentorList<Mentor>();
    }

    public void createMentor() {

        Login login = new Login(CodecoolerView.getString("login"));
        Password password = new Password(CodecoolerView.getString("password"));
        Mail mail = new Mail(CodecoolerView.getString("mail"));
        String name = CodecoolerView.getString("name");
        String surname = CodecoolerView.getString("surname");

        Mentor mentor = new Mentor(login, password, mail, name, surname);
        this.mentors.save(mentor);
    }

    public void menu() {
        Scanner input = new Scanner(System.in);
        AdminView.viewOptions();
        boolean inMenu = true;

        while (inMenu) {
            String option = input.nextLine();

            if (option.equals("1")) {
                AdminView.showAllMentors();
            }
            else if (option.equals("2")) {
                createMentor();
            }
            else if (option.equals("0")) {
                inMenu = false;
            }
        }
    }
}
