package com.codecool.jlamas.database;

import com.codecool.jlamas.models.quest.Quest;
import java.sql.*;
import java.util.ArrayList;

public class QuestDAO {
    public QuestDAO() {

    }

    public void insertQuest(Quest quest) {
        String sql = "INSERT INTO quest(name, description, reward) VALUES (?, ?, ?);";

        try (Connection c = ConnectDB.connect();
                PreparedStatement pstmt = c.prepareStatement(sql);) {

            pstmt.setString(1, quest.getName());
            pstmt.setString(2, quest.getDescription());
            pstmt.setInt(3, quest.getReward());
            pstmt.executeUpdate();

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Quest> selectAll(){
        ArrayList<Quest> questList = new ArrayList<Quest>();
        String sql = "SELECT name, description, reward FROM quest";

        try (Connection c = ConnectDB.connect();
             Statement stmt  = c.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            while (rs.next()) {
                Quest quest = new Quest(rs.getString("name"), rs.getString("description"), rs.getInt("reward"));
                questList.add(quest);
            }
        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }

        return questList;
    }

    public void updateQuest(Quest quest, String preUpdateName) {
        String sql = "UPDATE quest SET name = ? , "
                + "description = ? , "
                + "reward = ? "
                + "WHERE name = ?";

        try (Connection c = ConnectDB.connect();
                PreparedStatement pstmt = c.prepareStatement(sql);) {

            pstmt.setString(1, quest.getName());
            pstmt.setString(2, quest.getDescription());
            pstmt.setInt(3, quest.getReward());
            pstmt.setString(4, preUpdateName);
            pstmt.executeUpdate();

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteQuest(Quest quest) {
        String query;

        try (Connection c = ConnectDB.connect();
                Statement stmt = c.createStatement()) {

            query = String.format("DELETE FROM `quest` WHERE name = '%s'; ",
                    quest.getName());

            stmt.executeUpdate(query);

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Quest selectQuest(String questName) {
        String sql = String.format("SELECT * FROM `quest` WHERE name = '%s'; ",
                questName);
        Quest quest = null;

        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            quest = new Quest(rs.getString("name"), rs.getString("description"), rs.getInt("reward"));

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }

        return quest;
    }
}
