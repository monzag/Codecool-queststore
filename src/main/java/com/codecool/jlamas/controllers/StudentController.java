package com.codecool.jlamas.controllers;

import java.util.ArrayList;
import java.util.Random;

import com.codecool.jlamas.database.StudentDAO;
import com.codecool.jlamas.exceptions.InvalidUserDataException;
import com.codecool.jlamas.models.account.Student;
import com.codecool.jlamas.models.accountdata.Login;
import com.codecool.jlamas.models.accountdata.Mail;
import com.codecool.jlamas.models.accountdata.Password;
import com.codecool.jlamas.models.accountdata.Wallet;
import com.codecool.jlamas.views.StudentView;

public class StudentController {

    private StudentView studentView = new StudentView();
    private StudentDAO studentDao = new StudentDAO();

    public StudentController() {

    }

    public void displayAll() {
        studentView.displayAll(studentDao.requestAll());
    }

    public void addStudent() {
        try {
            String name = studentView.getName();
            String surname = studentView.getSurname();
            Mail email = studentView.getMail();
            Login login = studentView.getLogin(name, surname);
            Password password = getPassword();
            Wallet wallet = new Wallet();
            Student student = new Student(login, password, email, name, surname, wallet);
            studentDao.insert(student);

        } catch (InvalidUserDataException e) {

        }
    }

    public Password getPassword() {
        String alphabet= "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        String value = "";
        while (value.length() < 8) {
            char sign = alphabet.charAt(random.nextInt(36));
            value += sign;
        }

        return new Password(value);
    }

    public void removeStudent() {
        try {
            Student student = chooseStudent();
            studentDao.delete(student);

        } catch (IndexOutOfBoundsException e) {

        }
    }

    public Student chooseStudent() throws IndexOutOfBoundsException {
        ArrayList<Student> students = studentDao.requestAll();
        studentView.displayAll(students);
        Integer record = studentView.getMenuOption();
        Integer index = record - 1;
        if (index >= students.size()) {
            throw new IndexOutOfBoundsException();
        }
        return students.get(index);
    }

    public void editStudent() {
        try {
            Student student = chooseStudent();
            studentView.displayAttribute();
            String option = studentView.getString("Your choice: ");

            switch(option) {
                case EDIT_NAME: 
                    String name = studentView.getName();
                    student.setName(name);
                    break;
                case EDIT_SURNAME: 
                    String surname = studentView.getSurname();
                    student.setSurname(surname);
                    break;
                case EDIT_EMAIL: 
                    Mail email = studentView.getMail(); 
                    student.setEmail(email);
                    break;
                case EDIT_PASSWORD: 
                    String passwordText = studentView.getString("New password: ");
                    student.setPassword(new Password(passwordText));
                    break;
                case EDIT_CLASS:
                    // TODO
                    String classId = "2017.1A";
                    student.setClassId(classId);
                    break;
                case EDIT_TEAM:
                    // TODO
                    Integer teamId = 1;
                    student.setTeamId(teamId);
                    break;
                default: studentView.printErrorMessage();
                    break;
            }

            studentDao.update(student);
        } catch (IndexOutOfBoundsException|InvalidUserDataException e) {
            e.getMessage();
        }
    }
}