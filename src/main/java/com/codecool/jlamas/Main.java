package com.codecool.jlamas;

import org.flywaydb.core.Flyway;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws Exception {

        // set database migration
        Flyway flyway = new Flyway();
        flyway.setDataSource("jdbc:sqlite:./target/database", "jlamas", null);
        flyway.migrate();

        // create local server
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // server.createContext("/url", new ControllerName());
        // server.createContext("/static", new Static());
        server.setExecutor(null);

        server.start();

    }
}
