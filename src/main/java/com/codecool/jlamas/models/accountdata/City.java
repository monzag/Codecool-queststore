package com.codecool.jlamas.models.accountdata;

public class City {

    private String name;
    private String shortName;

    public City(String name, String shortName) {
        this.name = name;
        this.shortName = shortName;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getName() {
        return this.name;
    }
    public String getShortName() {
        return this.shortName;
    }
}
