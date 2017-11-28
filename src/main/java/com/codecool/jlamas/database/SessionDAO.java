package com.codecool.jlamas.database;

import com.codecool.jlamas.controllers.CookieController;
import com.sun.net.httpserver.HttpExchange;

import java.net.HttpCookie;
import java.sql.*;

public class SessionDAO {

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
        HttpCookie cookie = new CookieController().getCookie(httpExchange);
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
