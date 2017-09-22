package com.codecool.jlamas;

// import com.codecool.jlamas.controllers.AdminMenuController;
// import com.codecool.jlamas.controllers.AppController;
import com.codecool.jlamas.models.account.Mentor;
import com.codecool.jlamas.database.MentorDAO;

public class Main {
    public static void main(String[] args) {

        MentorDAO dao = new MentorDAO();
        System.out.println(dao.requestAll().toString());
        // Admin admin = (Admin)AppController.login();
        // if (admin != null) {
        //     AdminMenuController user = new AdminMenuController(admin);
        //     user.menu();
        // }
    }
}
