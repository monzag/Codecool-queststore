package com.codecool.jlamas.models.account;

import com.codecool.jlamas.models.accountdata.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;

class AdminTest {

    Admin admin;
    Login login;
    Password password;
    Mail mail;
    String name;
    String surname;

    @BeforeEach
    public void setUp() {
        login = spy(Login.class);
        password = spy(Password.class);
        mail = spy(Mail.class);
        name = "testAdminName";
        surname = "testAdminSurname";

        admin = new Admin(login, password, mail, name, surname);
    }

    @Test
    void testEmptyConstructor() {
        assertNotNull(new Admin());
    }

    @Test
    void testConstructorWithAttributes() {
        assertNotNull(new Admin(login, password, mail, name, surname));
    }
    
    @Test
    void testGetLogin() {
        Login actual = admin.getLogin();
        assertEquals(login, actual);
    }

    @Test
    void testSetLogin() {
        Login testLogin = new Login("testLogin");
        admin.setLogin(testLogin);
        Login actual = admin.getLogin();
        assertEquals(testLogin, actual);
    }

    @Test
    void testGetPassword() {
        Password actual = admin.getPassword();
        assertEquals(password, actual);
    }

    @Test
    void testSetPassword() {
        Password testPassword = new Password("testPassword");
        admin.setPassword(testPassword);
        Password actual = admin.getPassword();
        assertEquals(testPassword, actual);
    }

    @Test
    void testGetEmail() {
        Mail actual = admin.getEmail();
        assertEquals(mail, actual);
    }

    @Test
    void testSetEmail() {
        Mail testEmail = new Mail("testMail");
        admin.setEmail(testEmail);
        Mail actual = admin.getEmail();
        assertEquals(testEmail, actual);
    }

    @Test
    void testGetName() {
        String actual = admin.getName();
        assertEquals(name, actual);
    }

    @Test
    void testSetName() {
        String testName = "testName";
        admin.setName(testName);
        String actual = admin.getName();
        assertEquals(testName, actual);
    }

    @Test
    void testGetSurname() {
        String actual = admin.getSurname();
        assertEquals(surname, actual);
    }

    @Test
    void testSetSurname() {
        String testSurname = "testSurname";
        admin.setSurname(testSurname);
        String actual = admin.getSurname();
        assertEquals(testSurname, actual);
    }
}