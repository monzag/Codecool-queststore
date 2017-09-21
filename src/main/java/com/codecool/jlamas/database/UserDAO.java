package com.codecool.jlamas.database;

import java.sql.*;

import com.codecool.jlamas.models.account.Admin;
import com.codecool.jlamas.models.account.Mentor;
import com.codecool.jlamas.models.accountdata.Login;
import com.codecool.jlamas.models.accountdata.Password;
import com.codecool.jlamas.models.accountdata.Mail;

public class UserDAO {

    public UserDAO() {}

    public String getType(String login) {

        String query = "SELECT type FROM user WHERE login = '" + login + "';";

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

        String query = "SELECT * FROM user WHERE login = '" + login + "';";

        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement()) {

            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                Login loginInstance = new Login(login);
                Password password = new Password("admin");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String email = rs.getString("email");
                Mail mail = new Mail(email);
                Admin admin = new Admin(loginInstance, password, mail, name, surname);
                return admin;
            }

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
