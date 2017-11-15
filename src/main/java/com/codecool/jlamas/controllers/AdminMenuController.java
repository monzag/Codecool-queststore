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
            else if (httpExchange.getRequestURI().getPath().equals("/admin/mentors/list/edit")) {
                response = "";
            }
            else if (httpExchange.getRequestURI().getPath().equals("/admin/groups/list/remove")) {
                response = "";
            }
            else if (httpExchange.getRequestURI().getPath().equals("/admin/mentors/add")) {
                response = this.displayNewMentorForm();
            }
            else if (httpExchange.getRequestURI().getPath().equals("/admin/groups/list")) {
                response = this.displayGroups();
            }
            else if (httpExchange.getRequestURI().getPath().equals("/admin/groups/list/edit")) {
                response = "";
            }
            else if (httpExchange.getRequestURI().getPath().equals("/admin/groups/list/remove")) {
                response = "";
            }
            else if (httpExchange.getRequestURI().getPath().equals("/admin/groups/add")) {
                response = "";
            }
        }

        if (method.equals("POST")) {
            if (httpExchange.getRequestURI().getPath().equals("/admin/mentors/add")) {
                response = this.addMentor(httpExchange);
            }
        }

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private String displayProfile() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/admin.twig");
        JtwigModel model = JtwigModel.newModel();

        // instead of value 'student' login from cookie
        model.with("login", "student");

        return template.render(model);
    }

    private String displayMentors() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/admin_mentor_list.twig");
        JtwigModel model = JtwigModel.newModel();

        // instead of value 'student' login from cookie
        model.with("login", "student");
        model.with("mentors", new MentorController().getAllMentors());

        return template.render(model);
    }

    private String displayNewMentorForm() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/admin_mentor_add.twig");
        JtwigModel model = JtwigModel.newModel();

        // instead of value 'student' login from cookie
        model.with("login", "student");

        return template.render(model);
    }

    private String displayGroups() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/admin_groups_list.twig");
        JtwigModel model = JtwigModel.newModel();

        // instead of value 'student' login from cookie
        model.with("login", "student");
        model.with("groups", new GroupController().getAllGroups());

        return template.render(model);
    }

    private String addMentor(HttpExchange httpExchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String formData = br.readLine();

        Map inputs = parseFormData(formData);
        System.out.println(inputs.get("name"));
        System.out.println(inputs.get("surname"));
        System.out.println(inputs.get("email"));
        System.out.println(inputs.get("class"));

        return this.displayMentors();
    }

    private static Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            // We have to decode the value because it's urlencoded. see: https://en.wikipedia.org/wiki/POST_(HTTP)#Use_for_submitting_web_forms
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }

}
