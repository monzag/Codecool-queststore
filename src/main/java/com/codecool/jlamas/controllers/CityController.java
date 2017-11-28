package com.codecool.jlamas.controllers;

import com.codecool.jlamas.database.CityDAO;
import com.codecool.jlamas.exceptions.InvalidCityDataException;
import com.codecool.jlamas.exceptions.InvalidCityNameException;
import com.codecool.jlamas.exceptions.InvalidCityShortNameException;
import com.codecool.jlamas.models.accountdata.City;

import java.util.ArrayList;
import java.util.Map;


public class CityController {

    private CityDAO dao;

    public CityController() {
        this.dao = new CityDAO();
    }

    public ArrayList<City> getAll() {
        return this.dao.requestAll();
    }

    public City getCity(Integer id) {
        return this.dao.get(id);
    }

    public void removeCity(Integer id) {
        this.dao.delete(id);
    }

    public void editCityFromMap(Integer id, Map<String, String> inputs) throws InvalidCityDataException {
        String name = City.capitalizeName(inputs.get("name"));
        String shortName = inputs.get("shortname");

        City city = this.dao.get(id);
        if (!city.hasName(name)) {
            if (!this.isCityNameUnique(name)) {
                throw new InvalidCityNameException();
            }
        }
        if (!city.hasShortName(shortName)) {
            if (!this.isCityShortNameUnique(shortName)) {
                throw new InvalidCityShortNameException();
            }
        }

        city.setName(name);
        city.setShortName(shortName);
        this.dao.update(city);

    }

    public void createCityFromMap(Map<String, String> inputs) throws InvalidCityDataException {
        String name = City.capitalizeName(inputs.get("name"));
        String shortName = inputs.get("shortname");

        if (isNewCityDataUnique(name, shortName)) {
            City city = new City(null, name, shortName);
            this.dao.insert(city);
        }
    }

    private boolean isNewCityDataUnique(String name, String shortName) throws InvalidCityDataException {
        ArrayList<City> cities = this.dao.requestAll();

        for (City city : cities) {
            if (city.getName().equals(name)) {
                throw new InvalidCityNameException();
            }
            if (city.getShortName().equals(shortName)) {
                throw new InvalidCityShortNameException();
            }
        }
        return true;
    }

    private boolean isCityNameUnique(String name) {
        ArrayList<City> cities = this.dao.requestAll();

        for (City city : cities) {
            if (city.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }

    private boolean isCityShortNameUnique(String shortName) {
        ArrayList<City> cities = this.dao.requestAll();

        for (City city : cities) {
            if (city.getShortName().equals(shortName)) {
                return false;
            }
        }
        return true;
    }

}
