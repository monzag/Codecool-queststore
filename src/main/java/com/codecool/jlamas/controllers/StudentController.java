package com.codecool.jlamas.controllers;

import java.util.ArrayList;
import java.util.Map;

import com.codecool.jlamas.database.StudentDAO;
import com.codecool.jlamas.exceptions.EmailAlreadyUsedException;
import com.codecool.jlamas.exceptions.InvalidUserDataException;
import com.codecool.jlamas.models.account.Student;
import com.codecool.jlamas.models.accountdata.Group;
import com.codecool.jlamas.models.accountdata.Login;
import com.codecool.jlamas.models.accountdata.Mail;
import com.codecool.jlamas.models.accountdata.Password;
import com.codecool.jlamas.models.accountdata.Wallet;


public class StudentController {

    private StudentDAO studentDao;

    public StudentController() {
        this.studentDao = new StudentDAO();
    }

    public ArrayList<Student> getStudents() {
        return studentDao.requestAll();
    }

    public Student getStudent(String login) {
        return this.studentDao.getStudent(login);
    }

    public void removeStudent(String login) {
        studentDao.delete(this.getStudent(login));
    }

    public Group getGroup(String id) {
        return new GroupController().getGroup(Integer.valueOf(id));
    }

    public void createStudentFromMap(Map<String, String> attrs) throws InvalidUserDataException {

        if (!Mail.isValid(attrs.get("email"))) {
            throw new EmailAlreadyUsedException();
        }
        Mail email = new Mail(attrs.get("email"));

        String name = attrs.get("name");
        String surname = attrs.get("surname");
        Login login = Login.generate(name, surname);
        Password password = Password.generate();
        Group group = this.getGroup(attrs.get("group"));
        Wallet wallet = new Wallet(0);

        Student student = new Student(login, password, email, name, surname, group, wallet);
        student.correctNames();

        this.studentDao.insert(student);
    }

    public void editStudnetFromMap(Map<String, String> attrs, String login) throws InvalidUserDataException {
        Student student = this.getStudent(login);

        if (!student.hasEmail(attrs.get("email"))) {
            if (!Mail.isValid(attrs.get("email"))) {
                throw new EmailAlreadyUsedException();
            }
            student.setEmail(new Mail(attrs.get("email")));
        }

        student.setName(attrs.get("name"));
        student.setSurname(attrs.get("surname"));
        student.setGroup(this.getGroup(attrs.get("group")));

        student.correctNames();
        this.studentDao.update(student);
    }
}
