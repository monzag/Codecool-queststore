package com.codecool.jlamas.database;

import com.codecool.jlamas.controllers.CookieController;
import com.codecool.jlamas.models.account.Codecooler;
import com.sun.net.httpserver.HttpExchange;

import java.net.HttpCookie;
import java.sql.*;

public class SessionDAO {

    private UserDAO userDAO;
    private MentorDAO mentorDAO;
    private StudentDAO studentDAO;

    public SessionDAO() {
        this.userDAO = new UserDAO();
        this.mentorDAO = new MentorDAO();
        this.studentDAO = new StudentDAO();
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
        String login = null;
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

    public Codecooler getUserByCookie(HttpExchange httpExchange) {
        Codecooler codecooler = null;
        String login = getLoginByCookie(httpExchange);
        String type = userDAO.getType(login);

        if (type.equals("admin")) {
            codecooler = userDAO.getAdmin(login);
        }
        if (type.equals("mentor")) {
            codecooler = mentorDAO.getMentor(login);
        }

        if (type.equals("student")) {
            codecooler = studentDAO.getStudent(login);
        }
        return codecooler;
    }

    public void removeCookieFromDb(HttpCookie cookie) {
        String sessionId = cookie.getValue();

        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement()) {


            String query = String.format("DELETE FROM `cookie` WHERE sessionId = '%s'; ",
                    sessionId);

            stmt.executeUpdate(query);

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
