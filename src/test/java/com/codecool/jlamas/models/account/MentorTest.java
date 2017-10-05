package com.codecool.jlamas.models.account;

import com.codecool.jlamas.models.accountdata.Group;
import com.codecool.jlamas.models.accountdata.Login;
import com.codecool.jlamas.models.accountdata.Mail;
import com.codecool.jlamas.models.accountdata.Password;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;


import static org.junit.jupiter.api.Assertions.*;

class MentorTest {

    Mentor mentor;
    Login login;
    Password password;
    Mail mail;
    String name;
    String surname;
    Group group;

    @BeforeEach
    public void setUp() {
        login = spy(Login.class);
        password = spy(Password.class);
        mail = spy(Mail.class);
        name = "testMentorName";
        surname = "testMentorSurname";
        group = spy(Group.class);
        group.setName("2017.1");
        

        mentor = new Mentor(login, password, mail,name, surname, group);
    }

    @Test
    void testEmptyConstructor() {
        assertNotNull(new Mentor());
    }

    @Test
    void testConstructorWithoutGroup() {
        
        assertNotNull(new Mentor(login, password, mail, name, surname));
    }

    @Test
    void testConstructorWithGroup() {
        
        assertNotNull(new Mentor(login, password, mail, name, surname, group));
    }

    @Test
    void getGroup() {
        Group actual = mentor.getGroup();
        assertEquals(group, actual);
    }

    @Test
    void setGroup() {
        Group testGroup = spy(Group.class);
        mentor.setGroup(testGroup);
        Group actual = mentor.getGroup();
        assertEquals(testGroup, actual);
    }

    @Test
    void testtoString() {
        String testToString = "\ntestMentorName testMentorSurname" +
                            "\n================================\n" +
                            "class : 2017.1\n";

        String actual = mentor.toString();
        assertEquals(testToString, actual);
    }

    @Test
    void testGetLogin() {
        Login actual = mentor.getLogin();
        assertEquals(login, actual);
    }

    @Test
    void testSetLogin() {
        Login testLogin = new Login("testLogin");
        mentor.setLogin(testLogin);
        Login actual = mentor.getLogin();
        assertEquals(testLogin, actual);
    }

    @Test
    void testGetPassword() {
        Password actual = mentor.getPassword();
        assertEquals(password, actual);
    }

    @Test
    void testSetPassword() {
        Password testPassword = new Password("testPassword");
        mentor.setPassword(testPassword);
        Password actual = mentor.getPassword();
        assertEquals(testPassword, actual);
    }

    @Test
    void testGetEmail() {
        Mail actual = mentor.getEmail();
        assertEquals(mail, actual);
    }

    @Test
    void testSetEmail() {
        Mail testEmail = new Mail("testMail");
        mentor.setEmail(testEmail);
        Mail actual = mentor.getEmail();
        assertEquals(testEmail, actual);
    }

    @Test
    void testGetName() {
        String actual = mentor.getName();
        assertEquals(name, actual);
    }

    @Test
    void testSetName() {
        String testName = "testName";
        mentor.setName(testName);
        String actual = mentor.getName();
        assertEquals(testName, actual);
    }

    @Test
    void testGetSurname() {
        String actual = mentor.getSurname();
        assertEquals(surname, actual);
    }

    @Test
    void testSetSurname() {
        String testSurname = "testSurname";
        mentor.setSurname(testSurname);
        String actual = mentor.getSurname();
        assertEquals(testSurname, actual);
    }
}