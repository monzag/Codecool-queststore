package com.codecool.jlamas.controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;


public class AdminMenuController implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        String method = httpExchange.getRequestMethod();

        if (method.equals("GET")) {

            if (httpExchange.getRequestURI().getPath().equals("/admin")) {
                response = this.displayProfile();
            }
            else if (httpExchange.getRequestURI().getPath().equals("/admin/mentors/list")) {
                response = this.displayMentors();
            }
            else if (httpExchange.getRequestURI().getPath().matches("/admin/mentors/list/edit/.+")) {
                // TODO wrong url path done b-hand currently it does nothing beside back to list
                response = this.displayExistingMentorForm(httpExchange);
            }
            else if (httpExchange.getRequestURI().getPath().matches("/admin/mentors/list/remove/.+")) {
                // TODO wrong url path done b-hand currently it does nothing beside back to list
                response = this.removeMentor(httpExchange);
            }
            else if (httpExchange.getRequestURI().getPath().equals("/admin/mentors/add")) {
                response = this.displayNewMentorForm();
            }
            else if (httpExchange.getRequestURI().getPath().equals("/admin/groups/list")) {
                response = this.displayGroups();
            }
            else if (httpExchange.getRequestURI().getPath().matches("/admin/groups/list/edit/.+")) {
                response = "";
            }
            else if (httpExchange.getRequestURI().getPath().matches("/admin/groups/list/remove/.+")) {
                // TODO wrong url path done b-hand currently it does nothing beside back to list
                response = this.removeGroup(httpExchange);
            }
            else if (httpExchange.getRequestURI().getPath().equals("/admin/groups/add")) {
                response = this.displayNewGroupForm();
            }
        }

        if (method.equals("POST")) {
            if (httpExchange.getRequestURI().getPath().equals("/admin/mentors/add")) {
                response = this.addMentor(httpExchange);
            }
            if (httpExchange.getRequestURI().getPath().equals("/admin/groups/add")) {
                response = this.addGroup(httpExchange);
            }
            if (httpExchange.getRequestURI().getPath().matches("/admin/mentors/list/edit/.+")) {
                response = this.editMentor(httpExchange);
            }
        }

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private String displayProfile() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/admin/admin.twig");
        JtwigModel model = JtwigModel.newModel();

        // instead of value 'student' login from cookie
        model.with("login", "student");

        return template.render(model);
    }

    private String displayMentors() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/admin/admin_mentor_list.twig");
        JtwigModel model = JtwigModel.newModel();

        // instead of value 'student' login from cookie
        model.with("login", "student");
        model.with("mentors", new MentorController().getAllMentors());

        return template.render(model);
    }

    private String displayGroups() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/admin/admin_groups_list.twig");
        JtwigModel model = JtwigModel.newModel();

        // instead of value 'student' login from cookie
        model.with("login", "student");
        model.with("groups", new GroupController().getAllGroups());

        return template.render(model);
    }

    private String displayNewMentorForm() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/admin/admin_mentor_add.twig");
        JtwigModel model = JtwigModel.newModel();

        // instead of value 'student' login from cookie
        model.with("login", "student");
        model.with("groups", new GroupController().getAllGroups());

        return template.render(model);
    }

    private String displayExistingMentorForm(HttpExchange httpExchange) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/admin/admin_mentor_edit.twig");
        JtwigModel model = JtwigModel.newModel();

        // instead of value 'student' login from cookie
        model.with("login", "student");
        model.with("mentor", new MentorController().getMentor(this.parseLogin(httpExchange)));
        model.with("groups", new GroupController().getAllGroups());

        return template.render(model);
    }

    private String displayNewGroupForm() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/admin/admin_groups_add.twig");
        JtwigModel model = JtwigModel.newModel();

        // instead of value 'student' login from cookie
        model.with("login", "student");

        return template.render(model);
    }

    private String addMentor(HttpExchange httpExchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String formData = br.readLine();

        Map inputs = parseFormData(formData);
        // TODO data validation!
        MentorController ctrl = new MentorController();
        ctrl.createMentorFromMap(inputs);

        return this.displayMentors();
    }

    private String addGroup(HttpExchange httpExchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String formData = br.readLine();

        Map inputs = parseFormData(formData);
        // TODO data validation!
        GroupController ctrl = new GroupController();
        ctrl.createGroupFromMap(inputs);

        return this.displayGroups();
    }

    private String editMentor(HttpExchange httpExchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String formData = br.readLine();

        Map inputs = parseFormData(formData);
        // TODO data validation!
        MentorController ctrl = new MentorController();
        ctrl.editMentorFromMap(inputs, this.parseLogin(httpExchange));

        return this.displayMentors();
    }

    private String removeMentor(HttpExchange httpExchange) throws IOException {
        MentorController mentorController = new MentorController();
        mentorController.removeMentor(this.parseLogin(httpExchange));

        return this.displayMentors();
    }

    private String removeGroup(HttpExchange httpExchange) throws IOException {
        GroupController groupController = new GroupController();
        groupController.removeGroup(this.parseLogin(httpExchange));

        return this.displayGroups();
    }

    private String parseLogin(HttpExchange httpExchange) {
        return httpExchange.getRequestURI().getPath().split("/")[5];
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
