package models.containers;

import database.MentorDAO;

import models.account.Mentor;


public class MentorList<Mentor> extends CodecoolerList {

    private MentorDAO db;

    public MentorList() {
        super();
        db = new MentorDAO();
    }

    public void load() {
        this.codecoolers = db.loadAll();
    }

    public void save(Mentor mentor) {
        db.save(mentor);
    }


}
