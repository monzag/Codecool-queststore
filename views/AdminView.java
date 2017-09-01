package views;

import database.MentorDAO;
import java.util.ArrayList;
import models.account.Mentor;

public class AdminView extends CodecoolerView {

    public static void viewOptions() {
        String[] options = {"1. View all mentors", "2. Create mentor", "0. Exit"};

        for (String option : options) {
            System.out.println(option);
        }
    }

    public static void showAllMentors() {
        MentorDAO mentors = new MentorDAO();
        ArrayList<Mentor> mentorList = mentors.loadAll();
        for (Mentor mentor : mentorList) {
            System.out.println(mentor);
        }
    }
}
