package com.codecool.jlamas.database;

import com.codecool.jlamas.models.accountdata.Team;

import java.sql.*;
import java.util.ArrayList;

public class TeamDAO {

    public TeamDAO() {

    }

    public ArrayList<Team> getAll() {

        ArrayList<Team> teams = new ArrayList<>();
        String query = "SELECT * FROM team";

        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement()) {

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                teams.add(new Team(rs.getInt("id"), rs.getString("name")));
            }

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }
        return teams;
    }

    public Team get(Integer id) {

        String query = String.format("SELECT * FROM team WHERE id = %d", id);

        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement()) {

            ResultSet rs = stmt.executeQuery(query);
            return new Team(id, rs.getString("name"));

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Team get(String name) {

        String query = String.format("SELECT * FROM team WHERE name = %s", name);

        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement()) {

            ResultSet rs = stmt.executeQuery(query);
            return new Team(rs.getInt("id"), name);

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
