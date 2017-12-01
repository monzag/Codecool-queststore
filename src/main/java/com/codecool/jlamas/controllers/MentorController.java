package com.codecool.jlamas.controllers;

import java.util.ArrayList;
import java.util.Map;

import com.codecool.jlamas.database.MentorDAO;
import com.codecool.jlamas.exceptions.EmailAlreadyUsedException;
import com.codecool.jlamas.exceptions.InvalidUserDataException;
import com.codecool.jlamas.models.account.Mentor;
import com.codecool.jlamas.models.accountdata.Group;
import com.codecool.jlamas.models.accountdata.Login;
import com.codecool.jlamas.models.accountdata.Mail;
import com.codecool.jlamas.models.accountdata.Password;


public class MentorController implements Controller<Mentor> {

    private MentorDAO mentorDao;
    private GroupController groupController = new GroupController();

    public MentorController() {
        this.mentorDao = new MentorDAO();
        this.groupController = new GroupController();
    }

    public Mentor get(String login) {
        return this.mentorDao.getMentor(login);
    }

    public ArrayList<Mentor> getAll() {
        return mentorDao.requestAll();
    }

    public void remove(String login) {
        mentorDao.delete(this.get(login));
    }

    public void createFromMap(Map<String, String> attrs) throws InvalidUserDataException {

        if (!Mail.isValid(attrs.get("email"))) {
            throw new EmailAlreadyUsedException();
        }
        Mail email = new Mail(attrs.get("email"));

        String name = attrs.get("name");
        String surname = attrs.get("surname");
        Login login = Login.generate(name, surname);
        Password password = Password.generate();
        SendMail sendMail = new SendMail();
        String message = login.getValue() + " " + password.getValue();
        sendMail.sendMail(email.getValue(), message);
        Group group = this.groupController.get(attrs.get("class"));

        Mentor mentor = new Mentor(login, password, email, name, surname, group);
        mentor.correctNames();

        mentorDao.insert(mentor);
    }

    public void editFromMap(Map<String, String> attrs, String login) throws EmailAlreadyUsedException {

        Mentor mentor = this.get(login);

        if (!mentor.hasEmail(attrs.get("email"))) {
            if (!Mail.isValid(attrs.get("email"))) {
                throw new EmailAlreadyUsedException();
            }
            mentor.setEmail(new Mail(attrs.get("email")));
        }

        mentor.setName(attrs.get("name"));
        mentor.setSurname(attrs.get("surname"));
        mentor.setGroup(this.groupController.get(attrs.get("class")));

        mentor.correctNames();
        mentorDao.update(mentor);
    }

}
