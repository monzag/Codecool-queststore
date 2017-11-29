package com.codecool.jlamas.controllers;

import com.codecool.jlamas.database.*;
import com.codecool.jlamas.handlers.Response;
import com.codecool.jlamas.models.account.Codecooler;
import com.codecool.jlamas.models.account.Student;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

public class StudentMenuController implements HttpHandler {

//    private Student student = new StudentDAO().getStudent("student");
    private Map<String, Callable> getCommands = new HashMap<>();
    private Codecooler student;
    private SessionDAO session = new SessionDAO();
    private CookieController cookieController = new CookieController();
    private Response responseCode = new Response();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        String method = httpExchange.getRequestMethod();
        HttpCookie cookie = cookieController.getCookie(httpExchange);

        if (cookie != null) {
            this.student = session.getUserByCookie(httpExchange);
            String userType = new UserDAO().getType(student.getLogin().getValue());

            if (student != null && userType.equals("student")) {

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

        model.with("student", student);
        model.with("artifacts", new ArtifactDAO().requestAll());

        return template.render(model);
    }

    private String displayBoughtArtifact(String message) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/student/buyArtifact.twig");
        JtwigModel model = JtwigModel.newModel();

        model.with("message", message);
        model.with("student", student);
        model.with("artifacts", new ArtifactDAO().requestAll());

        return template.render(model);
    }

    private String buyArtifact(HttpExchange httpExchange) {
        WalletController walletController = new WalletController(student);
        String artifactName = parseUrl(httpExchange, 4);
        String message;

        if (walletController.buyArtifact(new ArtifactController().chooseArtifact(artifactName))) {
            message = "Artifact bought";
        } else {
            message = "Artifact can't be bought";
        }

        return displayBoughtArtifact(message);
    }

    private String parseUrl(HttpExchange httpExchange, int index) {
        return httpExchange.getRequestURI().getPath().split("/")[index];
    }

    private void addGetCommands(HttpExchange httpExchange) {
        getCommands.put("/student", () -> { return displayMovie();} );
        getCommands.put("/student/profile", () -> { return displayProfile();} );
        getCommands.put("/student/wallet", () -> { return displayWallet();} );
        getCommands.put("/student/store/buy/.+", () -> { return buyArtifact(httpExchange);}  );
        getCommands.put("/student/store", () -> {return displayStore();} );
    }

    private String findCommand(HttpExchange httpExchange, Map<String, Callable> mapName) {
        String response = null;
        String path = httpExchange.getRequestURI().getPath();
        Set<String> keys = mapName.keySet();

        addGetCommands(httpExchange);

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