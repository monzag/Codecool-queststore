package database;

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
            String rId = br.readline();
            String rLogin = br.readline();
            String rPassword = br.readline();
            String rEmail = br.readline();
            String rName = br.readline();
            String rSurname = br.readline();

            if (rId.startswith("ID: ")) {
                admin.setId(rId.substring("ID: ".length()));
            }
            else {
                return null;
            }
            if (rLogin.startswith("LOGIN: ")) {
                admin.setLogin(new Login(rLogin.substring("LOGIN: ".length())));
            }
            else {
                return null;
            }
            if (rPassword.startswith("PASSWORD: ")) {
                admin.setPassword(new Password(rPassword.substring("PASSWORD: ".length())));
            }
            else {
                return null;
            }
            if (rEmail.startswith("EMAIL: ")) {
                admin.setEmail(new Email(rEmail.substring("EMAIL: ".length())));
            }
            else {
                return null;
            }
            if (rName.startswith("NAME: ")) {
                admin.setName(rName.substring("NAME: ".length()));
            }
            else {
                return null;
            }
            if (rSurname.startswith("SURNAME: ")) {
                mentor.setSurname(rId.substring("SURNAME: ".length()));
            }
            else {
                return null;
            }
            return admin;

        } catch (IOException e) {
            System.out.println("File not found.");
        }

    public void save(Admin admin) {
        try (FileWriter fw = new FileWriter(FILEPATH + admin.getLogin() + ".txt")) {
            fw.write("ID: " + admin.getId());
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
