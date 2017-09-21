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
        this.classId = null;
    }

    public Mentor(Login login, Password password, Mail email, String name, String surname, String classTag) {
        super(login, password, email, name, surname);
        this.classTag = classTag;
    }

    public Integer getClassTag() {
        return this.classId;
    }

    public void setClassTag(Integer classId) {
        this.classId = classId;
    }

    public String toString() {
        return super.toString() + "class : " + this.classTag + "\n";
    }


}
