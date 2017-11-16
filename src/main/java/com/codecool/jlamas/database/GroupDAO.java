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

            pstmt.setString(1, preUpdateName);
            pstmt.setString(2, group.getName());
            pstmt.executeUpdate();

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