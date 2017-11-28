package com.codecool.jlamas.controllers;

import com.codecool.jlamas.database.ConnectDB;
import com.sun.net.httpserver.HttpExchange;

import java.net.HttpCookie;
import java.sql.*;
import java.util.UUID;

public class CookieController {

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
        this.addCookieToDb(cookie, login);

        return cookie;
    }

    public void addCookieToDb(HttpCookie cookie, String login) {
        String query = "INSERT INTO `cookie` VALUES (?, ?);";

        try (Connection c = ConnectDB.connect();
             PreparedStatement pstmt = c.prepareStatement(query)) {

            pstmt.setString(1, cookie.getValue());
            pstmt.setString(2, login);
            pstmt.executeUpdate();

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public String getLoginByCookie(HttpExchange httpExchange) {
        String login = "";
        HttpCookie cookie = getCookie(httpExchange);
        String query = "SELECT login FROM `cookie` WHERE sessionId = '" + cookie.getValue() + "';";

        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement()) {

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                login = rs.getString("login");
            }

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }

        return login;
    }
}
