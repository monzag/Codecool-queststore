package controllers;

import database.MentorDAO;
import models.account.Admin;


public class AdminController {

    private Admin user;
    private MentorDAO db;

    public AdminController(Admin user) {
        this.user = user;
        this.db = new MentorDAO();
    }


}
