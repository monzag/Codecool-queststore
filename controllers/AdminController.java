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

}
