package com.codecool.jlamas.database;

import com.codecool.jlamas.exceptions.InvalidCityDataException;
import com.codecool.jlamas.exceptions.InvalidCityNameException;
import com.codecool.jlamas.exceptions.InvalidCityShortNameException;
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

    public void insert(City city) throws InvalidCityDataException {
        String query = String.format("INSERT INTO `city` VALUES (null, '%s', '%s');",
                city.getName(), city.getShortName());

        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement()) {

            stmt.executeQuery(query);

        } catch(ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(City city) throws InvalidCityDataException {
        String query = String.format("UPDATE city SET name = '%s', short = '%s' WHERE id = %s;",
                city.getName(), city.getShortName(), city.getID());

        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement()) {

            stmt.executeUpdate(query);

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
            if (e.getMessage().contains("city.name")) {
                throw new InvalidCityNameException();
            }
            else {
                throw new InvalidCityShortNameException();
            }
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

    public City get(Integer id) {
        String query = String.format("SELECT * FROM `city` WHERE id = %s; ",
                id);
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

    public void delete(Integer id) {
        String query;

        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement()) {

            query = String.format("DELETE FROM `city` WHERE id = %s; ", id);

            stmt.executeUpdate(query);

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}