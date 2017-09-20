package com.codecool.jlamas.models.account;

import com.codecool.jlamas.models.accountdata.Mail;
import com.codecool.jlamas.models.accountdata.Login;
import com.codecool.jlamas.models.accountdata.Password;


public class Admin extends Codecooler {

    public Admin() {

    }

    public Admin(Login login, Password password, Mail email, String name, String surname) {
        super(login, password, email, name, surname);
    }


}
