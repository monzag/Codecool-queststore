package com.codecool.jlamas.controllers;

import com.codecool.jlamas.database.*;
import com.codecool.jlamas.handlers.Response;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.HttpCookie;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class AppController implements HttpHandler {

    private LoginDAO loginData;
    private UserDAO userData;
    private CookieController cookieController;
    private SessionDAO session;
    private Response responseCode;

    public AppController() {
        this.loginData = new LoginDAO();
        this.userData = new UserDAO();
        this.cookieController = new CookieController();
        this.session = new SessionDAO();
        this.responseCode = new Response();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        HttpCookie cookie = cookieController.getCookie(httpExchange);

        if (cookie != null) {
            if (method.equals("GET")) {
                String login = session.getLoginByCookie(httpExchange);
                if (login == null) {
                    logout(httpExchange);
                } else {
                    launchUserController(login, httpExchange);
                }
            }
        } else {

            if (method.equals("GET")) {
                displayLoginFormula(httpExchange);
            } else {
                login(httpExchange);
            }
        }
    }

    public void displayLoginFormula(HttpExchange httpExchange) throws IOException {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/login.twig");
        JtwigModel model = JtwigModel.newModel();

        String response = template.render(model);
        responseCode.sendOKResponse(response, httpExchange);
    }

    public void login(HttpExchange httpExchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String formData = br.readLine();

        System.out.println(formData);
        Map inputs = parseFormData(formData);
        String login = inputs.get("username").toString();
        String password = inputs.get("password").toString();

        if (loginData.matchLogin(login, password)) {
            cookieController.createCookie(httpExchange, login);
            launchUserController(login, httpExchange);

        } else {
            displayLoginFormula(httpExchange);
        }
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

    public void launchUserController(String login, HttpExchange httpExchange) throws IOException {
        String location = "/";
        String userType = this.userData.getType(login);

        if (userType.equals("admin")) {
            location = "/admin";

        } else if (userType.equals("mentor")) {
            location = "/mentor";


        } else if (userType.equals("student")) {
            location = "/student";
        }

        responseCode.sendRedirectResponse(httpExchange, location);
    }

    public void logout(HttpExchange httpExchange) throws IOException {
        session.removeCookieFromDb(cookieController.getCookie(httpExchange));
        cookieController.removeCookie(httpExchange);

        responseCode.sendRedirectResponse(httpExchange, "/");
    }
}
