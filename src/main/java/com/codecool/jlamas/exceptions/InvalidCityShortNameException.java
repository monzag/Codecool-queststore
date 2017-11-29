package com.codecool.jlamas.exceptions;

public class InvalidCityShortNameException extends InvalidCityDataException {


    public InvalidCityShortNameException(String message) {
        super(message);
    }

    public InvalidCityShortNameException() {
        super("This city short name is already used");
    }
}
