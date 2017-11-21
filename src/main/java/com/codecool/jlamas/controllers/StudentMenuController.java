package com.codecool.jlamas.controllers;

import com.codecool.jlamas.models.account.Student;
import com.codecool.jlamas.views.StudentView;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

public class StudentMenuController {

    private WalletController walletController;
    private Student student = new Student();
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

    private void addGetCommands(HttpExchange httpExchange) {
        getCommands.put("/student", () -> { return displayProfile(httpExchange);} );
        getCommands.put("/student/wallet", () -> { return displayWallet();} );
        getCommands.put("/student/store", () -> {return displayStore();} );
    }

    private void addPostCommands(HttpExchange httpExchange) {
        postCommands.put("/student/wallet/add/.+", () -> { return buyArtifact(httpExchange);}  );
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
}