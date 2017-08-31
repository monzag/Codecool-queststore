package controllers;

import models.account.Admin;
import models.account.Mentor;

import models.accountdata.Login;
import models.accountdata.Password;
import models.accountdata.Email;

import models.containers.MentorList;


public class AdminController {

    private Admin user;
    private MentorList<Mentor> mentors;

    public AdminController(Admin user) {
        this.user = user;
        this.mentors = new MentorList<Mentor>();
    }

    public boolean addMentor(Mentor mentor) {
        this.mentors.load();
        if (this.mentors.add(mentor)) {
            this.mentors.save();
            // report true if suceed
            return true;
        }
        // reports false if failed
        return false;
    }

    public void createMentor() {
        Integer id;
        Login login;
        Password password;
        Email email;
        String name;
        String surname;
        Mentor mentor;

        name = CodecoolerView.getString();
        surname = CodecoolerView.getString();
        login = new Login(CodecoolerView.getLogin());
        password = new Password(CodecoolerView.getLogin());
        email = new Email(CodecoolerView.getEmail());

        CodecoolerView.reportResult(this.addMentor(mentor))

    }

}
