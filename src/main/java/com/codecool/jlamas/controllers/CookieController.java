package com.codecool.jlamas.controllers;

import com.codecool.jlamas.database.SessionDAO;
import com.sun.net.httpserver.HttpExchange;

import java.net.HttpCookie;
import java.util.UUID;

public class CookieController {

    private SessionDAO session;

    public CookieController() {
        this.session = new SessionDAO();
    }

    public HttpCookie getCookie(HttpExchange httpExchange) {
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        HttpCookie cookie = null;
        if (cookieStr != null) {  // Cookie already exists
            cookie = HttpCookie.parse(cookieStr).get(0);
        }

        return cookie;
    }

    public HttpCookie createCookie(HttpExchange httpExchange, String login) {
        // Create a new cookie
        String sessionId = UUID.randomUUID().toString();
        HttpCookie cookie = new HttpCookie("sessionId", sessionId);
        httpExchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
        session.addCookieToDb(cookie, login);

        return cookie;
    }
}
