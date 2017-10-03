package com.codecool.jlamas.database;

import java.sql.*;
import java.util.ArrayList;

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

    public ArrayList<Group> selectAll() {
        ArrayList<Group> groups = new ArrayList<>();
        String query = "SELECT name FROM group";

        try (Connection c = ConnectDB.connect();
             Statement stmt  = c.createStatement();
             ResultSet rs    = stmt.executeQuery(query)){

            while (rs.next()) {
                Group group = new Group(rs.getString("name"));
                groups.add(group);
            }
        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }

        return groups;
    }
}