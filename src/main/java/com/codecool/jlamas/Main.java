package com.codecool.jlamas;

import org.flywaydb.core.Flyway;
import com.codecool.jlamas.controllers.AppController;

public class Main {
    public static void main(String[] args) {

        Flyway flyway = new Flyway();
        flyway.setDataSource("jdbc:sqlite:./target/database", "jlamas", null);
        flyway.migrate();

        AppController ctrl = new AppController();
        ctrl.login();
    }
}
