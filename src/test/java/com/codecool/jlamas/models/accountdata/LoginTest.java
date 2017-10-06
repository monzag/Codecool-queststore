package com.codecool.jlamas.models.accountdata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginTest {

    private Login login;

    @BeforeEach
    void setUp() {
        this.login = new Login("login");
    }

    @Test
    void testEmptyConstructor() {
        new Login();
    }

    @Test
    void isValid() {
        assertTrue(login.isValid(this.login.value));
    }

    @Test
    void isLoginUnique() {
    }

}