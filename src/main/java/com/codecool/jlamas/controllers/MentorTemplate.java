package com.codecool.jlamas.controllers;

import com.codecool.jlamas.database.StudentDAO;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class MentorTemplate implements HttpHandler {

    private StudentController studentController = new StudentController();

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

            if (httpExchange.getRequestURI().getPath().equals("/mentor/groups/addStudent")) {
                response = this.displayAddStudentFormula();
            }
        }

        if (method.equals("POST")) {
            if (httpExchange.getRequestURI().getPath().equals("/mentor/groups/addStudent")) {
                response = this.addStudent(httpExchange);
            }
        }


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

    private String displayAddStudentFormula() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/addStudent.twig");
        JtwigModel model = JtwigModel.newModel();

        // profile pic found by login
        model.with("login", "student");

        String response = template.render(model);

        return response;
    }

    private String addStudent(HttpExchange httpExchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String formData = br.readLine();

        System.out.println(formData);
        Map inputs = parseFormData(formData);
        String name = inputs.get("name").toString();
        String surname = inputs.get("surname").toString();
        String email = inputs.get("email").toString();
        String groupName = inputs.get("group").toString();
        studentController.addStudent(name, surname, email, groupName);

        return displayGroups();

    }

    private static Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }


}
