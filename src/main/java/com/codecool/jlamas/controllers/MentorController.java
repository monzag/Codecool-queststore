package com.codecool.jlamas.controllers;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import com.codecool.jlamas.database.MentorDAO;
import com.codecool.jlamas.exceptions.EmailAlreadyUsedException;
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
    private GroupController groupController = new GroupController();

    public MentorController() {
        
    }

    public Mentor getMentor(String login) {
        return this.mentorDao.getMentor(login);
    }

    public ArrayList<Mentor> getAllMentors() {
        return mentorDao.requestAll();
    }

    public void removeMentor(String login) {
        mentorDao.delete(this.getMentor(login));
    }

    public void createMentorFromMap(Map<String, String> attrs) throws InvalidUserDataException {

        Password password = this.getPassword();

        if (!Mail.isValid(attrs.get("email"))) {
            throw new EmailAlreadyUsedException();
        }

        Mail email = new Mail(attrs.get("email"));
        String name = attrs.get("name");
        String surname = attrs.get("surname");
        Login login = Login.generate(name, surname);
        Group group = groupController.getGroup(attrs.get("class"));

        Mentor mentor = new Mentor(login, password, email, name, surname, group);
        mentor.correctNames();

        mentorDao.insert(mentor);
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

}
