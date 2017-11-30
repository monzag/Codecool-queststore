package com.codecool.jlamas.handlers;

import com.codecool.jlamas.controllers.*;
import com.codecool.jlamas.database.SessionDAO;
import com.codecool.jlamas.database.UserDAO;
import com.codecool.jlamas.exceptions.InvalidUserDataException;
import com.codecool.jlamas.exceptions.NotMatchingPasswordException;
import com.codecool.jlamas.models.account.Mentor;
import com.codecool.jlamas.models.quest.Quest;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.HttpCookie;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class MentorHandler extends AbstractHandler implements HttpHandler {

    private static final Integer STUDENT_INDEX = 4;
    private static final Integer QUEST_INDEX = 6;

    private static final String STUDENT_FORM = "templates/mentor/addStudent.twig";

    private Map<String, Callable> getCommands = new HashMap<>();
    private Map<String, Callable> postCommands = new HashMap<>();

    private StudentController studentController = new StudentController();
    private QuestController questController = new QuestController();
    private ArtifactController artifactController = new ArtifactController();

    private Mentor mentor;
    private SessionDAO session = new SessionDAO();
    private CookieController cookieController = new CookieController();
    private Response responseCode = new Response();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        String method = httpExchange.getRequestMethod();
        HttpCookie cookie = cookieController.getCookie(httpExchange);

        if (cookie != null) {
            String userType = new UserDAO().getType(session.getUserByCookie(httpExchange).getLogin().getValue());
            if (userType.equals("mentor")) {
                this.mentor = (Mentor) session.getUserByCookie(httpExchange);
                if (mentor != null) {
                    if (method.equals("GET")) {
                        if (httpExchange.getRequestURI().getPath().toString().equals("/mentor/logout")) {
                            this.logout(httpExchange);

                        } else {
                            response = findCommand(httpExchange, getCommands);
                            responseCode.sendOKResponse(response, httpExchange);
                        }
                    }
                    if (method.equals("POST")) {
                        response = findCommand(httpExchange, postCommands);
                        responseCode.sendOKResponse(response, httpExchange);
                    }
                }
            } else {
                responseCode.sendRedirectResponse(httpExchange, "/");
            }
        }
        else {
            responseCode.sendRedirectResponse(httpExchange, "/");
        }
    }

    protected void addGetCommands(HttpExchange httpExchange) {
        getCommands.put("/mentor/quest/show", () -> { return displayQuests();} );
        getCommands.put("/mentor/quest/add", () -> {return displayAddQuest();} );
        getCommands.put("/mentor/quest/remove/.+", () -> { return removeQuest(httpExchange);} );
        getCommands.put("/mentor/quest/edit/.+", () -> {return displayEditQuestForm(httpExchange);} );
        getCommands.put("/mentor", () -> {return displayProfile();} );
        getCommands.put("/mentor/groups", () -> {return displayGroups("");} );
        getCommands.put("/mentor/groups/addStudent", () -> {return displayStudentForm(null, null);} );
        getCommands.put("/mentor/groups/remove/.+", () -> {return removeStudent(httpExchange);} );
        getCommands.put("/mentor/groups/edit/.+", () -> {return displayStudentForm(httpExchange, null);} );
        getCommands.put("/mentor/groups/quest/[A-Za-z0-9.]+", () -> {return displayQuestsToMark("", httpExchange);} );
        getCommands.put("/mentor/groups/quest/[A-Za-z0-9.]+/mark/.+", () -> {return markQuest(httpExchange);} );
        getCommands.put("/mentor/artifact/show", () -> { return displayArtifact("");} );
        getCommands.put("/mentor/artifact/add", () -> { return displayAddArtifact();} );
        getCommands.put("/mentor/artifact/remove/.+", () -> { return removeArtifact(httpExchange);} );
        getCommands.put("/mentor/artifact/edit/.+", () -> { return displayEditArtifactFormula(httpExchange);} );
        getCommands.put("/mentor/password/edit/.+", () -> {return this.displayEditPassword("");} );
    }

    protected void addPostCommands(HttpExchange httpExchange) {
        postCommands.put("/mentor/quest/add", () -> { return addQuest(httpExchange);}  );
        postCommands.put("/mentor/quest/edit/.+", () -> { return editQuest(httpExchange);}  );
        postCommands.put("/mentor/groups/addStudent", () -> { return addStudent(httpExchange);}  );
        postCommands.put("/mentor/groups/edit/.+", () -> { return editStudent(httpExchange);}  );
        postCommands.put("/mentor/artifact/add", () -> { return addArtifact(httpExchange);} );
        postCommands.put("/mentor/artifact/edit/.+", () -> { return editArtifact(httpExchange);} );
        postCommands.put("/mentor/password/edit/.+", () -> { return this.editPassword(httpExchange); });
    }

    protected String displayProfile() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentor/mentorProfile.twig");
        JtwigModel model = JtwigModel.newModel();

        // profile pic found by login
        model.with("login", "student");
        model.with("mentor", mentor);

        return template.render(model);
    }

    private String displayGroups(String message) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentor/showGroups.twig");
        JtwigModel model = JtwigModel.newModel();

        // profile pic found by login
        model.with("login", "student");
        model.with("message", message);
        model.with("students", studentController.getAll());

        return template.render(model);
    }

    private String displayStudentForm(HttpExchange httpExchange, Map<String, String> inputs) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(STUDENT_FORM);
        JtwigModel model = JtwigModel.newModel();

        model.with("login", "student");

        if (inputs == null && httpExchange != null) {
            model.with("student", studentController.get(this.parseStringFromURL(httpExchange, STUDENT_INDEX)));
        }
        else if (inputs != null) {
            model.with("name", inputs.get("name"));
            model.with("surname", inputs.get("surname"));
        }
        model.with("groups", new GroupController().getAll());

        return template.render(model);
    }

    public String displayArtifact(String message) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentor/showArtifact.twig");
        JtwigModel model = JtwigModel.newModel();

        // profile pic found by login
        model.with("login", "student");
        model.with("message", message);
        model.with("artifacts", artifactController.displayArtifacts());

        return template.render(model);
    }

    public String displayAddArtifact() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentor/addArtifact.twig");
        JtwigModel model = JtwigModel.newModel();

        return template.render(model);
    }

    public String displayEditArtifactFormula(HttpExchange httpExchange) {
        String artifactName = this.parseStringFromURL(httpExchange, STUDENT_INDEX);
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentor/editArtifact.twig");
        JtwigModel model = JtwigModel.newModel();

        model.with("artifact", artifactController.chooseArtifact(artifactName));

        return template.render(model);
    }

    private String displayQuests() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentor/mentor_quests.twig");
        JtwigModel model = JtwigModel.newModel();

        return template.render(model.with("questsList", questController.showAllQuests()));
    }

    private String displayQuestsToMark(String message, HttpExchange httpExchange) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentor/markQuest.twig");
        JtwigModel model = JtwigModel.newModel();
        String login = this.parseStringFromURL(httpExchange, STUDENT_INDEX);
        // profile pic found by login
        model.with("login", "student");
        model.with("message", message);
        model.with("studentLogin", login);
        model.with("questsList", questController.showAllQuests());

        return template.render(model);
    }

    private String displayAddQuest() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentor/add_quest.twig");
        JtwigModel model = JtwigModel.newModel();

        return template.render(model);
    }

    private String displayEditQuestForm(HttpExchange httpExchange) {
        String questName = this.parseStringFromURL(httpExchange, STUDENT_INDEX);
        Quest quest = questController.chooseQuest(questName);
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentor/edit_quest.twig");
        JtwigModel model = JtwigModel.newModel();

        model.with("quest", quest);

        return template.render(model);
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
        String questName = this.parseStringFromURL(httpExchange, STUDENT_INDEX);
        Quest quest = questController.chooseQuest(questName);

        questController.deleteQuest(quest);

        return displayQuests();
    }

    private String editQuest(HttpExchange httpExchange) throws IOException {
        Map <String, String> inputs = this.parseUserInputsFromHttp(httpExchange);

        String name = inputs.get("questName").toString();
        String description = inputs.get("description").toString();
        Integer reward = Integer.valueOf(inputs.get("reward").toString());
        String oldName = this.parseStringFromURL(httpExchange, STUDENT_INDEX);

        Quest quest = new Quest(name, description, reward);

        questController.editQuest(oldName, quest);

        return displayQuests();
    }

    private String addStudent(HttpExchange httpExchange) throws IOException {
        Map <String, String> inputs = this.parseUserInputsFromHttp(httpExchange);

        try {
            studentController.createFromMap(inputs);
        } catch (InvalidUserDataException e) {
            return this.displayStudentForm(null, inputs);
        }

        return displayGroups("Student has been added");
    }

    private String removeStudent(HttpExchange httpExchange) {
        String login = this.parseStringFromURL(httpExchange, STUDENT_INDEX);
        studentController.remove(login);

        return displayGroups("Student has been removed");
    }

    private String editStudent(HttpExchange httpExchange) throws IOException {
        Map <String, String> inputs = this.parseUserInputsFromHttp(httpExchange);

        try {
            studentController.editFromMap(inputs, this.parseStringFromURL(httpExchange, STUDENT_INDEX));
        } catch (InvalidUserDataException e) {
            return this.displayStudentForm(httpExchange, inputs);
        }

        return displayGroups("Student has been edited");
    }

    public String addArtifact(HttpExchange httpExchange) throws IOException {
        Map <String, String> inputs = this.parseUserInputsFromHttp(httpExchange);

        String name = inputs.get("artifactName").toString();
        String description = inputs.get("description").toString();
        Integer price = Integer.valueOf(inputs.get("price").toString());
        String type = inputs.get("type").toString();
        artifactController.createArtifact(name, description, price, type);


        return displayArtifact("Artifact has been added");
    }

    public String removeArtifact(HttpExchange httpExchange) {
        String name = parseStringFromURL(httpExchange, STUDENT_INDEX);
        artifactController.removeArtifact(name);

        return displayArtifact("Artifact has been removed");
    }

    public String editArtifact(HttpExchange httpExchange) throws IOException {
        Map <String, String> inputs = this.parseUserInputsFromHttp(httpExchange);

        String name = inputs.get("artifactName").toString();
        String description = inputs.get("description").toString();
        Integer price = Integer.valueOf(inputs.get("price").toString());
        String type = inputs.get("type").toString();
        String oldName = this.parseStringFromURL(httpExchange, STUDENT_INDEX);

        artifactController.editArtifact(oldName, name, description, price, type);

        return displayArtifact("Artifact has been edited");
    }

    private String markQuest(HttpExchange httpExchange) {
        String login = parseStringFromURL(httpExchange, STUDENT_INDEX);

        String questName = this.parseStringFromURL(httpExchange, QUEST_INDEX);
        questController.markQuestAsDone(studentController.get(login), questController.chooseQuest(questName));

        return displayQuestsToMark("Quest has been marked", httpExchange);
    }

    protected String displayEditPassword(String message) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentor/mentor_change_password.twig");
        JtwigModel model = JtwigModel.newModel();

        model.with("login", "student");
        model.with("msg", message);

        return template.render(model);
    }

}
