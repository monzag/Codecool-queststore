package com.codecool.jlamas.models.account;

import com.codecool.jlamas.models.accountdata.*;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    Student student;
    Login login;
    Password password;
    Mail mail;
    String name;
    String surname;
    Group group;
    Integer teamId;
    Wallet wallet;

    @BeforeEach
    public void setUp() {
        login = spy(Login.class);
        password = spy(Password.class);
        mail = spy(Mail.class);
        name = "testStudentName";
        surname = "testStudentSurname";
        group = spy(Group.class);
        group.setName("testGroup");
        teamId = 1;
        wallet = spy(Wallet.class);
        wallet.setBalance(100);

        student = new Student(login, password, mail,name, surname, group, teamId, wallet);
    }

    @Test
    void testEmptyConstructor() {
        assertNotNull(new Student());
    }

    @Test
    void testConstructorWithoutTeamId() {

        assertNotNull(new Student(login, password, mail, name, surname, group, wallet));
    }

    @Test
    void testConstructorWithTeamId() {

        assertNotNull(new Student(login, password, mail, name, surname, group, teamId, wallet));
    }
    @Test
    void getGroup() {
        Group actual = student.getGroup();
        assertEquals(group, actual);
    }

    @Test
    void setGroup() {
        Group testGroup = spy(Group.class);
        student.setGroup(testGroup);
        Group actual = student.getGroup();
        assertEquals(testGroup, actual);
    }

    @Test
    void getTeamId() {
        Integer actual = student.getTeamId();
        assertEquals(teamId, actual);
    }

    @Test
    void setTeamId() {
        Integer testTeamId = 2;
        student.setTeamId(testTeamId);
        Integer actual = student.getTeamId();
        assertEquals(testTeamId, actual);
    }

    @Test
    void getWallet() {
        Wallet actual = student.getWallet();
        assertEquals(wallet, actual);
    }

    @Test
    void setWallet() {
        Wallet testWallet = spy(Wallet.class);
        student.setWallet(testWallet);
        Wallet actual = student.getWallet();
        assertEquals(testWallet, actual);
    }

    @Test
    void pay() {
        Boolean testPay = student.pay(10);
        assertTrue(testPay);
    }

    @Test
    void earn() {
        Integer addingValue = 10;
        Integer balance =  wallet.getBalance();
        student.earn(addingValue);
        Integer newBalance = wallet.getBalance();
        Integer result = balance + addingValue;
        assertEquals(newBalance, result);
    }

    @Test
    void testToString() {
        String testToString = "\ntestStudentName testStudentSurname" +
                "\n================================\n" +
                "Group: testGroup";

        String actual = student.toString();
        assertEquals(testToString, actual);
    }

    @Test
    void testGetLogin() {
        Login actual = student.getLogin();
        assertEquals(login, actual);
    }

    @Test
    void testSetLogin() {
        Login testLogin = new Login("testLogin");
        student.setLogin(testLogin);
        Login actual = student.getLogin();
        assertEquals(testLogin, actual);
    }

    @Test
    void testGetPassword() {
        Password actual = student.getPassword();
        assertEquals(password, actual);
    }

    @Test
    void testSetPassword() {
        Password testPassword = new Password("testPassword");
        student.setPassword(testPassword);
        Password actual = student.getPassword();
        assertEquals(testPassword, actual);
    }

    @Test
    void testGetEmail() {
        Mail actual = student.getEmail();
        assertEquals(mail, actual);
    }

    @Test
    void testSetEmail() {
        Mail testEmail = new Mail("testMail");
        student.setEmail(testEmail);
        Mail actual = student.getEmail();
        assertEquals(testEmail, actual);
    }

    @Test
    void testGetName() {
        String actual = student.getName();
        assertEquals(name, actual);
    }

    @Test
    void testSetName() {
        String testName = "testName";
        student.setName(testName);
        String actual = student.getName();
        assertEquals(testName, actual);
    }

    @Test
    void testGetSurname() {
        String actual = student.getSurname();
        assertEquals(surname, actual);
    }

    @Test
    void testSetSurname() {
        String testSurname = "testSurname";
        student.setSurname(testSurname);
        String actual = student.getSurname();
        assertEquals(testSurname, actual);
    }

}