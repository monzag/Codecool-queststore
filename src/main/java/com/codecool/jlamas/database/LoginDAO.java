package com.codecool.jlamas.database;

import java.sql.*;

public class LoginDAO {

    public boolean matchLogin(String login, String password) {

        String query = "SELECT * FROM login WHERE login = '" + login +
                     "' AND password = '" + password + "';";

        try (Connection c = ConnectDB.connect();
            Statement stmt = c.createStatement()) {

            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return true;
            }

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public String findLogin(String login) {

        String query = "SELECT * FROM login WHERE login = '" + login + "';";

        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement()) {

            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return login;
            }

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
