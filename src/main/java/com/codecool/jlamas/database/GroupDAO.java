package com.codecool.jlamas.database;

import java.sql.*;

import com.codecool.jlamas.models.accountdata.Group;

public class GroupDAO {

    public GroupDAO() {

    }

    public void insertGroup(Group group) {
        String query = "INSERT INTO group(name) VALUES (?);";

        try (Connection c = ConnectDB.connect();
            PreparedStatement pstmt = c.prepareStatement(query);) {

            pstmt.setString(1, group.getName());
            pstmt.executeUpdate();

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}