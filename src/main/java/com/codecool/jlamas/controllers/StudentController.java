package com.codecool.jlamas.controllers;

import com.codecool.jlamas.exceptions.InvalidUserDataException;
import com.codecool.jlamas.models.account.Student;
import com.codecool.jlamas.models.accountdata.Login;
import com.codecool.jlamas.models.accountdata.Mail;
import com.codecool.jlamas.models.accountdata.Password;
import com.codecool.jlamas.models.accountdata.Wallet;

public class StudentController {

    public StudentController() {

    }

    public void displayAll() {
        ;
    }

    public void addStudent() {
        try {
            String name = studentView.getName();
            String surname = studentView.getSurname();
            Mail email = studentView.getMail();

            Login login = new Login("student");
            Password password = new Password("student");
            Wallet wallet = new Wallet();
            Student student = new Student(login, password, email, name, surname, wallet);
            studentDao.insert(student);

        } catch (InvalidUserDataException e) {

        }
    }

    
}