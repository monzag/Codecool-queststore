package database;

import models.account.Admin;
import models.accountdata.*;

import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class AdminDAO implements DAO {

    private final String FILEPATH = "database/admins/";

    public AdminDAO() {
    }

    public Admin load(String login) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILEPATH + login + ".txt"))) {
            Admin admin = new Admin();
            String rLogin = br.readline();
            String rPassword = br.readline();
            String rEmail = br.readline();
            String rName = br.readline();
            String rSurname = br.readline();

            if (rLogin.startsWith("LOGIN: ")) {
                admin.setLogin(new Login(rLogin.substring("LOGIN: ".length())));
            }
            else {
                return null;
            }
            if (rPassword.startsWith("PASSWORD: ")) {
                admin.setPassword(new Password(rPassword.substring("PASSWORD: ".length())));
            }
            else {
                return null;
            }
            if (rEmail.startsWith("EMAIL: ")) {
                admin.setEmail(new Mail(rEmail.substring("EMAIL: ".length())));
            }
            else {
                return null;
            }
            if (rName.startsWith("NAME: ")) {
                admin.setName(rName.substring("NAME: ".length()));
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
            return admin;

        } catch (IOException e) {
            System.out.println("File not found.");
        }
    }

    public ArrayList<Admin> loadAll() {
        ArrayList<Admin> adminList = new ArrayList<>();

        File[] files = new File(FILEPATH).listFiles();

        for (File file : files) {
            int dotPosition = file.getName().lastIndexOf(".");
            adminList.add(load(file.getName().substring(0, dotPosition)));
        }
        return adminList;
    }

    public void save(Admin admin) {
        try (FileWriter fw = new FileWriter(FILEPATH + admin.getLogin() + ".txt")) {
            fw.write("LOGIN: " + admin.getLogin().getValue());
            fw.write("PASSWORD: " + admin.getPassword().getValue());
            fw.write("EMAIL: " + admin.getEmail().getValue());
            fw.write("NAME: " + admin.getName());
            fw.write("SURNAME: " + admin.getSurname());
        } catch (IOException e) {
            System.out.println("Filepath not found.");
        }
    }
}
