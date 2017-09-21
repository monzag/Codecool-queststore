package com.codecool.jlamas.database;

import java.sql.*;
import java.util.ArrayList;

import com.codecool.jlamas.models.account.Mentor;
import com.codecool.jlamas.models.accountdata.*;


public class MentorDAO {

    public MentorDAO() {

    }

    public ArrayList<Mentor> requestAll() {
        ArrayList<Mentor> mentors;
        String sql = "SELECT * FROM `user` JOIN `mentor` ON ;";

        mentors = new ArrayList<Mentor>();
        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement(sql);) {



        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }

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
