package com.codecool.jlamas.views;

import com.codecool.jlamas.database.MentorDAO;
import java.util.ArrayList;
import com.codecool.jlamas.models.account.Mentor;

public class AdminView extends CodecoolerView {

    public void displayAdminMenu() {
        String[] options = {"Display mentors", 
                            "Add mentor",
                            "Edit mentor",
                            "Add class",
                            "Add level",
                            "Edit quest"};

        printMenu(options);       
    }

    public static void showAllMentors() {
        MentorDAO mentors = new MentorDAO();
        ArrayList<Mentor> mentorList = mentors.loadAll();
        for (Mentor mentor : mentorList) {
            System.out.println(mentor);
        }
    }
}
