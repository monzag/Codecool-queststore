package com.codecool.jlamas.controllers;

import com.codecool.jlamas.database.CityDAO;
import com.codecool.jlamas.models.accountdata.City;

import java.util.ArrayList;
import java.util.Calendar;


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

    public ArrayList<Integer> getYears() {
        ArrayList<Integer> years = new ArrayList<Integer>();

        Integer year = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = year-PAST_YEARS; i < year+NEXT_YEARS; i++) {
            years.add(i);
        }
        return years;
    }

}
