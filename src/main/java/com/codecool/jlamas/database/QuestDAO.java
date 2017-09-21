package com.codecool.jlamas.database;

import java.sql.*;
import java.util.ArrayList;

public class QuestDAO {
    public QuestDAO() {

    }

    public void insertQuest(String name, String description, Integer reward) {
        String sql = "INSERT INTO quest(name, description, reward) VALUES (?, ?, ?);";

        try (Connection c = ConnectDB.connect();
                PreparedStatement pstmt = c.prepareStatement(sql);) {

            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.setInt(3, reward);
            pstmt.executeUpdate();

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Quest> selectAll(){
        ArrayList<Quest> questList = new ArrayList<Quest>();
        String sql = "SELECT id, name, capacity FROM warehouses";

        try (Connection c = ConnectDB.connect();
             Statement stmt  = c.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            while (rs.next()) {
                Quest quest = new Quest(rs.getString("name"), rs.getString("description"), rs.getInt("reward"));
                questList.add(quest);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return questList;
    }
}
