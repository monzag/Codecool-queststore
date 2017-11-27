package com.codecool.jlamas.controllers;

import com.codecool.jlamas.exceptions.InvalidUserDataException;
import com.codecool.jlamas.models.accountdata.City;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;


public class AdminMenuController implements HttpHandler {

    private Map<String, Callable> getCommands = new HashMap<String, Callable>();
    private Map<String, Callable> postCommands = new HashMap<String, Callable>();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        String method = httpExchange.getRequestMethod();

        if (method.equals("GET")) {
            response = this.findCommand(httpExchange, getCommands);
        }

        if (method.equals("POST")) {
            response = this.findCommand(httpExchange, postCommands);
        }

        final byte[] finalResponseBytes = response.getBytes("UTF-8");
        httpExchange.sendResponseHeaders(200, finalResponseBytes.length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(finalResponseBytes);
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

    private String displayCities() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/admin/admin_cities_list.twig");
        JtwigModel model = JtwigModel.newModel();

        // instead of value 'student' login from cookie
        model.with("login", "student");
        model.with("cities", new CityController().getAll());

        return template.render(model);
    }

    private String displayMentorForm(HttpExchange httpExchange, Map<String, String> inputs) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/admin/admin_mentor_form.twig");
        JtwigModel model = JtwigModel.newModel();

        // instead of value 'student' login from cookie
        model.with("login", "student");
        if (httpExchange != null) {
            model.with("mentor", new MentorController().getMentor(this.parseLogin(httpExchange)));
        }
        if (inputs != null) {
            model.with("name", inputs.get("name"));
            model.with("surname", inputs.get("surname"));
        }
        model.with("groups", new GroupController().getAllGroups());

        return template.render(model);
    }

    private String displayExistingGroupForm(HttpExchange httpExchange) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/admin/admin_groups_edit.twig");
        JtwigModel model = JtwigModel.newModel();

        GroupController ctrl = new GroupController();

        // instead of value 'student' login from cookie
        model.with("login", "student");
        model.with("group", ctrl.getGroup(this.parseGroupID(httpExchange)));

        return template.render(model);
    }

    private String displayNewGroupForm() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/admin/admin_groups_add.twig");
        JtwigModel model = JtwigModel.newModel();

        CityController ctrl = new CityController();
        // instead of value 'student' login from cookie
        model.with("login", "student");
        model.with("cities", ctrl.getAll());
        model.with("years", ctrl.getYears());

        return template.render(model);
    }

    private String addMentor(HttpExchange httpExchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String formData = br.readLine();

        Map inputs = parseFormData(formData);

        MentorController ctrl = new MentorController();
        try {
            ctrl.createMentorFromMap(inputs);
        } catch (InvalidUserDataException e) {
            return this.displayMentorForm(null, inputs);
        }

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
        MentorController ctrl = new MentorController();
        try {
            ctrl.editMentorFromMap(inputs, this.parseLogin(httpExchange));
        } catch (InvalidUserDataException e) {
            return this.displayMentorForm(null, inputs);
        }

        return this.displayMentors();
    }

    private String editGroup(HttpExchange httpExchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String formData = br.readLine();

        Map inputs = parseFormData(formData);
        // TODO data validation!
        GroupController ctrl = new GroupController();
        ctrl.editGroupFromMap(inputs, this.parseGroupID(httpExchange));

        return this.displayGroups();
    }

    private String removeMentor(HttpExchange httpExchange) throws IOException {
        MentorController mentorController = new MentorController();
        mentorController.removeMentor(this.parseLogin(httpExchange));

        return this.displayMentors();
    }

    private String removeGroup(HttpExchange httpExchange) throws IOException {
        GroupController groupController = new GroupController();
        groupController.removeGroup(this.parseGroupID(httpExchange));

        return this.displayGroups();
    }

    private String removeCity(HttpExchange httpExchange) throws IOException {
        CityController cityController = new CityController();
        cityController.removeCity(this.parseGroupID(httpExchange));

        return this.displayGroups();
    }

    private String parseLogin(HttpExchange httpExchange) {
        return httpExchange.getRequestURI().getPath().split("/")[5];
    }

    private Integer parseGroupID(HttpExchange httpExchange) {
        return Integer.valueOf(this.parseLogin(httpExchange));
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

    private void addGetCommands (HttpExchange httpExchange) {
        getCommands.put("/admin", () -> {return this.displayProfile();} );
        getCommands.put("/admin/mentors/list", () -> {return this.displayMentors();} );
        getCommands.put("/admin/mentors/list/edit/.+", () -> {return this.displayMentorForm(httpExchange, null);} );
        getCommands.put("/admin/mentors/list/remove/.+", () -> { return this.removeMentor(httpExchange);} );
        getCommands.put("/admin/mentors/add", () -> {return this.displayMentorForm(null, null);} );
        getCommands.put("/admin/groups/list", () -> {return this.displayGroups();} );
        getCommands.put("/admin/groups/list/edit/.+", () -> {return this.displayExistingGroupForm(httpExchange);} );
        getCommands.put("/admin/groups/list/remove/.+", () -> {return this.removeGroup(httpExchange);} );
        getCommands.put("/admin/groups/add", () -> {return this.displayNewGroupForm();} );
        getCommands.put("/admin/cities/list", () -> {return this.displayCities();} );
        getCommands.put("/admin/cities/list/remove/.+", () -> {return this.removeCity(httpExchange);} );
    }

    private void addPostCommands (HttpExchange httpExchange) {
        postCommands.put("/admin/mentors/add", () -> { return this.addMentor(httpExchange);} );
        postCommands.put("/admin/groups/add", () -> { return this.addGroup(httpExchange);} );
        postCommands.put("/admin/mentors/list/edit/.+", () -> { return this.editMentor(httpExchange);} );
        postCommands.put("/admin/groups/list/edit/.+", () -> { return this.editGroup(httpExchange);} );

    }

    private String findCommand(HttpExchange httpExchange, Map<String, Callable> mapName) {
        String response = null;
        String path = httpExchange.getRequestURI().getPath();
        Set<String> keys = mapName.keySet();

        this.addGetCommands(httpExchange);
        this.addPostCommands(httpExchange);

        for (String key : keys) {
            if (path.matches(key)) {
                try {
                    response = (String) mapName.get(key).call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return response;
    }

}
