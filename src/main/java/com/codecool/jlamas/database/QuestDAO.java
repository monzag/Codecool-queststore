package com.codecool.jlamas.database;

import java.sql.SQLException;

public class QuestDAO extends AbstractDAO {
    public QuestDAO() {

    }

    public void insertQuest(String name, String description, Integer reward) {
        String sql = "INSERT INTO quest(name, description, reward) VALUES (?, ?, ?);";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = connection.prepareStatement(sql)) {

            prepStmt.setString(1, name);
            prepStmt.setString(2, description);
            prepStmt.setInt(3, reward);
            prepStmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error");
        }
    }

    public void update(int id, String name, String description, Integer reward) {
        String sql = "UPDATE quest SET name = ? , "
                + "description = ? "
                + "reward = ? "
                + "WHERE id = ?";

        try (Connection connection = this.connect();
                PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.setInt(3, reward);
            pstmt.setInt(4, id);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error");
        }
    }
}
