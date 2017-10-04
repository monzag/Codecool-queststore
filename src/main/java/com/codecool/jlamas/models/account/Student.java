package com.codecool.jlamas.models.account;

import com.codecool.jlamas.models.accountdata.Group;
import com.codecool.jlamas.models.accountdata.Login;
import com.codecool.jlamas.models.accountdata.Mail;
import com.codecool.jlamas.models.accountdata.Password;
import com.codecool.jlamas.models.accountdata.Wallet;


public class Student extends Codecooler {

    private Group group;
    private Integer teamId;
    private Wallet wallet;

    public Student(){

    }

    public Student(Login login, Password password, Mail email, String name, String surname, Wallet wallet) {

        super(login, password, email, name, surname);
        this.group = null;
        this.teamId = null;
        this.wallet = wallet;

    }

    public Student(Login login, Password password, Mail email, String name, String surname, Group group, Wallet wallet) {

        super(login, password, email, name, surname);
        this.group = group;
        this.teamId = null;
        this.wallet = wallet;

    }

    public Student(Login login, Password password, Mail email, String name, String surname, Group group, Integer teamId, Wallet wallet) {

        super(login, password, email, name, surname);
        this.group = group;
        this.teamId = teamId;
        this.wallet = wallet;

    }

    public Group getGroup() {
        return this.group;
    }
    public Integer getTeamId() {
        return this.teamId;
    }

    public Wallet getWallet() {
        return this.wallet;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public boolean pay(Integer amount) {
        // try to take money of wallet if suceed return true
        return this.wallet.take(amount);
    }

    public void earn(Integer amount) {
        this.wallet.put(amount);
    }

    public String toString() {
        return super.toString() + "Group: " + group.getName();
    }


}
