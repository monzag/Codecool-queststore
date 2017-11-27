package com.codecool.jlamas.controllers;

import com.codecool.jlamas.database.CityDAO;
import com.codecool.jlamas.exceptions.InvalidCityDataException;
import com.codecool.jlamas.models.accountdata.City;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;


public class CityController {

    // decide which years beside current will be displayed
    private static final int PAST_YEARS = 1;
    private static final int NEXT_YEARS = 4;

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

    public ArrayList<Integer> getYears() {
        ArrayList<Integer> years = new ArrayList<Integer>();

        Integer year = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = year-PAST_YEARS; i < year+NEXT_YEARS; i++) {
            years.add(i);
        }
        return years;
    }

    public void editCityFromMap(Integer id, Map<String, String> inputs) throws InvalidCityDataException {
        City city = this.dao.get(id);
        city.setName(inputs.get("name"));
        city.setShortName(inputs.get("shortname"));

        this.dao.update(city);
    }

}
