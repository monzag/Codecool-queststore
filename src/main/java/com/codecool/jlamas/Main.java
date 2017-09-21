package com.codecool.jlamas;

// import com.codecool.jlamas.controllers.AdminMenuController;
// import com.codecool.jlamas.controllers.AppController;
import com.codecool.jlamas.controllers.AppController;
import com.codecool.jlamas.models.account.Mentor;
import com.codecool.jlamas.database.MentorDAO;

public class Main {
    public static void main(String[] args) {

        AppController ctrl = new AppController();
        ctrl.login();
    }
}
