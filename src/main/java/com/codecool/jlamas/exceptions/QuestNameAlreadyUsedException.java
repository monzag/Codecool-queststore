package com.codecool.jlamas.exceptions;

public class QuestNameAlreadyUsedException extends Exception {

    public QuestNameAlreadyUsedException(String message) {
        super(message);
    }

    public QuestNameAlreadyUsedException() {
        super("This quest name is already used");
    }
}
