package com.codecool.jlamas.controllers;

import java.util.ArrayList;
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

    private static final String EDIT_NAME = "1";
    private static final String EDIT_SURNAME = "2";
    private static final String EDIT_EMAIL = "3";
    private static final String EDIT_PASSWORD = "4";

    private MentorView mentorView = new MentorView();
    private MentorDAO mentorDao = new MentorDAO();

    public MentorController() {
        
    }

    public void addMentor() {
//        try {
//            String name = mentorView.getName();
//            String surname = mentorView.getSurname();
//            Mail email = mentorView.getMail();
//            Login login = mentorView.getLogin(name, surname);
//            Password password = getPassword();
//            Group group = getGroup();
//            Mentor mentor = new Mentor(login, password, email, name, surname, group);
//            mentorDao.insert(mentor);
//
//        } catch (InvalidUserDataException e) {
//            System.out.println(e.getMessage());
//        }
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

    public Group getGroup() {
        Group group = new Group();
        GroupController groupController = new GroupController();
        try {
            group = groupController.chooseGroup();
        } catch (IndexOutOfBoundsException e) {
            e.getMessage();
        }
        return group;
    }

    public void editMentor() {
        try {
            Mentor mentor = chooseMentor();
            mentorView.displayAttribute();
            String option = mentorView.getString("Your choice: ");

            switch(option) {
                case EDIT_NAME: 
                    String name = mentorView.getName();
                    mentor.setName(name);
                    break;
                case EDIT_SURNAME: 
                    String surname = mentorView.getSurname();
                    mentor.setSurname(surname);
                    break;
                case EDIT_EMAIL: 
//                    Mail email = mentorView.getMail();
//                    mentor.setEmail(email);
//                    break;
                case EDIT_PASSWORD: 
                    String passwordText = mentorView.getString("New password: ");
                    mentor.setPassword(new Password(passwordText));
                    break;
                default: mentorView.printErrorMessage();
                    break;
            }

            mentorDao.update(mentor);
        } catch (IndexOutOfBoundsException|InvalidUserDataException e) {
            e.getMessage();
        }   
    }

    public void removeMentor() {
        try {
            Mentor mentor = chooseMentor();
            mentorDao.delete(mentor);

        } catch (IndexOutOfBoundsException e) {
            e.getMessage();
        }
    }

    public Mentor chooseMentor() throws IndexOutOfBoundsException {
        ArrayList<Mentor> mentors = mentorDao.requestAll();
        mentorView.displayAll(mentors);
        Integer record = mentorView.getMenuOption();
        Integer index = record - 1;
        if (index >= mentors.size()) {
            throw new IndexOutOfBoundsException();
        }
        return mentors.get(index);
    }

    public void displayMentors() {
        mentorView.displayAll(mentorDao.requestAll());
    }
}
