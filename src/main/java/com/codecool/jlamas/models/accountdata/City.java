package com.codecool.jlamas.models.accountdata;

import java.util.ArrayList;
import java.util.Arrays;

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

    public boolean equals(City other) {
        return this.name.equals(other.getName()) && this.shortName.equals(other.getShortName());
    }

    public boolean hasName(String name) {
        return this.name .equals(name);
    }

    public boolean hasShortName(String shortName) {
        return this.shortName .equals(shortName);
    }

    public static String capitalizeName(String name) {
        ArrayList<String> words = new ArrayList<String>(Arrays.asList(name.split(" ")));
        for (int i = 0; i < words.size(); i++) {
            words.set(i, capitalizeWord(words.get(i)));
        }
        return String.join(" ", words);
    }

    private static String capitalizeWord(String word) {
        return word.substring(0,1).toUpperCase() + word.substring(1).toLowerCase();
    }
}
