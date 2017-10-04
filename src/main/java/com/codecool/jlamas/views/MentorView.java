package com.codecool.jlamas.views;

import java.util.ArrayList;

import com.codecool.jlamas.models.account.Mentor;


public class MentorView extends CodecoolerView {

    public MentorView() {
        super();
    }

    public void displayAttribute() {
        // Demo:
        String[] attributes = {"name", 
                               "surname",
                               "email",
                               "password"};
        printMenu(attributes);

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
