package com.codecool.jlamas.controllers;

import java.util.ArrayList;

import com.codecool.jlamas.database.MentorDAO;
import com.codecool.jlamas.exceptions.InvalidUserDataException;
import com.codecool.jlamas.models.account.Mentor;
import com.codecool.jlamas.models.accountdata.Login;
import com.codecool.jlamas.models.accountdata.Mail;
import com.codecool.jlamas.models.accountdata.Password;
import com.codecool.jlamas.views.MentorView;

public class MentorController {

    private MentorView mentorView = new MentorView();
    private MentorDAO mentorDao = new MentorDAO();

    public MentorController() {
        
    }

    public void addMentor() {
        try {
            String name = mentorView.getName();
            String surname = mentorView.getSurname();
            Mail email = mentorView.getMail();
            Login login = mentorView.getLogin(name, surname);
            Password password = getPassword();
            
            String classTag = "2017.1";
            Mentor mentor = new Mentor(login, password, email, name, surname, classTag);
            mentorDao.insert(mentor);

        } catch (InvalidUserDataException e) {
            System.out.println(e.getMessage());
        }
    }

    public void editMentor() {
        try {
            Mentor mentor = chooseMentor();

            // Demo version:
            mentorView.displayAttribute();
            mentorView.enterToContinue();
            System.out.println("New data: ");
            String name = System.console().readLine();
            mentor.setName(name);
            mentorDao.update(mentor);
        } catch (IndexOutOfBoundsException e) {
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
