package com.codecool.jlamas.exceptions;


public class InvalidCityNameException extends InvalidCityDataException {

    public InvalidCityNameException(String message) {
        super(message);
    }

    public InvalidCityNameException() {
        super("This city name is already in database");
    }
}