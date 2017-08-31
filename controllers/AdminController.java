package controllers;

import models.account.Admin;
import models.account.Mentor;

import models.accountdata.Login;
import models.accountdata.Password;
import models.accountdata.Email;

import models.containers.MentorList;

import views.CodecoolerView;


public class AdminController {

    private Admin admin;

    public AdminController(Admin admin) {
        this.admin = admin;
    }

    public void createMentor() {
        Integer id;
        Login login;
        Password password;
        Email email;
        String name;
        String surname;
        Mentor mentor;

        try {
            name = CodecoolerView.getString("name");
            surname = CodecoolerView.getString("surname");
            login = new Login(CodecoolerView.getString("login"));
            password = new Password(CodecoolerView.getString("password"));
            email = new Email(CodecoolerView.getString("email"));

            CodecoolerView.reportResult(this.addMentor(mentor))

        } catch (Exception e) {
            CodecoolerView.reportResult(false);
        }

    }


}
