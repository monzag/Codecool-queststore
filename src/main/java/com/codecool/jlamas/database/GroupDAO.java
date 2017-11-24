package com.codecool.jlamas.database;

import java.sql.*;
import java.util.ArrayList;

import com.codecool.jlamas.models.accountdata.City;
import com.codecool.jlamas.models.accountdata.Group;

public class GroupDAO {

    public GroupDAO() {

    }

    public void insert(Group group) {
        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement()) {

            String query = String.format("INSERT INTO `group` VALUES (null, '%s', %d, %d);",
                    group.getCity().getName(), group.getYear(), group.getNumber());

            stmt.executeQuery(query);

        } catch(ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Group> selectAll() {
        ArrayList<Group> groups = new ArrayList<>();
        String query = "SELECT * FROM `group`;";

        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery(query)){

            CityDAO cityDAO = new CityDAO();

            while (rs.next()) {
                Group group = new Group();
                City city = cityDAO.get(rs.getString("city_name"));

                group.setID(rs.getInt("id"));
                group.setCity(city);
                group.setYear(rs.getInt("year"));
                group.setNumber(rs.getInt("number"));

                groups.add(group);
            }
        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }

        return groups;
    }

    public void update(Group group) {
        String query = "";
        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement();) {

            query = String.format("UPDATE `group` SET city_name = '%s', year = %d, number = %d  WHERE group_id = %d; ",
                    group.getCity().getName(),
                    group.getYear(),
                    group.getNumber());

            stmt.executeUpdate(query);

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public Group getGroup(Integer id) {
        String query = String.format("SELECT * FROM `group` WHERE id = %d;", id);

        Group group = null;
        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery(query)){

             CityDAO cityDAO = new CityDAO();
             City city = cityDAO.get(rs.getString("city_name"));

             group = new Group();
             group.setID(rs.getInt("id"));
             group.setCity(city);
             group.setYear(rs.getInt("year"));
             group.setNumber(rs.getInt("number"));

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }
        return group;
    }

    public void delete(Group group) {
        String query = "DELETE FROM `group` WHERE id = " + group.getID() + ";";

        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement();) {
             stmt.executeUpdate(query);
        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}