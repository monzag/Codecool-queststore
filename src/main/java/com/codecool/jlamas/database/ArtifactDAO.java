package com.codecool.jlamas.database;


import com.codecool.jlamas.models.artifact.Artifact;

import java.sql.*;
import java.util.ArrayList;

public class ArtifactDAO {

    public ArtifactDAO() {

    }

    public ArrayList<Artifact> requestAll(){
        ArrayList<Artifact> artifactList = new ArrayList<>();
        String sql = "SELECT name, price, description FROM artifact";

        try (Connection c = ConnectDB.connect();
             Statement stmt  = c.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            while (rs.next()) {
                Artifact artifact = new Artifact(rs.getString("name"), rs.getInt("price"), rs.getString("description"));
                artifactList.add(artifact);
            }
        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }

        return artifactList;
    }

}
