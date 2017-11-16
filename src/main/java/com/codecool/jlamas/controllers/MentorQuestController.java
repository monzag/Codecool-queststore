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

public class MentorQuestController implements HttpHandler {
    private QuestController questController = new QuestController();
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

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
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

    private String displayAddQuest() {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/add_quest.twig");
        JtwigModel model = JtwigModel.newModel();

        return template.render(model);
    }

    private String displayQuests() {
        questsList = questController.showAllQuests();
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentor_quests.twig");
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

    private String parseQuestName(HttpExchange httpExchange) {
        return httpExchange.getRequestURI().getPath().split("/")[4];
    }

    private String removeQuest(HttpExchange httpExchange) {
        String questName = parseQuestName(httpExchange);
        Quest quest = questController.chooseQuest(questName);

        questController.deleteQuest(quest);

        return displayQuests();
    }

    private String displayEditQuestForm(HttpExchange httpExchange) {
        String questName = parseQuestName(httpExchange);
        Quest quest = questController.chooseQuest(questName);
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/edit_quest.twig");
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
        String oldName = parseQuestName(httpExchange);

        Quest quest = new Quest(name, description, reward);

        questController.editQuest(oldName, quest);

        return displayQuests();
    }

    private void addGetCommands(HttpExchange httpExchange) {
        getCommands.put("/mentor/quest/show", () -> { return displayQuests();} );
        getCommands.put("/mentor/quest/add", () -> {return displayAddQuest();} );
        getCommands.put("/mentor/quest/remove/.+", () -> { return removeQuest(httpExchange);} );
        getCommands.put("/mentor/quest/edit/.+", () -> {return displayEditQuestForm(httpExchange);} );
    }

    private void addPostCommands(HttpExchange httpExchange) {
        postCommands.put("/mentor/quest/add", () -> { return addQuest(httpExchange);}  );
        postCommands.put("/mentor/quest/edit/.+", () -> { return editQuest(httpExchange);}  );
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