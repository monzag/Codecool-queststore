package com.codecool.jlamas.controllers;

import com.codecool.jlamas.exceptions.InvalidUserDataException;
import com.codecool.jlamas.models.account.Mentor;
import com.codecool.jlamas.models.accountdata.Login;
import com.codecool.jlamas.models.accountdata.Mail;
import com.codecool.jlamas.models.accountdata.Password;
import com.codecool.jlamas.views.MentorView;

public class MentorController {

    private MentorView mentorView = new MentorView();

    public MentorController() {
        
    }

    public void addMentor() {
        try {
            String name = mentorView.getName();
            String surname = mentorView.getSurname();
            Mail email = mentorView.getMail();

            Login login = new Login();
            Password password = new Password();
            Mentor mentor = new Mentor(login, password, email, name, surname);
            // MentorDAO - insert new

        } catch (InvalidUserDataException e) {

        }
    }

    public void editMentor() {
        ;
    }

    public void removeMentor() {
        // MentorDAO - displayAll
        // choose Mentor
        // MentorDAO - remove record
    }

    public void displayMentors() {
        ;
    }
}
