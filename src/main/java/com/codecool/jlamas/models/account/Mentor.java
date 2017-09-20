package com.codecool.jlamas.models.account;

import com.codecool.jlamas.models.accountdata.Login;
import com.codecool.jlamas.models.accountdata.Mail;
import com.codecool.jlamas.models.accountdata.Password;


public class Mentor extends Codecooler {

    private Integer classId;

    public Mentor() {

    }

    public Mentor(Login login, Password password, Mail email, String name, String surname) {
        super(login, password, email, name, surname);
        this.classId = 0;
    }

    public Mentor(Login login, Password password, Mail email, String name, String surname, Integer classId) {
        super(login, password, email, name, surname);
        this.classId = classId;
    }

    public Integer getClassId() {
        return this.classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String toString() {
        return super.toString() + "class : " + this.classId + "\n";
    }


}
