package com.codecool.jlamas.views;

import java.util.ArrayList;

import com.codecool.jlamas.exceptions.InvalidUserDataException;
import com.codecool.jlamas.models.account.Mentor;


public class MentorView extends CodecoolerView {

    public MentorView() {
        super();
    }

    public String getAttribute() {
        String[] attributes = {"name", 
                               "surname",
                               "email",
                               "password"};
        printMenu(attributes);

        System.out.println("\nYour choice: 1");
        try {
            String name = getSurname();
            return name;

        } catch (InvalidUserDataException e) {

        }

    }

    public void displayAll(ArrayList<Mentor> mentors) {
        System.out.println("\nUsers:");
        Integer number = 1;
        for (Mentor mentor : mentors) {
            System.out.println(number + ". " + mentor);
            number++;
        }
    }
}
