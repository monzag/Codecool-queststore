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

    public Team get(String name) {

        String query = String.format("SELECT * FROM team WHERE name = %s", id);

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

    public void insert(Team team) {
        String query = "INSERT INTO team VALUES (NULL, ?)";

        try (Connection c = ConnectDB.connect();
             PreparedStatement pstmt = c.prepareStatement(query)) {

            pstmt.setString(1, team.getName());
            pstmt.executeUpdate();

        } catch(ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(Team team) {
        String query = "UPDATE team SET name = ?";

        try (Connection c = ConnectDB.connect();
             PreparedStatement pstmt = c.prepareStatement(query)) {

            pstmt.setString(1, team.getName());
            pstmt.executeUpdate();

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(Integer id) {
        String query = "DELETE FROM team WHERE id = ?";

        try (Connection c = ConnectDB.connect();
             PreparedStatement pstmt = c.prepareStatement(query)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
