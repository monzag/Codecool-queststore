package com.codecool.jlamas.controllers;

import com.codecool.jlamas.models.quest.Quest;
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

public class MentorMenuController implements HttpHandler{

    private StudentController studentController = new StudentController();
    private QuestController questController = new QuestController();
    private ArtifactController artifactController = new ArtifactController();
    private ArrayList<Quest> questsList;
    private Map<String, Callable> getCommands = new HashMap<>();
    private Map<String, Callable> postCommands = new HashMap<>();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        String method = httpExchange.getRequestMethod();

        if (method.equals("GET")) {
            response = findCommand(httpExchange, getCommands);
        }

        if (method.equals("POST")) {
            response = findCommand(httpExchange, postCommands);
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

    private String displayAddQuest() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentor/add_quest.twig");
        JtwigModel model = JtwigModel.newModel();

        return template.render(model);
    }

    private String displayQuests() {
        questsList = questController.showAllQuests();
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentor/mentor_quests.twig");
        JtwigModel model = JtwigModel.newModel();

        return template.render(model.with("questsList", questsList));
    }

    private String addQuest(HttpExchange httpExchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String formData = br.readLine();

        Map inputs = parseFormData(formData);
        System.out.println("2");

        String questName = (String) inputs.get("questName");
        String description = (String) inputs.get("description");
        Integer reward = Integer.valueOf(inputs.get("reward").toString());

        Quest quest = new Quest(questName, description, reward);

        questController.createQuest(quest);

        return displayQuests();

    }


    private String removeQuest(HttpExchange httpExchange) {
        String questName = parseUrl(httpExchange, 4);
        Quest quest = questController.chooseQuest(questName);

        questController.deleteQuest(quest);

        return displayQuests();
    }

    private String displayEditQuestForm(HttpExchange httpExchange) {
        String questName = parseUrl(httpExchange, 4);
        Quest quest = questController.chooseQuest(questName);
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentor/edit_quest.twig");
        JtwigModel model = JtwigModel.newModel();

        model.with("quest", quest);

        return template.render(model);
    }

    private String editQuest(HttpExchange httpExchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String formData = br.readLine();

        Map inputs = parseFormData(formData);

        String name = inputs.get("questName").toString();
        String description = inputs.get("description").toString();
        Integer reward = Integer.valueOf(inputs.get("reward").toString());
        String oldName = parseUrl(httpExchange, 4);

        Quest quest = new Quest(name, description, reward);

        questController.editQuest(oldName, quest);

        return displayQuests();
    }

    private void addGetCommands(HttpExchange httpExchange) {
        getCommands.put("/mentor/quest/show", () -> { return displayQuests();} );
        getCommands.put("/mentor/quest/add", () -> {return displayAddQuest();} );
        getCommands.put("/mentor/quest/remove/.+", () -> { return removeQuest(httpExchange);} );
        getCommands.put("/mentor/quest/edit/.+", () -> {return displayEditQuestForm(httpExchange);} );
        getCommands.put("/mentor", () -> {return displayProfile();} );
        getCommands.put("/mentor/groups", () -> {return displayGroups("");} );
        getCommands.put("/mentor/groups/addStudent", () -> {return displayAddStudentFormula();} );
        getCommands.put("/mentor/groups/remove/.+", () -> {return removeStudent(httpExchange);} );
        getCommands.put("/mentor/groups/edit/.+", () -> {return displayEditFormula(httpExchange);} );
        getCommands.put("/mentor/groups/quest/[A-Za-z0-9.]+", () -> {return displayQuestsToMark("", httpExchange);} );
        getCommands.put("/mentor/groups/quest/[A-Za-z0-9.]+/mark/.+", () -> {return markQuest(httpExchange);} );
        getCommands.put("/mentor/artifact/show", () -> { return displayArtifact("");} );
        getCommands.put("/mentor/artifact/add", () -> { return displayAddArtifact();} );
    }

    private void addPostCommands(HttpExchange httpExchange) {
        postCommands.put("/mentor/quest/add", () -> { return addQuest(httpExchange);}  );
        postCommands.put("/mentor/quest/edit/.+", () -> { return editQuest(httpExchange);}  );
        postCommands.put("/mentor/groups/addStudent", () -> { return addStudent(httpExchange);}  );
        postCommands.put("/mentor/groups/edit/.+", () -> { return editStudent(httpExchange);}  );
        postCommands.put("/mentor/artifact/add", () -> { return addArtifact(httpExchange);} );
    }

    private String findCommand(HttpExchange httpExchange, Map<String, Callable> mapName) {
        String response = null;
        String path = httpExchange.getRequestURI().getPath();
        Set<String> keys = mapName.keySet();

        addGetCommands(httpExchange);
        addPostCommands(httpExchange);

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

    public String displayArtifact(String message) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentor/showArtifact.twig");
        JtwigModel model = JtwigModel.newModel();

        // profile pic found by login
        model.with("login", "student");
        model.with("message", message);
        model.with("artifacts", artifactController.displayArtifacts());

        String response = template.render(model);

        return response;
    }

    public String displayAddArtifact() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentor/addArtifact.twig");
        JtwigModel model = JtwigModel.newModel();

        String response = template.render(model);

        return response;
    }

    public String addArtifact(HttpExchange httpExchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String formData = br.readLine();

        System.out.println(formData);
        Map inputs = parseFormData(formData);
        String name = inputs.get("artifactName").toString();
        String description = inputs.get("description").toString();
        Integer price = Integer.valueOf(inputs.get("price").toString());
        artifactController.createArtifact(name, description, price);


        return displayArtifact("Artifact has been added");

    }
}
