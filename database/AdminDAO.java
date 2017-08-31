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
            String rLogin = br.readLine();
            String rPassword = br.readLine();
            String rEmail = br.readLine();
            String rName = br.readLine();
            String rSurname = br.readLine();

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
                admin.setSurname(rSurname.substring("SURNAME: ".length()));
            }
            else {
                return null;
            }
            return admin;

        } catch (IOException e) {
            System.out.println("File not found.");
            return null;
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
        try (FileWriter fw = new FileWriter(FILEPATH + admin.getLogin().getValue() + ".txt")) {
            fw.write("LOGIN: " + admin.getLogin().getValue() + '\n');
            fw.write("PASSWORD: " + admin.getPassword().getValue() + '\n');
            fw.write("EMAIL: " + admin.getEmail().getValue() + '\n');
            fw.write("NAME: " + admin.getName() + '\n');
            fw.write("SURNAME: " + admin.getSurname() + '\n');
        } catch (IOException e) {
            System.out.println("Filepath not found.");
        }
    }
}
