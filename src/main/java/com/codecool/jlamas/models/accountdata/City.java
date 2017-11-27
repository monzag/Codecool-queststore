package com.codecool.jlamas.models.accountdata;

public class City {

    private Integer id;
    private String name;
    private String shortName;

    public City(Integer id, String name, String shortName) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
    }

    public void setID(Integer id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Integer getID() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public String getShortName() {
        return this.shortName;
    }
}
