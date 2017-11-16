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

    public ArrayList<Student> getStudents() {
        return studentDao.requestAll();
    }

    public void addStudent(String name, String surname, String mail, String groupName) {
//        try {
            System.out.println("in StudentController");
            SendMail sendPassword = new SendMail();
            Mail email = new Mail(mail);
            Login login = new Login("testLogin");
            Password password = getPassword();
            Group group = new Group(groupName);
            Wallet wallet = new Wallet(0);
            Student student = new Student(login, password, email, name, surname, group, wallet);
            System.out.println(student);
            studentDao.insert(student);
//            String logData = "Login: " + login.getValue() + " Password: " + password.getValue();
//            sendPassword.sendMail(email.getValue(), logData);

//        } catch (InvalidUserDataException e) {
//            e.getMessage();
//        }
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

    public void removeStudent(String login) {
        try {
            Student student = studentDao.getStudent(login);
            studentDao.delete(student);

        } catch (IndexOutOfBoundsException e) {

        }
    }

    public Student chooseStudent(String login) {
//        ArrayList<Student> students = studentDao.requestAll();
//        studentView.displayAll(students);
//        Integer record = studentView.getMenuOption();
//        Integer index = record - 1;
//        if (index >= students.size()) {
//            throw new IndexOutOfBoundsException();
//        }
        return studentDao.getStudent(login);
    }

    public void editStudent(String login, String name, String surname, String email, String groupName) {
        try {
            Student student = studentDao.getStudent(login);
            student.setName(name);
            student.setSurname(surname);
            student.setEmail(new Mail(email));
            student.setGroup(new Group(groupName));
            studentDao.update(student);
        } catch (NullPointerException e) {
            e.getMessage();
        }
    }
}
