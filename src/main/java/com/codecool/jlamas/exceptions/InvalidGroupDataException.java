package com.codecool.jlamas.exceptions;

public class InvalidGroupDataException extends Exception {

    public InvalidGroupDataException(String message) {
        super(message);
    }

    public InvalidGroupDataException() {
        super("This group already exist");
    }
}
