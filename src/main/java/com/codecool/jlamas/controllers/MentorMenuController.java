package com.codecool.jlamas.controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class MentorMenuController implements HttpHandler{

    private StudentController studentController = new StudentController();
    private QuestController questController = new QuestController();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String response = "";
        String method = httpExchange.getRequestMethod();

        if (method.equals("GET")) {
            if (httpExchange.getRequestURI().getPath().equals("/mentor")) {
                response = this.displayProfile();
            }

            if (httpExchange.getRequestURI().getPath().equals("/mentor/groups")) {
                response = this.displayGroups("");
            }

            if (httpExchange.getRequestURI().getPath().equals("/mentor/groups/addStudent")) {
                response = this.displayAddStudentFormula();
            }

            if (httpExchange.getRequestURI().getPath().matches("/mentor/groups/remove/.+")) {
                response = this.removeStudent(httpExchange);
            }

            if (httpExchange.getRequestURI().getPath().matches("/mentor/groups/edit/.+")) {
                response = this.displayEditFormula(httpExchange);
            }

            if (httpExchange.getRequestURI().getPath().matches("/mentor/groups/quest/[A-Za-z0-9.]+")) {
                response = this.displayQuestsToMark("", httpExchange);
            }

            if (httpExchange.getRequestURI().getPath().matches("/mentor/groups/quest/[A-Za-z0-9.]+/mark/.+")) {
                response = this.markQuest(httpExchange);
            }
        }

        if (method.equals("POST")) {
            if (httpExchange.getRequestURI().getPath().equals("/mentor/groups/addStudent")) {
                response = this.addStudent(httpExchange);
            }

            if (httpExchange.getRequestURI().getPath().matches("/mentor/groups/edit/.+")) {
                response = this.editStudent(httpExchange);
            }
        }


        final byte[] finalResponseBytes = response.getBytes("UTF-8");
        httpExchange.sendResponseHeaders(200, finalResponseBytes.length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(finalResponseBytes);
        os.close();

    }

    private String displayProfile() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentor/mentorProfile.twig");
        JtwigModel model = JtwigModel.newModel();

        // profile pic found by login
        model.with("login", "student");

        String response = template.render(model);

        return response;
    }

    private String displayGroups(String message) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentor/showGroups.twig");
        JtwigModel model = JtwigModel.newModel();

        // profile pic found by login
        model.with("login", "student");
        model.with("message", message);
        model.with("students", studentController.getStudents());

        String response = template.render(model);

        return response;
    }

    private String displayAddStudentFormula() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentor/addStudent.twig");
        JtwigModel model = JtwigModel.newModel();

        // profile pic found by login
        model.with("login", "student");
        model.with("groups", new GroupController().getAllGroups());

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

        return displayGroups("Student has been added");

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

    private String removeStudent(HttpExchange httpExchange) {
        String login = parseUrl(httpExchange, 4);
        studentController.removeStudent(login);

        return displayGroups("Student has been removed");
    }

    private String parseUrl(HttpExchange httpExchange, int index) {
        return httpExchange.getRequestURI().getPath().split("/")[index];
    }

    private String displayEditFormula(HttpExchange httpExchange) {
        String login = parseUrl(httpExchange, 4);

        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentor/editStudent.twig");
        JtwigModel model = JtwigModel.newModel();

        // profile pic found by login
        model.with("login", "student");
        model.with("student", studentController.chooseStudent(login));

        return template.render(model);
    }

    private String editStudent(HttpExchange httpExchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String formData = br.readLine();

        System.out.println(formData);
        Map inputs = parseFormData(formData);
        String name = inputs.get("name").toString();
        String surname = inputs.get("surname").toString();
        String email = inputs.get("email").toString();
        String groupName = inputs.get("group").toString();
        String login = parseUrl(httpExchange, 4);
        studentController.editStudent(login, name, surname, email, groupName);

        return displayGroups("Student has been edited");
    }

    private String displayQuestsToMark(String message, HttpExchange httpExchange) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentor/markQuest.twig");
        JtwigModel model = JtwigModel.newModel();
        String login = parseUrl(httpExchange, 4);
        // profile pic found by login
        model.with("login", "student");
        model.with("message", message);
        model.with("studentLogin", login);
        model.with("questsList", questController.showAllQuests());

        String response = template.render(model);

        return response;
    }

    private String markQuest(HttpExchange httpExchange) {
        String login = parseUrl(httpExchange, 4);

        String questName = parseUrl(httpExchange, 6);
        questController.markQuestAsDone(studentController.chooseStudent(login), questController.chooseQuest(questName));

        return displayQuestsToMark("Quest has been marked", httpExchange);
    }

}
