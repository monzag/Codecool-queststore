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
    private QuestDAO questDAO = new QuestDAO();
    private ArrayList<Quest> questsList;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        questsList = questDAO.selectAll();

        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/mentor_quests.twig");
        JtwigModel model = JtwigModel.newModel();

        String response = "";
        String method = httpExchange.getRequestMethod();

        if (method.equals("GET")) {
            response = template.render(model.with("questsList", questsList));
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
}

