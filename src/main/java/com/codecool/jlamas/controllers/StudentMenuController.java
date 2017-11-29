package com.codecool.jlamas.controllers;

import com.codecool.jlamas.database.*;
import com.codecool.jlamas.handlers.AbstractHandler;
import com.codecool.jlamas.handlers.Response;
import com.codecool.jlamas.models.account.Codecooler;
import com.codecool.jlamas.models.account.Student;
import com.codecool.jlamas.models.artifact.Artifact;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

public class StudentMenuController extends AbstractHandler implements HttpHandler {

    private WalletController walletController;
    private TeamPurchaseController teamPurchaseController;
    private Map<String, Callable> getCommands = new HashMap<>();
    private Map<String, Callable> postCommands = new HashMap<>();
    private Student student;
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
            if (userType.equals("student")) {
                this.student = (Student) session.getUserByCookie(httpExchange);
                walletController = new WalletController(student);

                if (student != null) {

                    if (method.equals("GET")) {
                        response = findCommand(httpExchange, getCommands);
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
    }

    private String displayMovie() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/student/student.twig");
        JtwigModel model = JtwigModel.newModel();

        return template.render(model);
    }

    private String displayProfile() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/student/profile.twig");
        JtwigModel model = JtwigModel.newModel();

        return template.render(model);
    }

    private String displayWallet() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/student/wallet.twig");
        JtwigModel model = JtwigModel.newModel();

        model.with("student", student);
        model.with("artifacts", new OwnedArtifactDAO().requestAllBy(student));

        return template.render(model);
    }

    private String displayStore() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/student/store.twig");
        JtwigModel model = JtwigModel.newModel();

        model.with("message", null);
        model.with("student", student);
        model.with("artifacts", new ArtifactDAO().requestAll());

        return template.render(model);
    }

    private String displayBoughtArtifact(String message) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/student/store.twig");
        JtwigModel model = JtwigModel.newModel();

        model.with("message", message);
        model.with("student", student);
        model.with("artifacts", new ArtifactDAO().requestAll());

        return template.render(model);
    }

    private String buyArtifact(HttpExchange httpExchange) {
        String artifactName = parseUrl(httpExchange, 4);
        String message;

        if (walletController.buyArtifact(new ArtifactController().chooseArtifact(artifactName))) {
            message = "Artifact bought";
        } else {
            message = "Artifact can't be bought";
        }

        return displayBoughtArtifact(message);
    }

    private String openTeamPurchase(HttpExchange httpExchange) {

        String artifactName = parseUrl(httpExchange, 4);
        //TODO: MISSING MESSAGE!

        return chooseStudentsForPurchase("", new ArtifactController().chooseArtifact(artifactName));
    }

    private String chooseStudentsForPurchase(String errmsg, Artifact artifact) {

        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/student/chooseStudentsForPurchase.twig");
        JtwigModel model = JtwigModel.newModel();

        model.with("errmsg", errmsg);
        model.with("artifact", artifact);
        model.with("students", new StudentDAO().requestAll());

        return template.render(model);
    }

    private String addTeamPurchase(HttpExchange httpExchange) throws IOException {

        String artifactName = parseUrl(httpExchange, 4);
        ArrayList<Student> students = inputsToStudents(this.parseUserInputsFromHttp(httpExchange));
        String message = "Pending purchase opened";

        new TeamPurchaseController().addTeamPurchase(students, new ArtifactDAO().selectArtifact(artifactName));

        return displayTeamPurchase(message);
    }

    private String acceptTeamPurchase(HttpExchange httpExchange) {

        Integer id = Integer.parseInt(parseUrl(httpExchange, 4));
        String message;

        if (new TeamPurchaseController().acceptPurchaseRequest(student, id)) {
            message = "Request accepted";
        } else {
            message = "Not enough money!";
        }
        return displayTeamPurchase(message);
    }

    private String cancelTeamPurchase(HttpExchange httpExchange) {

        Integer id = Integer.parseInt(parseUrl(httpExchange, 4));
        new TeamPurchaseController().cancelTeamPurchase(id);
        String message = "Request canceled for all students";

        return displayTeamPurchase(message);
    }

    private String displayTeamPurchase(String message) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/student/teamPurchase.twig");
        JtwigModel model = JtwigModel.newModel();

        model.with("message", message);
        model.with("student", student);

        return template.render(model);
    }

    private ArrayList<Student> inputsToStudents(Map<String, String> inputs) {

        StudentDAO studentDAO = new StudentDAO();
        ArrayList<Student> students = new ArrayList<>();
        for (String value : inputs.values()) {
            students.add(studentDAO.getStudent(value));
        }
        return students;
    }

    private String parseUrl(HttpExchange httpExchange, int index) {
        return httpExchange.getRequestURI().getPath().split("/")[index];
    }

    protected void addGetCommands(HttpExchange httpExchange) {
        getCommands.put("/student", () -> { return displayMovie();} );
        getCommands.put("/student/profile", () -> { return displayProfile();} );
        getCommands.put("/student/wallet", () -> { return displayWallet();} );
        getCommands.put("/student/store/buy/.+", () -> { return buyArtifact(httpExchange);}  );
        getCommands.put("/student/store", () -> { return displayStore();} );
        getCommands.put("/student/team_purchases/open/.+", () -> { return openTeamPurchase(httpExchange);} );

    }

    protected void addPostCommands(HttpExchange httpExchange) {
        postCommands.put("/student/team_purchases/add/.+", () -> { return addTeamPurchase(httpExchange);} );
        postCommands.put("/student/team_purchases/accept/.+", () -> { return acceptTeamPurchase(httpExchange);} );
        postCommands.put("/student/team_purchases/cancel/.+", () -> { return cancelTeamPurchase(httpExchange);} );
    }
}