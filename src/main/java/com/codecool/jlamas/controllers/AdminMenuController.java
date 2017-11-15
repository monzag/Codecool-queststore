package com.codecool.jlamas.controllers;

// import com.codecool.jlamas.models.account.Admin;
// import com.codecool.jlamas.models.account.Mentor;
// import com.codecool.jlamas.views.AdminView;

// import java.util.ArrayList;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;

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
                response = "";
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

    private String displayGroups() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/admin_groups_list.twig");
        JtwigModel model = JtwigModel.newModel();

        // instead of value 'student' login from cookie
        model.with("login", "student");
        model.with("groups", new GroupController().getAllGroups());

        return template.render(model);
    }
//
//    public void createMentor() {
//        mentorController.addMentor();
//    }
//
//    public void editMentor() {
//        mentorController.editMentor();
//    }
//
//    public void addGroup() {
//        GroupController groupController = new GroupController();
//        groupController.createGroup();
//    }
//
//    public void addLevel() {
//        ;
//    }
//
//    public void editQuest() {
//        QuestController questController = new QuestController();
//        questController.editQuest();
//    }
}
