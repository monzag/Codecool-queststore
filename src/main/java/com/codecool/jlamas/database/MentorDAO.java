package com.codecool.jlamas.database;

import java.sql.*;
import java.util.ArrayList;

import com.codecool.jlamas.models.account.Mentor;
import com.codecool.jlamas.models.accountdata.*;


public class MentorDAO {

    public MentorDAO() {

    }

    public ArrayList<Mentor> requestAll() {
        String query = String.format("%s %s %s %s %s %s %s"
            , "SELECT user.login, user.email, user.name, user.surname, login.password, mentor.class_tag"
            , "FROM user"
            ,     "INNER JOIN login"
            ,             "ON login.login = user.login"
            ,     "INNER JOIN mentor"
            ,             "ON mentor.login = user.login"
            , "WHERE user.type = 'Mentor';");


        ArrayList<Mentor> mentors = new ArrayList<Mentor>();
        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement(sql);) {

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

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

            rs.close();
            stmt.close();

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }

        return mentors;
    }

    public boolean insert(Mentor mentor) {
    // true if was successful

    }

    public boolean update(Mentor mentor) {
    // true if was successful

    }

    public boolean delete(Mentor mentor) {
    // true if was successful

    }

}
