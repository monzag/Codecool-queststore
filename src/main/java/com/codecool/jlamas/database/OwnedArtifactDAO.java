package com.codecool.jlamas.database;

import com.codecool.jlamas.models.account.Student;
import com.codecool.jlamas.models.artifact.Artifact;

import java.sql.*;
import java.util.ArrayList;

public class OwnedArtifactDAO {

    public OwnedArtifactDAO() {

    }

    public boolean insert(Student student, Artifact artifact) {
        String sql = "INSERT INTO owned_artifact(artifact_name, owner_name) VALUES (?, ?);";

        try (Connection c = ConnectDB.connect();
             PreparedStatement pstmt = c.prepareStatement(sql);) {

            pstmt.setString(1, artifact.getName());
            pstmt.setString(2, student.getLogin().getValue());
            pstmt.executeUpdate();

            return true;

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public ArrayList<Artifact> requestAllBy(Student student) {
        ArrayList<Artifact> artifactList = new ArrayList<>();
        String sql = "SELECT name, price, description FROM artifact "
                + "INNER JOIN owned_artifact ON owned_artifact.artifact_name = artifact.name "
                + "WHERE owned_artifact.owner_name = ?";

        try (Connection c = ConnectDB.connect();
             PreparedStatement pstmt = c.prepareStatement(sql);) {

            pstmt.setString(1, student.getLogin().getValue());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Artifact artifact = new Artifact(rs.getString("name"), rs.getInt("price"), rs.getString("description"));
                artifactList.add(artifact);
            }

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }

        return artifactList;
    }

    public boolean delete(Artifact artifact, Student student) {

        String query = "DELETE FROM owned_artifact WHERE artifact_name = ? AND owner_name = ?";

        try (Connection c = ConnectDB.connect();
             PreparedStatement pstmt = c.prepareStatement(query)) {
            pstmt.setString(1, artifact.getName());
            pstmt.setString(2, student.getLogin().getValue());
            pstmt.executeUpdate();
        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
