package com.codecool.jlamas;

import com.codecool.jlamas.controllers.AdminController;
import com.codecool.jlamas.controllers.AppController;
import com.codecool.jlamas.models.account.Admin;

public class Main {
    public static void main(String[] args) {
        Admin admin = (Admin)AppController.login();
        if (admin != null) {
            AdminController user = new AdminController(admin);
            user.menu();    
        }
    }
}
