package com.codecool.jlamas.models.account;

import java.awt.GradientPaint;

import com.codecool.jlamas.models.accountdata.Group;
import com.codecool.jlamas.models.accountdata.Login;
import com.codecool.jlamas.models.accountdata.Mail;
import com.codecool.jlamas.models.accountdata.Password;


public class Mentor extends Codecooler {

    private Group group;

    public Mentor() {

    }

    public Mentor(Login login, Password password, Mail email, String name, String surname) {
        super(login, password, email, name, surname);
        this.group = null;
    }

    public Mentor(Login login, Password password, Mail email, String name, String surname, Group group) {
        super(login, password, email, name, surname);
        this.group = group;
    }

    public Group getGroup() {
        return this.group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String toString() {
        return super.toString() + "class : " + this.group + "\n";
    }


}
