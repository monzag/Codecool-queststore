package com.codecool.jlamas.views;

import java.util.ArrayList;

import com.codecool.jlamas.exceptions.InvalidUserDataException;
import com.codecool.jlamas.models.account.Mentor;


public class MentorView extends CodecoolerView {

    public MentorView() {
        super();
    }

    public String getAttribute() {
        String attribute = null;
        String[] attributes = {"name", 
                               "surname",
                               "email",
                               "password"};
        printMenu(attributes);

        // Demo:
        System.out.println("\nYour choice: 1");
        try {
            attribute = getSurname();

        } catch (InvalidUserDataException e) {

        }

        return attribute;

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
