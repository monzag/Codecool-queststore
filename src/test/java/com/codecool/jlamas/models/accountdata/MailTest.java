package com.codecool.jlamas.models.accountdata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MailTest {

    private Mail mail;

    @BeforeEach
    void setUp() {
        this.mail = new Mail("email@gmail.com");
    }

    @Test
    void testEmptyConstructor() {
        new Mail();
    }

    @Test
    void testIsValid1() {
        String validMail = "costam@gmail.com";
        assertTrue(mail.isValid(validMail));
    }

    @Test
    void testIsValid2() {
        String invalidMail = "zle";
        assertFalse(mail.isValid(invalidMail));
    }

}