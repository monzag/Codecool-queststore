package com.codecool.jlamas;

import com.codecool.jlamas.controllers.*;
import com.codecool.jlamas.handlers.AdminHandler;
import com.codecool.jlamas.handlers.MentorHandler;
import org.flywaydb.core.Flyway;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws Exception {
        
        Flyway flyway = new Flyway();
        flyway.setDataSource("jdbc:sqlite:./target/database", "jlamas", null);
        flyway.migrate();

        HttpServer server = HttpServer.create(new InetSocketAddress(8100), 0);

        server.createContext("/", new AppController());
        server.createContext("/mentor", new MentorHandler());
        server.createContext("/student", new StudentMenuController());
        server.createContext("/admin", new AdminHandler());
        server.createContext("/static", new Static());
        server.setExecutor(null);

        server.start();

    }
}
