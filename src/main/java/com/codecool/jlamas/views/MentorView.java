package com.codecool.jlamas.views;

import java.util.ArrayList;

import com.codecool.jlamas.models.account.Mentor;


public class MentorView extends CodecoolerView {

    public MentorView() {
        super();
    }

    public void displayAll(ArrayList<Mentor> mentors) {
        System.out.println("\nUsers:");
        Integer number = 1;
        for (Mentor mentor : mentors) {
            System.out.println(number + ". " + mentor);
            number++;
        }
    }

    public void displayAttribute() {
        String[] attributes = {"name", 
                               "surname",
                               "email",
                               "password"};
        printMenu(attributes);

    }
}
