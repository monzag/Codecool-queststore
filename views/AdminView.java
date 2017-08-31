package views;

import database.MentorDAO;
import java.util.ArrayList;

public class AdminView extends CodecoolerView {

    public static void viewOptions() {
        String[] options = {"1. View all mentors", "2. Create mentor", "0. Exit"};

        for (String option : options) {
            System.out.println(option);
        }
    }

    public static void showAllMentors() {
        MentorDAO mentors = new MentorDAO();
        ArrayList<Mentor> mentorsList = mentors.loadAll();

        if (!(mentorsList.size() == 0)) {

            for (Mentor mentor : mentorsList) {
                System.out.println(mentor);
            }
        } else {
            System.out.println("There is no data to show");
        }
    }
}
