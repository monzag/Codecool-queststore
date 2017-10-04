package com.codecool.jlamas.database;

import java.sql.*;
import java.util.ArrayList;

import com.codecool.jlamas.models.accountdata.Group;

public class GroupDAO {

    public GroupDAO() {

    }

    public void insertGroup(Group group) {
        String query = "INSERT INTO `group` VALUES (?);";

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
        String query = "SELECT group_tag FROM `group`;";

        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery(query)){

            while (rs.next()) {
                Group group = new Group(rs.getString("group_tag"));
                groups.add(group);
            }
        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }

        return groups;
    }

    public void update(Group group, String preUpdateName) {
        String query = "UPDATE `group` SET group_tag = ? WHERE group_tag = ?";

        try (Connection c = ConnectDB.connect();
                PreparedStatement pstmt = c.prepareStatement(query);) {

            pstmt.setString(1, group.getName());
            pstmt.setString(2, preUpdateName);
            pstmt.executeUpdate();

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}