package com.codecool.jlamas.database;

import com.codecool.jlamas.models.quest.Quest;
import java.sql.*;

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

    // public void update(int id, String name, String description, Integer reward) {
    //     String sql = "UPDATE quest SET name = ? , "
    //             + "description = ? "
    //             + "reward = ? "
    //             + "WHERE id = ?";
    //
    //     try (Connection connection = this.connect();
    //             PreparedStatement pstmt = connection.prepareStatement(sql)) {
    //
    //         pstmt.setString(1, name);
    //         pstmt.setString(2, description);
    //         pstmt.setInt(3, reward);
    //         pstmt.setInt(4, id);
    //
    //         pstmt.executeUpdate();
    //     } catch (SQLException e) {
    //         System.out.println("Error");
    //     }
    // }
}
