package com.codecool.jlamas.database;

import com.codecool.jlamas.models.accountdata.Team;

import javax.xml.transform.Result;
import java.sql.*;

public class TeamDAO {

    public TeamDAO() {

    }

    public Team get(Integer id) {

        String query = String.format("SELECT * FROM team WHERE id = %d", id);

        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement()) {

            ResultSet rs = stmt.executeQuery(query);
            Team team = new Team(id, rs.getString("name"));
            return team;

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


}
