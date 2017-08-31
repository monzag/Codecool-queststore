package database;

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
            String rId = br.readline();
            String rLogin = br.readline();
            String rPassword = br.readline();
            String rEmail = br.readline();
            String rName = br.readline();
            String rSurname = br.readline();
            String rClassId = br.readline();
            String rTeamId = br.readLine();
            String rBalance = br.readLine();

            if (rId.startswith("ID: ")) {
                student.setId(rId.substring("ID: ".length()));
            }
            else {
                return null;
            }
            if (rLogin.startswith("LOGIN: ")) {
                student.setLogin(rLogin.substring("LOGIN: ".length()));
            }
            else {
                return null;
            }
            if (rPassword.startswith("PASSWORD: ")) {
                student.setPassword(rPassword.substring("PASSWORD: ".length()));
            }
            else {
                return null;
            }
            if (rEmail.startswith("EMAIL: ")) {
                student.setEmail(rEmail.substring("EMAIL: ".length()));
            }
            else {
                return null;
            }
            if (rName.startswith("NAME: ")) {
                student.setId(rName.substring("NAME: ".length()));
            }
            else {
                return null;
            }
            if (rSurname.startswith("SURNAME: ")) {
                student.setSurname(rId.substring("SURNAME: ".length()));
            }
            else {
                return null;
            }
            if (rClassId.startswith("CLASSID: ")) {
                student.setClassId(rClassId.substring("CLASSID: ".length()));
            }
            else {
                return null;
            }
            if (rTeamId.startswith("TEAMID: ")) {
                student.setTeamId(rTeamId.substring("TEAMID: ".length()));
            }
            else {
                return null;
            }
            if (rBalance.startswith("BALANCE: ")) {
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
}
