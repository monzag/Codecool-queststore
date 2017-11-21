package com.codecool.jlamas.controllers;

import com.codecool.jlamas.database.ArtifactDAO;
import com.codecool.jlamas.database.OwnedArtifactDAO;
import com.codecool.jlamas.models.account.Student;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

public class StudentMenuController implements HttpHandler {

    private Student student = new Student();
    private WalletController walletController = new WalletController(student);
    private Map<String, Callable> getCommands = new HashMap<>();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        String method = httpExchange.getRequestMethod();

        if (method.equals("GET")) {
            response = findCommand(httpExchange, getCommands);
        }

        final byte[] finalResponseBytes = response.getBytes("UTF-8");
        httpExchange.sendResponseHeaders(200, finalResponseBytes.length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(finalResponseBytes);
        os.close();
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

        model.with("artifacts", new OwnedArtifactDAO().requestAllBy(student));

        return template.render(model);
    }

    private String displayStore() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/student/store.twig");
        JtwigModel model = JtwigModel.newModel();

        model.with("artifacts", new ArtifactDAO().requestAll());

        return template.render(model);
    }

    private String displayBoughtArtifact(String message, HttpExchange httpExchange) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/student/store.twig");
        JtwigModel model = JtwigModel.newModel();

        model.with("message", message);
        model.with("student", student);
        model.with("artifacts", new ArtifactDAO().requestAll());

        return template.render(model);
    }

    private String buyArtifact(HttpExchange httpExchange) {
        //String login = parseUrl(httpExchange, 3);
        String artifactName = parseUrl(httpExchange, 4);
        walletController.addOwnedArtifact(new ArtifactController().chooseArtifact(artifactName));

        return displayBoughtArtifact("Quest has been marked", httpExchange);
    }

    private String parseUrl(HttpExchange httpExchange, int index) {
        return httpExchange.getRequestURI().getPath().split("/")[index];
    }

    private void addGetCommands(HttpExchange httpExchange) {
        getCommands.put("/student", () -> { return displayMovie();} );
        getCommands.put("/student/profile", () -> { return displayProfile();} );
        getCommands.put("/student/wallet", () -> { return displayWallet();} );
        getCommands.put("/student/wallet/add/.+", () -> { return buyArtifact(httpExchange);}  );
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