package com.codecool.jlamas.controllers;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import com.codecool.jlamas.database.MentorDAO;
import com.codecool.jlamas.exceptions.InvalidUserDataException;
import com.codecool.jlamas.models.account.Mentor;
import com.codecool.jlamas.models.accountdata.Group;
import com.codecool.jlamas.models.accountdata.Login;
import com.codecool.jlamas.models.accountdata.Mail;
import com.codecool.jlamas.models.accountdata.Password;
import com.codecool.jlamas.views.MentorView;

public class MentorController {

    private MentorView mentorView = new MentorView();
    private MentorDAO mentorDao = new MentorDAO();

    public MentorController() {
        
    }

    public ArrayList<Mentor> getAllMentors() {
        return mentorDao.requestAll();
    }

    public Mentor getMentor(String login) {
        return this.mentorDao.getMentor(login);
    }

    public void createMentorFromMap(Map<String, String> attrs) {
        // TODO data validation check MentorView class <- CodecoolerView

        Password password = this.getPassword();
        Mail email = new Mail(attrs.get("email"));
        String name = attrs.get("name");
        String surname = attrs.get("surname");
        Login login = mentorView.getLogin(name, surname);

        // TODO no group choosen
        Group group = new GroupController().getGroup(attrs.get("class"));

        mentorDao.insert(new Mentor(login, password, email, name, surname, group));
    }

    public void editMentorFromMap(Map<String, String> attrs, String login) {
        // TODO data validation check MentorView class <- CodecoolerView

        Mentor mentor = this.getMentor(login);
        mentor.setEmail(new Mail(attrs.get("email")));
        mentor.setName(attrs.get("name"));
        mentor.setSurname(attrs.get("surname"));

        // TODO no group choosen
        Group group = new GroupController().getGroup(attrs.get("class"));
        mentor.setGroup(group);

        mentorDao.update(mentor);
    }

    public Password getPassword() {
        String alphabet= "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        String value = "";
        while (value.length() < 8) {
            char sign = alphabet.charAt(random.nextInt(36));
            value += sign;
        }

        return new Password(value);
    }

    public void removeMentor(String login) {
        mentorDao.delete(this.getMentor(login));
    }

}
