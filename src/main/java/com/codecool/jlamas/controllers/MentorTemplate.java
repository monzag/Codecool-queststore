package com.codecool.jlamas.controllers;

import com.codecool.jlamas.database.StudentDAO;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;

public class MentorTemplate implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String response = "";
        String method = httpExchange.getRequestMethod();

        if (method.equals("GET")) {
            if (httpExchange.getRequestURI().getPath().equals("/mentor")) {
                response = this.displayProfile();
            }

            if (httpExchange.getRequestURI().getPath().equals("/mentor/groups")) {
                response = this.displayGroups();
            }
        }

//        if (method.equals("POST")) {
//            if (httpExchange.getRequestURI().equals("/mentor/groups")) {
//                response = this.displayGroups();
//            }
//        }


        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }

    private String displayProfile() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentorProfile.twig");
        JtwigModel model = JtwigModel.newModel();

        // profile pic found by login
        model.with("login", "student");

        String response = template.render(model);

        return response;
    }

    private String displayGroups() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/showGroups.twig");
        JtwigModel model = JtwigModel.newModel();

        // profile pic found by login
        model.with("login", "student");

        StudentDAO studentDAO = new StudentDAO();
        model.with("students", studentDAO.requestAll());

        String response = template.render(model);

        return response;
    }
}

