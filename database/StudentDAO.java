package database;

import models.accountdata.*;
import models.account.Student;

import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;


public class StudentDAO implements DAO {

    private final String FILEPATH = "database/students/";

    public StudentDAO() {
    }

    public Student load(String login) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILEPATH + login + ".txt"))) {
            Student student = new Student();
            String rLogin = br.readLine();
            String rPassword = br.readLine();
            String rEmail = br.readLine();
            String rName = br.readLine();
            String rSurname = br.readLine();
            String rClassId = br.readLine();
            String rTeamId = br.readLine();
            String rBalance = br.readLine();

            if (rLogin.startsWith("LOGIN: ")) {
                student.setLogin(new Login(rLogin.substring("LOGIN: ".length())));
            }
            else {
                return null;
            }
            if (rPassword.startsWith("PASSWORD: ")) {
                student.setPassword(new Password(rPassword.substring("PASSWORD: ".length())));
            }
            else {
                return null;
            }
            if (rEmail.startsWith("EMAIL: ")) {
                student.setEmail(new Mail(rEmail.substring("EMAIL: ".length())));
            }
            else {
                return null;
            }
            if (rName.startsWith("NAME: ")) {
                student.setName(rName.substring("NAME: ".length()));
            }
            else {
                return null;
            }
            if (rSurname.startsWith("SURNAME: ")) {
                student.setSurname(rSurname.substring("SURNAME: ".length()));
            }
            else {
                return null;
            }
            if (rClassId.startsWith("CLASSID: ")) {
                student.setClassId(rClassId.substring("CLASSID: ".length()));
            }
            else {
                return null;
            }
            if (rTeamId.startsWith("TEAMID: ")) {
                student.setTeamId(rTeamId.substring("TEAMID: ".length()));
            }
            else {
                return null;
            }
            if (rBalance.startsWith("BALANCE: ")) {
                student.setWallet(new Wallet(Integer.parseInt(rBalance.substring("BALANCE: ".length()))));
            }
            else {
                return null;
            }
            return student;
        } catch (IOException e) {
            System.out.println("File not found.");
        }
    }

    public ArrayList<Student> loadAll() {
        ArrayList<Student> studentList = new ArrayList<>();

        File[] files = new File(FILEPATH).listFiles();

        for (File file : files) {
            int dotPosition = file.getName().lastIndexOf(".");
            studentList.add(load(file.getName().substring(0, dotPosition)));
        }
        return studentList;
    }

    public void save(Student student) {
        try (FileWriter fw = new FileWriter(FILEPATH + student.getLogin() + ".txt")) {
            fw.write("LOGIN: " + student.getLogin().getValue());
            fw.write("PASSWORD: " + student.getPassword().getValue());
            fw.write("EMAIL: " + student.getEmail().getValue());
            fw.write("NAME: " + student.getName());
            fw.write("SURNAME: " + student.getSurname());
            fw.write("CLASSID: " + student.getClassId());
        } catch (IOException e) {
            System.out.println("Filepath not found.");
        }
    }
}