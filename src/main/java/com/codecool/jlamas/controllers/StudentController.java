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


public class StudentController implements Controller<Student> {

    private StudentDAO studentDao;
    private GroupController groupController;

    public StudentController() {
        this.studentDao = new StudentDAO();
        this.groupController = new GroupController();
    }

    public ArrayList<Student> getAll() {
        return studentDao.requestAll();
    }

    public Student get(String login) {
        return this.studentDao.getStudent(login);
    }

    public void remove(String login) {
        studentDao.delete(this.get(login));
    }

    public Group getGroup(String id) {
        return this.groupController.getGroup(Integer.valueOf(id));
    }

    public void createFromMap(Map<String, String> attrs) throws InvalidUserDataException {

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

    public ArrayList<Student> createStudentsFromInputs(Map<String, String> inputs) {

        ArrayList<Student> students = new ArrayList<>();
        for (String value : inputs.values()) {
            students.add(this.studentDao.getStudent(value));
        }
        return students;
    }

    public void editFromMap(Map<String, String> attrs, String login) throws InvalidUserDataException {
        Student student = this.get(login);

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
