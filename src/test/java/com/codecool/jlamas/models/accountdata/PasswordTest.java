package com.codecool.jlamas.models.accountdata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordTest {

    private Password passwrd;

    @BeforeEach
    void setUp() {
        this.passwrd = new Password("abdcefgh");
    }

    @Test
    void testEmptyPasswordConstructor() {
        new Password();
    }

    @Test
    void isValid7Characters() {
        String password = "abcdefg";
        assertTrue(passwrd.isValid(password));
    }

    @Test
    void isInvalid4Characters() {
        String password = "abcd";
        assertFalse(passwrd.isValid(password));
    }
}