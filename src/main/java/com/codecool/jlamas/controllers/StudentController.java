package com.codecool.jlamas.controllers;

import java.util.ArrayList;
import java.util.Map;

import com.codecool.jlamas.database.StudentDAO;
import com.codecool.jlamas.database.TeamDAO;
import com.codecool.jlamas.exceptions.EmailAlreadyUsedException;
import com.codecool.jlamas.exceptions.InvalidUserDataException;
import com.codecool.jlamas.models.account.Student;
import com.codecool.jlamas.models.accountdata.*;


public class StudentController implements Controller<Student> {

    private StudentDAO studentDao;
    private GroupController groupController;
    private TeamDAO teamDao;

    public StudentController() {
        this.studentDao = new StudentDAO();
        this.groupController = new GroupController();
        this.teamDao = new TeamDAO();
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

    public void createFromMap(Map<String, String> attrs) throws InvalidUserDataException {

        if (!Mail.isValid(attrs.get("email"))) {
            throw new EmailAlreadyUsedException();
        }
        Mail email = new Mail(attrs.get("email"));

        String name = attrs.get("name");
        String surname = attrs.get("surname");
        Login login = Login.generate(name, surname);
        Password password = Password.generate();
        Group group = this.groupController.get(attrs.get("group"));
        Team team = this.teamDao.get(Integer.parseInt(attrs.get("team")));
        Wallet wallet = new Wallet(0);

        Student student = new Student(login, password, email, name, surname, group, team, wallet);
        student.correctNames();

        this.studentDao.insert(student);
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
        student.setGroup(this.groupController.get(attrs.get("group")));

        student.correctNames();
        this.studentDao.update(student);
    }
}
