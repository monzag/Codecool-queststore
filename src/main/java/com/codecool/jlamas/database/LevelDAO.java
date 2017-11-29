package com.codecool.jlamas.database;

import com.codecool.jlamas.models.level.Level;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
