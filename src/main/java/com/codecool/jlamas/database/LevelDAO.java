package com.codecool.jlamas.database;

import com.codecool.jlamas.models.level.Level;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LevelDAO {

    public LevelDAO() {
    }

    public void insertLevel(Level level) {
        String sql = "INSERT INTO level(name, score) VALUES (?, ?);";

        try (Connection c = ConnectDB.connect();
             PreparedStatement pstmt = c.prepareStatement(sql);) {

            pstmt.setString(1, level.getName());
            pstmt.setInt(2, level.getScore());
            pstmt.executeUpdate();

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Level> selectAll() {
        List<Level> levelsList = new ArrayList<>();
        String sql = "SELECT * FROM `level`";

        try (Connection c = ConnectDB.connect();
             Statement stmt  = c.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            while (rs.next()) {
                Level level = new Level(rs.getString("name"), rs.getInt("score"));
                levelsList.add(level);
            }
        } catch (ClassNotFoundException|SQLException e) {
            System.out.println("dupa");
        }

        return levelsList;
    }

    public void deleteLevel(Level level) {
        String query;

        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement()) {

            query = String.format("DELETE FROM `level` WHERE name = '%s'; ",
                    level.getName());

            stmt.executeUpdate(query);

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateLevel(Level level, String preUpdateName) {
        String sql = "UPDATE level SET name = ? , "
                + "score = ? "
                + "WHERE name = ?";

        try (Connection c = ConnectDB.connect();
             PreparedStatement pstmt = c.prepareStatement(sql);) {

            pstmt.setString(1, level.getName());
            pstmt.setInt(2, level.getScore());
            pstmt.setString(3, preUpdateName);
            pstmt.executeUpdate();

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Level selectLevel(String levelName) {
        String sql = String.format("SELECT * FROM `level` WHERE name = '%s'; ",
                levelName);
        Level level = null;

        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            level = new Level(rs.getString("name"), rs.getInt("score"));

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }

        return level;
    }

    public Level getStudentLevel(Integer totalEarnings) {
        String sql = String.format("SELECT * FROM `level` WHERE score >= '%s' LIMIT 1; ",
                totalEarnings);
        Level level = null;

        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            level = new Level(rs.getString("name"), rs.getInt("score"));

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }

        return level;
    }
}
