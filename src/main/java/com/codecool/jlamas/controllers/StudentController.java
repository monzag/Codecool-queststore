package com.codecool.jlamas.controllers;

import java.util.ArrayList;
import java.util.Random;

import com.codecool.jlamas.database.StudentDAO;
import com.codecool.jlamas.exceptions.InvalidUserDataException;
import com.codecool.jlamas.models.account.Student;
import com.codecool.jlamas.models.accountdata.Group;
import com.codecool.jlamas.models.accountdata.Login;
import com.codecool.jlamas.models.accountdata.Mail;
import com.codecool.jlamas.models.accountdata.Password;
import com.codecool.jlamas.models.accountdata.Wallet;
import com.codecool.jlamas.views.StudentView;

public class StudentController {

    private static final String EDIT_NAME = "1";
    private static final String EDIT_SURNAME = "2";
    private static final String EDIT_EMAIL = "3";
    private static final String EDIT_PASSWORD = "4";
    private static final String EDIT_GROUP = "5";
    private static final String EDIT_TEAM = "6";

    private StudentView studentView = new StudentView();
    private StudentDAO studentDao = new StudentDAO();

    public StudentController() {

    }

    public void displayAll() {
        studentView.displayAll(studentDao.requestAll());
    }

    public void addStudent() {
        try {
            SendMail sendPassword = new SendMail();
            String name = studentView.getName();
            String surname = studentView.getSurname();
            Mail email = studentView.getMail();
            Login login = studentView.getLogin(name, surname);
            Password password = getPassword();
            Group group = getGroup();
            Wallet wallet = new Wallet(0);
            Student student = new Student(login, password, email, name, surname, group, wallet);
            studentDao.insert(student);
            String logData = "Login: " + login.getValue() + " Password: " + password.getValue();
            sendPassword.sendMail(email.getValue(), logData);

        } catch (InvalidUserDataException e) {
            e.getMessage();
        }
    }

    public Group getGroup() {
        Group group = new Group();
        GroupController groupController = new GroupController();
        try {
            group = groupController.chooseGroup();
        } catch (IndexOutOfBoundsException e) {
            studentView.printIndexError();
        }
        return group;
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
            studentView.printIndexError();
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
                case EDIT_GROUP:
                    GroupController groupController = new GroupController();
                    groupController.editGroup();
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
