package com.codecool.jlamas.database;

import com.codecool.jlamas.models.level.Level;
import com.codecool.jlamas.models.quest.Quest;

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
        String sql = "SELECT name, score FROM level";

        try (Connection c = ConnectDB.connect();
             Statement stmt  = c.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            while (rs.next()) {
                Level level = new Level(rs.getString("name"), rs.getInt("score"));
                levelsList.add(level);
            }
        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }

        return levelsList;
    }

}
