package com.codecool.jlamas;

import org.flywaydb.core.Flyway;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

import com.codecool.jlamas.controllers.AdminMenuController;
import com.codecool.jlamas.controllers.TemplateController;
import com.codecool.jlamas.controllers.MentorTemplate;
import com.codecool.jlamas.controllers.TemplateController;

public class Main {
    public static void main(String[] args) throws Exception {

        // set database migration
        Flyway flyway = new Flyway();
        flyway.setDataSource("jdbc:sqlite:./target/database", "jlamas", null);
        flyway.migrate();

        // create local server
        HttpServer server = HttpServer.create(new InetSocketAddress(8100), 0);

        // set routes
        // server.createContext("/url", new ControllerName());
        server.createContext("/template", new TemplateController());
        server.createContext("/admin", new AdminMenuController());
        server.createContext("/mentor", new MentorTemplate());
        server.createContext("/static", new Static());
        server.setExecutor(null);

        server.start();

    }
}
