package com.codecool.jlamas.controllers;

import com.codecool.jlamas.database.QuestDAO;
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

public class MentorQuestController implements HttpHandler {
    private QuestController questController = new QuestController();
    private ArrayList<Quest> questsList;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String response = "";
        String method = httpExchange.getRequestMethod();

        if (method.equals("GET")) {
            if (httpExchange.getRequestURI().getPath().equals("/mentor/quest/show")) {
                response = displayQuests();
            }

            if (httpExchange.getRequestURI().getPath().equals("/mentor/quest/add")) {
                response = displayAddQuest();
            }

            if (httpExchange.getRequestURI().getPath().matches("/mentor/quest/remove/.+")) {
                response = this.removeQuest(httpExchange);
            }

        }

        if (method.equals("POST")) {
            if (httpExchange.getRequestURI().getPath().equals("/mentor/quest/add")) {
                response = addQuest(httpExchange);
            }
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
        System.out.println(quest.getName());
        questController.deleteQuest(quest);
        System.out.println("2");
        return displayQuests();
    }
}

