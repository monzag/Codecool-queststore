package com.codecool.jlamas.exceptions;

public class NotMatchingPasswordException extends Exception {

    public NotMatchingPasswordException() { super("Invalid old password"); }
}
