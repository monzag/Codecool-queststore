package com.codecool.jlamas.models.account;

import com.codecool.jlamas.models.accountdata.Login;
import com.codecool.jlamas.models.accountdata.Mail;
import com.codecool.jlamas.models.accountdata.Password;
import com.codecool.jlamas.models.accountdata.Wallet;


public class Student extends Codecooler {

    private Integer classId;
    private Integer teamId;
    private Wallet wallet;

    public Student(){

    }

    public Student(Login login, Password password, Mail email, String name, String surname, Wallet wallet) {

        super(login, password, email, name, surname);
        this.classId = null;
        this.teamId = null;
        this.wallet = wallet;

    }

    public Student(Login login, Password password, Mail email, String name, String surname, Integer classId, Wallet wallet) {

        super(login, password, email, name, surname);
        this.classId = classId;
        this.teamId = null;
        this.wallet = wallet;

    }

    public Student(Login login, Password password, Mail email, String name, String surname, Integer classId, Integer teamId, Wallet wallet) {

        super(login, password, email, name, surname);
        this.classId = classId;
        this.teamId = teamId;
        this.wallet = wallet;

    }

    public Integer getClassId() {
        return this.classId;
    }
    public Integer getTeamId() {
        return this.teamId;
    }

    public Wallet getWallet() {
        return this.wallet;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
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


}
