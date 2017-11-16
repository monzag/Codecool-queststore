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

    public void update(Group group, String newName) {
        String query = "";
        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement();) {

            query = String.format("UPDATE `group` SET group_tag = '%s' WHERE group_tag = '%s'; ",
                    newName,
                    group.getName());

            query += String.format("UPDATE `mentor` SET group_tag = '%s' WHERE group_tag = '%s'; ",
                    newName,
                    group.getName());

            query += String.format("UPDATE `student` SET group_tag = '%s' WHERE group_tag = '%s'; ",
                    newName,
                    group.getName());

            stmt.executeUpdate(query);

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public Group getGroup(String groupTag) {
        Group group = null;
        String query = "SELECT group_tag FROM `group` WHERE group_tag = '" + groupTag +"';";

        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery(query)){

             group = new Group(rs.getString("group_tag"));

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }

        return group;
    }

    public void delete(Group group) {
        String query = "DELETE FROM `group` WHERE group_tag = '" + group.getName() + "';";

        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement();) {
             stmt.executeUpdate(query);
        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}