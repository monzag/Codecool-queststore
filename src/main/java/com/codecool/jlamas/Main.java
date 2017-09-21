package com.codecool.jlamas;

import com.codecool.jlamas.controllers.AdminMenuController;
import com.codecool.jlamas.controllers.AppController;
import com.codecool.jlamas.models.account.Admin;

public class Main {
    public static void main(String[] args) {
        Admin admin = (Admin)AppController.login();
        if (admin != null) {
            AdminMenuController user = new AdminMenuController(admin);
            user.menu();    
        }
    }
}
