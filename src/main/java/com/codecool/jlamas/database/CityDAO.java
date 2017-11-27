package com.codecool.jlamas.database;

import com.codecool.jlamas.models.accountdata.City;

import java.sql.*;
import java.util.ArrayList;


public class CityDAO {

    public CityDAO() {

    }

    public ArrayList<City> requestAll(){
        ArrayList<City> cities = new ArrayList<>();
        String query = "SELECT * FROM `city`";

        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery(query)){

            while (rs.next()) {

                City city = new City(rs.getInt("id"), rs.getString("name"), rs.getString("short"));
                cities.add(city);
            }
        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }
        return cities;
    }

    public boolean insert(City city) {
        String query = String.format("INSERT INTO `city` VALUES (%s, '%s', '%s');",
                null, city.getName(), city.getShortName());

        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement()) {

            stmt.executeQuery(query);

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());

            return false;
        }
        return true;

    }
    public void update(City city, String newName, String newShortName) {
        String query = String.format("UPDATE city SET id = %s, name = '%s', short = '%s' WHERE name = '%s'",
                newName, newShortName, city.getName());

        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement()) {

            stmt.executeUpdate(query);

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public City get(String name) {
        String query = String.format("SELECT * FROM `city` WHERE name = '%s'; ",
                name);
        City city = null;

        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            city = new City(rs.getInt("id"), rs.getString("name"), rs.getString("short"));

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }
        return city;
    }

    public void delete(City city) {
        String query;

        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement()) {

            query = String.format("DELETE FROM `city` WHERE name = '%s'; ",
                    city.getName());

            stmt.executeUpdate(query);

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}