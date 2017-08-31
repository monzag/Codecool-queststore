package views;

public class AdminView extends CodecoolerView {

    public static void viewOptions() {
        String[] options = {"1. View all mentors", "2.Create mentor"};

        for (String option : options) {
            System.out.println(option);
        }
    }
}
