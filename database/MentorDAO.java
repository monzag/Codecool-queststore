package database;

import models.account.Mentor;
import models.accountdata.*;

import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;


public class MentorDAO implements DAO {

    private final String FILEPATH = "database/mentors/";

    public MentorDAO() {
    }

    public Mentor load(String login) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILEPATH + login + ".txt"))) {
            Mentor mentor = new Mentor();
            String rLogin = br.readLine();
            String rPassword = br.readLine();
            String rEmail = br.readLine();
            String rName = br.readLine();
            String rSurname = br.readLine();
            String rClassId = br.readLine();

            if (rLogin.startsWith("LOGIN: ")) {
                mentor.setLogin(new Login(rLogin.substring("LOGIN: ".length())));
            }
            else {
                return null;
            }
            if (rPassword.startsWith("PASSWORD: ")) {
                mentor.setPassword(new Password(rPassword.substring("PASSWORD: ".length())));
            }
            else {
                return null;
            }
            if (rEmail.startsWith("EMAIL: ")) {
                mentor.setEmail(new Mail(rEmail.substring("EMAIL: ".length())));
            }
            else {
                return null;
            }
            if (rName.startsWith("NAME: ")) {
                mentor.setName(rName.substring("NAME: ".length()));
            }
            else {
                return null;
            }
            if (rSurname.startsWith("SURNAME: ")) {
                mentor.setSurname(rSurname.substring("SURNAME: ".length()));
            }
            else {
                return null;
            }
            if (rClassId.startsWith("CLASSID: ")) {
                mentor.setClassId(Integer.parseInt(rClassId.substring("CLASSID: ".length())));
            }
            else {
                return null;
            }

            return mentor;

        } catch (IOException e) {
            System.out.println("File not found.");
            return null;
        }
    }

    public ArrayList<Mentor> loadAll() {
        ArrayList<Mentor> mentorList = new ArrayList<>();

        File[] files = new File(FILEPATH).listFiles();

        for (File file : files) {
            int dotPosition = file.getName().lastIndexOf(".");
            mentorList.add(load(file.getName().substring(0, dotPosition)));
        }
        return mentorList;
    }

    public void save(Mentor mentor) {
        try (FileWriter fw = new FileWriter(FILEPATH + mentor.getLogin().getValue() + ".txt")) {
            fw.write("LOGIN: " + mentor.getLogin().getValue() + '\n');
            fw.write("PASSWORD: " + mentor.getPassword().getValue() + '\n');
            fw.write("EMAIL: " + mentor.getEmail().getValue() + '\n');
            fw.write("NAME: " + mentor.getName() + '\n');
            fw.write("SURNAME: " + mentor.getSurname() + '\n');
            fw.write("CLASSID: " + mentor.getClassId() + '\n');
        } catch (IOException e) {
            System.out.println("Filepath not found.");
        }
    }
}
