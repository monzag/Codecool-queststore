package com.codecool.jlamas.database;

import java.sql.*;

public class UserDAO {

    public UserDAO() {}

    public String getType(String login) {

        String query = "SELECT type FROM user WHERE user.login = " + login + ";";

        try (Connection c = ConnectDB.connect();
            Statement stmt = c.createStatement()) {

            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                String userType = rs.getString("type");
                return userType;
            }

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
