package com.codecool.jlamas.handlers;

import com.codecool.jlamas.controllers.CookieController;
import com.codecool.jlamas.controllers.UserController;
import com.codecool.jlamas.database.SessionDAO;
import com.codecool.jlamas.exceptions.NotMatchingPasswordException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;


public abstract class AbstractHandler implements HttpHandler {

    @Override
    public abstract void handle(HttpExchange httpExchange) throws IOException;

    protected abstract void addGetCommands (HttpExchange httpExchange);

    protected abstract void addPostCommands (HttpExchange httpExchange);

    protected String findCommand(HttpExchange httpExchange, Map<String, Callable> mapName) {
        String response = null;
        String path = httpExchange.getRequestURI().getPath();
        Set<String> keys = mapName.keySet();

        this.addGetCommands(httpExchange);
        this.addPostCommands(httpExchange);

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

    protected Map<String, String> parseUserInputsFromHttp(HttpExchange httpExchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String formData = br.readLine();

        return this.parseFormData(formData);
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

    protected String parseStringFromURL(HttpExchange httpExchange, Integer index) {
        return httpExchange.getRequestURI().getPath().split("/")[index];
    }

    protected Integer parseIntFromURL(HttpExchange httpExchange, Integer index) {
        return Integer.valueOf(this.parseStringFromURL(httpExchange, index));
    }

    protected void logout(HttpExchange httpExchange) throws IOException {
        SessionDAO session = new SessionDAO();
        CookieController cookieController = new CookieController();
        Response responseCode = new Response();

        session.removeCookieFromDb(cookieController.getCookie(httpExchange));
        cookieController.removeCookie(httpExchange);
        responseCode.sendRedirectResponse(httpExchange, "/");
    }

    protected String editPassword(HttpExchange httpExchange) throws IOException {
        Map<String, String> inputs = this.parseUserInputsFromHttp(httpExchange);
        UserController userController = new UserController();

        try {
            int loginIndex = 4;
            userController.editPassword(inputs, this.parseStringFromURL(httpExchange, loginIndex));

        } catch (NotMatchingPasswordException e) {
            return this.displayEditPassword();
        }

        return this.displayProfile();
    }

    protected abstract String displayEditPassword ();

    protected abstract String displayProfile();
}