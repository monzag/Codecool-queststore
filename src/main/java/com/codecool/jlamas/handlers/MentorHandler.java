package com.codecool.jlamas.handlers;

import com.codecool.jlamas.controllers.*;
import com.codecool.jlamas.database.SessionDAO;
import com.codecool.jlamas.database.UserDAO;
import com.codecool.jlamas.exceptions.ArtifactNameAlreadyUsedException;
import com.codecool.jlamas.exceptions.InvalidUserDataException;
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
    private static final Integer ARTIFACT_INDEX = 4;
    private static final Integer QUEST_INDEX = 6;

    private static final String MAIN = "templates/main.twig";
    private static final String PROFILE = "classpath:/templates/mentor/mentor_profile.twig";
    private static final String CHANGE_PASSWORD = "classpath:/templates/change_password.twig";
    private static final String STUDENT_FORM = "classpath:/templates/mentor/mentor_student_form.twig";
    private static final String ARTIFACT_FORM = "classpath:/templates/mentor/mentor_artifact_form.twig";
    private static final String ARTIFACT_LIST = "classpath:/templates/mentor/mentor_artifact_list.twig";
    private static final String GROUP_LIST = "classpath:/templates/mentor/mentor_group_list.twig";
    private static final String QUEST_LIST = "classpath:/templates/mentor/mentor_quest_list.twig";
    private static final String QUEST_MARK = "classpath:/templates/mentor/mentor_quest_mark.twig";
    private static final String QUEST_ADD = "classpath:/templates/mentor/mentor_quest_add.twig";
    private static final String QUEST_EDIT = "classpath:/templates/mentor/mentor_quest_edit.twig";

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
        getCommands.put("/mentor/artifact/add", () -> { return displayArtifactForm(null, null);} );
        getCommands.put("/mentor/artifact/remove/.+", () -> { return removeArtifact(httpExchange);} );
        getCommands.put("/mentor/artifact/edit/.+", () -> { return displayArtifactForm(httpExchange, null);} );
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

    protected JtwigModel getContent(String content_path) {
        JtwigModel model = JtwigModel.newModel();

        model.with("nav_path", "classpath:/templates/mentor/nav_menu.twig");
        model.with("content_path", content_path);
        model.with("login", mentor.getLogin().getValue());

        return model;
    }

    protected String displayProfile() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(PROFILE);
        JtwigModel model = JtwigModel.newModel();

        // profile pic found by login
        model.with("login", mentor.getLogin().getValue());
        model.with("mentor", mentor);

        return template.render(model);
    }

    private String displayGroups(String message) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(GROUP_LIST);
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

    public String displayArtifactForm(HttpExchange httpExchange, Map<String, String> inputs) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(ARTIFACT_FORM);
        JtwigModel model = JtwigModel.newModel();

        if (inputs == null && httpExchange != null) {
            model.with("artifact", artifactController.get(this.parseStringFromURL(httpExchange, ARTIFACT_INDEX)));
        }
        else if (inputs != null) {
            model.with("artifactName", inputs.get("artifactName"));
            model.with("description", inputs.get("description"));
            model.with("price", inputs.get("price"));
        }

        return template.render(model);
    }

    public String displayArtifact(String message) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(ARTIFACT_LIST);
        JtwigModel model = JtwigModel.newModel();

        // profile pic found by login
        model.with("login", "student");
        model.with("message", message);
        model.with("artifacts", artifactController.getAll());

        return template.render(model);
    }

    private String displayQuests() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(QUEST_LIST);
        JtwigModel model = JtwigModel.newModel();

        return template.render(model.with("questsList", questController.showAllQuests()));
    }

    private String displayQuestsToMark(String message, HttpExchange httpExchange) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(QUEST_MARK);
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
        JtwigTemplate template = JtwigTemplate.classpathTemplate(QUEST_ADD);
        JtwigModel model = JtwigModel.newModel();

        return template.render(model);
    }

    private String displayEditQuestForm(HttpExchange httpExchange) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(QUEST_EDIT);
        JtwigModel model = JtwigModel.newModel();

        model.with("quest", questController.chooseQuest(this.parseStringFromURL(httpExchange, STUDENT_INDEX)));

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
        String oldName = this.parseStringFromURL(httpExchange, QUEST_INDEX);

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

        ArtifactController ctrl = new  ArtifactController();
        try {
            ctrl.createFromMap(inputs);
        } catch (ArtifactNameAlreadyUsedException e) {
            return this.displayArtifactForm(null, inputs);
        }

        return displayArtifact("Artifact has been added");
    }

    public String editArtifact(HttpExchange httpExchange) throws IOException {
        Map <String, String> inputs = this.parseUserInputsFromHttp(httpExchange);

        ArtifactController ctrl = new ArtifactController();
        try {
            ctrl.editFromMap(inputs, this.parseStringFromURL(httpExchange, ARTIFACT_INDEX));
        } catch (ArtifactNameAlreadyUsedException e) {
            return this.displayArtifactForm(httpExchange, inputs);
        }

        return displayArtifact("Artifact has been edited");
    }

    public String removeArtifact(HttpExchange httpExchange) {
        String name = parseStringFromURL(httpExchange, ARTIFACT_INDEX);
        artifactController.remove(name);

        return displayArtifact("Artifact has been removed");
    }

    private String markQuest(HttpExchange httpExchange) {
        String login = parseStringFromURL(httpExchange, STUDENT_INDEX);

        String questName = this.parseStringFromURL(httpExchange, QUEST_INDEX);
        questController.markQuestAsDone(studentController.get(login), questController.chooseQuest(questName));

        return displayQuestsToMark("Quest has been marked", httpExchange);
    }

    protected String displayEditPassword(String message) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(CHANGE_PASSWORD);
        JtwigModel model = JtwigModel.newModel();

        model.with("login", "student");
        model.with("msg", message);

        return template.render(model);
    }

}
