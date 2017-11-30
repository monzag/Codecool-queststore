package com.codecool.jlamas.exceptions;

public class ArtifactNameAlreadyUsedException extends Exception {

    public ArtifactNameAlreadyUsedException(String message) {
        super(message);
    }

    public ArtifactNameAlreadyUsedException() {
        super("This artifact name is already used");
    }
}
