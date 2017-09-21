package com.codecool.jlamas.database;

import java.sql.*;
import java.util.ArrayList;

import com.codecool.jlamas.models.account.Mentor;
import com.codecool.jlamas.models.accountdata.*;


public class MentorDAO {

    public MentorDAO() {

    }

    public ArrayList<Mentor> requestAll() {
        String query = String.format("%s %s %s %s %s %s %s",
            , "SELECT user.login, user.email, user.name, user.surname, login.password, mentor.class_tag"
            , "FROM user"
            ,     "INNER JOIN login"
            ,             "ON login.login = user.login"
            ,     "INNER JOIN mentor"
            ,             "ON mentor.login = user.login"
            , "WHERE user.type = 'Mentor';");


        ArrayList<Mentor> mentors = new ArrayList<Mentor>();
        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery(quary);) {

            while (rs.next()) {
                Mentor mentor = new Mentor();

                mentor.setName(rs.getString("name"));
                mentor.setSurname(rs.getString("surname"));
                mentor.setLogin(new Login(rs.getString("login")));
                mentor.setPassword(new Password(rs.getString("password")));
                mentor.setEmail(new Mail(rs.getString("email")));
                mentor.setClassTag(rs.getString("class_tag"));

                mentors.add(mentor);
            }

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }

        return mentors;
    }

    public boolean insert(Mentor mentor) {
    // true if was successful
    String query;


        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement();) {

            query = String.format("INSERT INTO `user` VALUES('%s', '%s', '%s', '%s', 'Mentor'); ",
                    mentor.getLogin().getValue(),
                    mentor.getEmail().getValue(),
                    mentor.getName(),
                    mentor.getSurname());

            query += String.format("INSERT INTO `login` VALUES('%s', '%s'); ",
                    mentor.getLogin().getValue(),
                    mentor.getPassword().getValue());

            query += String.format("INSERT INTO `mentor` VALUES('%s', '%s'); ",
                    mentor.getLogin().getValue(),
                    mentor.getClassTag());

            stmt.executeUpdate(sql);

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());

            return false;
        }
        return true;

    }

    public boolean update(Mentor mentor) {
    // true if was successful

    }

    public boolean delete(Mentor mentor) {
    // true if was successful

    }

}
