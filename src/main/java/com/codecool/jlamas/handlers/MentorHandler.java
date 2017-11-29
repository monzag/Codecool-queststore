package com.codecool.jlamas.handlers;

import com.codecool.jlamas.controllers.*;
import com.codecool.jlamas.database.SessionDAO;
import com.codecool.jlamas.database.UserDAO;
import com.codecool.jlamas.handlers.Response;
import com.codecool.jlamas.models.account.Mentor;
import com.codecool.jlamas.models.quest.Quest;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.HttpCookie;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

public class MentorHandler extends AbstractHandler implements HttpHandler{

    private StudentController studentController = new StudentController();
    private QuestController questController = new QuestController();
    private ArtifactController artifactController = new ArtifactController();
    private ArrayList<Quest> questsList;
    private Map<String, Callable> getCommands = new HashMap<>();
    private Map<String, Callable> postCommands = new HashMap<>();
    private Mentor mentor;
    private SessionDAO<Mentor> session = new SessionDAO();
    private CookieController cookieController = new CookieController();
    private Response responseCode = new Response();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        String method = httpExchange.getRequestMethod();
        HttpCookie cookie = cookieController.getCookie(httpExchange);

        if (cookie != null) {
            this.mentor = session.getUserByCookie(httpExchange);
            String userType = new UserDAO().getType(mentor.getLogin().getValue());

            if (mentor != null && userType.equals("mentor")) {

                if (method.equals("GET")) {
                    response = findCommand(httpExchange, getCommands);
                }

                if (method.equals("POST")) {
                    response = findCommand(httpExchange, postCommands);
                }

                responseCode.sendOKResponse(response, httpExchange);

            } else {
                session.removeCookieFromDb(cookie);
                responseCode.sendRedirectResponse(httpExchange, "/");
            }

        } else {
            responseCode.sendRedirectResponse(httpExchange, "/");
        }
    }

    private String displayProfile() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentor/mentorProfile.twig");
        JtwigModel model = JtwigModel.newModel();

        // profile pic found by login
        model.with("login", "student");
        model.with("mentor", mentor);

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
        Map <String, String> inputs = this.parseUserInputsFromHttp(httpExchange);

        String name = inputs.get("name").toString();
        String surname = inputs.get("surname").toString();
        String email = inputs.get("email").toString();
        String groupName = inputs.get("group").toString();
        studentController.addStudent(name, surname, email, groupName);

        return displayGroups("Student has been added");

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
        Map <String, String> inputs = this.parseUserInputsFromHttp(httpExchange);

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
        Map <String, String> inputs = this.parseUserInputsFromHttp(httpExchange);

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
        Map <String, String> inputs = this.parseUserInputsFromHttp(httpExchange);

        String name = inputs.get("questName").toString();
        String description = inputs.get("description").toString();
        Integer reward = Integer.valueOf(inputs.get("reward").toString());
        String oldName = parseUrl(httpExchange, 4);

        Quest quest = new Quest(name, description, reward);

        questController.editQuest(oldName, quest);

        return displayQuests();
    }

    protected void addGetCommands(HttpExchange httpExchange) {
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
        getCommands.put("/mentor/artifact/remove/.+", () -> { return removeArtifact(httpExchange);} );
        getCommands.put("/mentor/artifact/edit/.+", () -> { return displayEditArtifactFormula(httpExchange);} );
    }

    protected void addPostCommands(HttpExchange httpExchange) {
        postCommands.put("/mentor/quest/add", () -> { return addQuest(httpExchange);}  );
        postCommands.put("/mentor/quest/edit/.+", () -> { return editQuest(httpExchange);}  );
        postCommands.put("/mentor/groups/addStudent", () -> { return addStudent(httpExchange);}  );
        postCommands.put("/mentor/groups/edit/.+", () -> { return editStudent(httpExchange);}  );
        postCommands.put("/mentor/artifact/add", () -> { return addArtifact(httpExchange);} );
        postCommands.put("/mentor/artifact/edit/.+", () -> { return editArtifact(httpExchange);} );
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
        Map <String, String> inputs = this.parseUserInputsFromHttp(httpExchange);

        String name = inputs.get("artifactName").toString();
        String description = inputs.get("description").toString();
        Integer price = Integer.valueOf(inputs.get("price").toString());
        artifactController.createArtifact(name, description, price);


        return displayArtifact("Artifact has been added");

    }

    public String removeArtifact(HttpExchange httpExchange) {
        String name = parseUrl(httpExchange, 4);
        artifactController.removeArtifact(name);

        return displayArtifact("Artifact has been removed");
    }

    public String displayEditArtifactFormula(HttpExchange httpExchange) {
        String artifactName = parseUrl(httpExchange, 4);
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentor/editArtifact.twig");
        JtwigModel model = JtwigModel.newModel();

        model.with("artifact", artifactController.chooseArtifact(artifactName));

        return template.render(model);
    }

    public String editArtifact(HttpExchange httpExchange) throws IOException {
        Map <String, String> inputs = this.parseUserInputsFromHttp(httpExchange);

        String name = inputs.get("artifactName").toString();
        String description = inputs.get("description").toString();
        Integer price = Integer.valueOf(inputs.get("price").toString());
        String oldName = parseUrl(httpExchange, 4);

        artifactController.editArtifact(oldName, name, description, price);

        return displayArtifact("Artifact has been edited");
    }

}
