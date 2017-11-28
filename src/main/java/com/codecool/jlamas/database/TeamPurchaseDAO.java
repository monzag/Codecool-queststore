package com.codecool.jlamas.database;

import com.codecool.jlamas.models.account.Student;
import com.codecool.jlamas.models.artifact.Artifact;

import java.sql.*;

public class TeamPurchaseDAO {

    public TeamPurchaseDAO() {}

    public void insert(Artifact artifact, Student student, Integer price) {
        String sql = "INSERT INTO team_purchase VALUES(?, ?, ?, ?)";

        try (Connection c = ConnectDB.connect();
             PreparedStatement pstmt = c.prepareStatement(sql);) {

            pstmt.setString(1, artifact.getName());
            pstmt.setString(2, student.getLogin().getValue());
            pstmt.setInt(3, price);
            pstmt.setInt(4, 0);
            pstmt.executeUpdate();

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    
}
