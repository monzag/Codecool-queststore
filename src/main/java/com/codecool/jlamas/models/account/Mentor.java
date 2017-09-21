package com.codecool.jlamas.models.account;

import com.codecool.jlamas.models.accountdata.Login;
import com.codecool.jlamas.models.accountdata.Mail;
import com.codecool.jlamas.models.accountdata.Password;


public class Mentor extends Codecooler {

    private String classTag;

    public Mentor() {

    }

    public Mentor(Login login, Password password, Mail email, String name, String surname) {
        super(login, password, email, name, surname);
        this.classTag = null;
    }

    public Mentor(Login login, Password password, Mail email, String name, String surname, String classTag) {
        super(login, password, email, name, surname);
        this.classTag = classTag;
    }

    public String getClassTag() {
        return this.classTag;
    }

    public void setClassTag(String classTag) {
        this.classTag = classTag;
    }

    public String toString() {
        return super.toString() + "class : " + this.classTag + "\n";
    }


}
