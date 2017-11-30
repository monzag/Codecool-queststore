package com.codecool.jlamas.exceptions;

public class InvalidRepeatedPasswordException extends Exception {

    public InvalidRepeatedPasswordException() { super("Repeated password doesn't match");}
}
