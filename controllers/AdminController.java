package controllers;

import models.account.Admin;
import models.account.Mentor;
import models.containers.MentorList;


public class AdminController {

    private Admin user;
    private MentorList<Mentor> mentors;

    public AdminController(Admin user) {
        this.user = user;
        this.mentors = new MentorList<Mentor>();
    }

    public void createMentor() {
        this.mentors.load();
        this.mentors.add(new Mentor());
        this.mentors.save();
    }

}
