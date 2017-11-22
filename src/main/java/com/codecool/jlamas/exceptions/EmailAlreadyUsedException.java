package com.codecool.jlamas.exceptions;

public class EmailAlreadyUsedException extends InvalidUserDataException {
    public EmailAlreadyUsedException(String message) {
        super(message);
    }

    public EmailAlreadyUsedException() {
        super("This email was already used");
    }
}
