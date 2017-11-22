package com.codecool.jlamas.models.account;

import com.codecool.jlamas.models.accountdata.Login;
import com.codecool.jlamas.models.accountdata.Mail;
import com.codecool.jlamas.models.accountdata.Password;


public class Codecooler {

    protected Login login;
    protected Password password;
    protected Mail email;
    protected String name;
    protected String surname;

    public Codecooler() {

    }

    public Codecooler(Login login, Password password, Mail email, String name, String surname) {

        this.login = login;
        this.password = password;
        this.email = email;
        this.name = name;
        this.surname = surname;

    }

    public Login getLogin() {
        return this.login;
    }
    public Password getPassword() {
        return this.password;
    }
    public Mail getEmail() {
        return this.email;
    }
    public String getName() {
        return this.name;
    }
    public String getSurname() {
        return this.surname;
    }

    public void setLogin(Login login) {
        this.login = login;
    }
    public void setPassword(Password password) {
        this.password = password;
    }
    public void setEmail(Mail email) {
        this.email = email;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void correctNames() {
        this.name = this.capitalize(this.name);
        this.surname = this.capitalize(this.surname);
    }

    private static String capitalize(String name) {
        return name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
    }


}
