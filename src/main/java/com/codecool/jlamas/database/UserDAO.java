package com.codecool.jlamas.database;

import java.sql.*;

import com.codecool.jlamas.models.account.Admin;
import com.codecool.jlamas.models.account.Mentor;

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

    public Admin getAdmin(String login) {

        String query = "SELECT * FROM user WHERE user.login = " + login + ";";

        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement()) {

            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String email = rs.getString("email");
                Admin admin = new Admin(login, name, surname, email);
                return admin;
            }

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Mentor getMentor(String login) {

        String query = "SELECT * FROM user WHERE user.login = " + login + ";";

        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement()) {

            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String email = rs.getString("email");
                // TODO: join with mentor class_tag
                // Mentor mentor = new Mentor(login, name, surname, email);
                // return mentor;
            }

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
