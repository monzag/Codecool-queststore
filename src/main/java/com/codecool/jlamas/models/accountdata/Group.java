package com.codecool.jlamas.models.accountdata;

public class Group {

    private Integer id;
    private City city;
    private Integer year;
    private Integer number;

    public Group() {

    }

    public Group(City city, Integer year, Integer number) {
        this.city = city;
        this.year = year;
        this.number = number;
    }

    @Deprecated
    public void setName(String name) {

    }

    public void setID(Integer id) {
        this.id = id;
    }
    public void setCity(City city) {
        this.city = city;
    }
    public void setYear(Integer year) {
        this.year = year;
    }
    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getID() {
        return this.id;
    }
    public City getCity() {
        return this.city;
    }
    public Integer getYear() {
        return this.year;
    }
    public Integer getNumber() {
        return this.number;
    }

    public String getName() {
        return String.format("%s.%d.%d", this.city.getShortName(), this.year, this.number);
    }

    public boolean equals(Group other) {
        return this.city.equals(other.getCity()) && this.year.equals(other.getYear()) && this.number.equals(other.getNumber());
    }

}