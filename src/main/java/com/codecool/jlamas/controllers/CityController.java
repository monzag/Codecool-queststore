package com.codecool.jlamas.controllers;

import com.codecool.jlamas.database.CityDAO;
import com.codecool.jlamas.models.accountdata.City;

import java.util.ArrayList;


public class CityController {

    private CityDAO dao;

    public CityController() {
        this.dao = new CityDAO();
    }

    public ArrayList<City> getAll() {
        return this.dao.requestAll();
    }

}
