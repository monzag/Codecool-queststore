package com.codecool.jlamas.database;

import java.util.ArrayList;

import com.codecool.jlamas.models.account.Student;
import com.codecool.jlamas.models.accountdata.Group;
import com.codecool.jlamas.models.accountdata.Login;
import com.codecool.jlamas.models.accountdata.Mail;
import com.codecool.jlamas.models.accountdata.Password;
import com.codecool.jlamas.models.accountdata.Wallet;

public class StudentDAO {

    public StudentDAO() {
    }

    public ArrayList<Student> requestAll() {
        String query = String.format("%s %s %s %s %s %s %s %s"
                , "SELECT user.login, user.email, user.name, user.surname, login.password, student.group_tag"
                ,         "student.team_tag, student.balance"
                , "FROM user"
                ,     "INNER JOIN login"
                ,             "ON login.login = user.login"
                ,     "INNER JOIN student"
                ,             "ON student.login = user.login"
                , "WHERE user.type = 'student';");


        ArrayList<Student> students = new ArrayList<Student>();
        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery(query);) {

            while (rs.next()) {
                Student student = new Student();

                student.setName(rs.getString("name"));
                student.setSurname(rs.getString("surname"));
                student.setLogin(new Login(rs.getString("login")));
                student.setPassword(new Password(rs.getString("password")));
                student.setEmail(new Mail(rs.getString("email")));
                student.setGroup(new Group(rs.getString("group_tag")));
                student.setTeamId(rs.getInt("team_tag"));
                students.add(student);
            }

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }

        return students;
    }

    public boolean delete(Student student) {
            // true if was successful
            String query;

            try (Connection c = ConnectDB.connect();
                 Statement stmt = c.createStatement();) {

                query = String.format("DELETE FROM `user` WHERE login = '%s'; ",
                        student.getLogin().getValue());

                query += String.format("DELETE FROM `login` WHERE login = '%s'; ",
                        student.getLogin().getValue());

                query += String.format("DELETE FROM `mentor` WHERE login = '%s'; ",
                        student.getLogin().getValue());

                stmt.executeUpdate(query);

            } catch (ClassNotFoundException|SQLException e) {
                System.out.println(e.getMessage());

                return false;
            }
            return true;
    }

    public boolean insert(Student student) {

        final Integer UNSIGNED_TEAM = 0;
        final Integer BALANCE = 0;

        String query;

        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement();) {

            query = String.format("INSERT INTO `user` VALUES('%s', '%s', '%s', '%s', 'student'); ",
                    student.getLogin().getValue(),
                    student.getEmail().getValue(),
                    student.getName(),
                    student.getSurname());

            query += String.format("INSERT INTO `login` VALUES('%s', '%s'); ",
                    student.getLogin().getValue(),
                    student.getPassword().getValue());

            query += String.format("INSERT INTO `student` VALUES('%s', '%s', '%s', '%s'); ",
                    student.getLogin().getValue(),
                    student.getGroup().getName(),
                    UNSIGNED_TEAM,
                    BALANCE);

            stmt.executeUpdate(query);

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());

            return false;
        }
        return true;

    }

    public boolean update(Student student) {
        String query;

        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement();) {

            query = String.format("UPDATE `user` SET login = '%s', email = '%s', name = '%s', surname = '%s', " +
                                  "type = 'mentor' WHERE login = '%s'; ",
                    student.getLogin().getValue(),
                    student.getEmail().getValue(),
                    student.getName(),
                    student.getSurname(),
                    student.getLogin().getValue());

            query += String.format("UPDATE `login` SET login = '%s', password = '%s' WHERE login = '%s'; ",
                    student.getLogin().getValue(),
                    student.getPassword().getValue(),
                    student.getLogin().getValue());

            query += String.format("UPDATE `mentor` SET login = '%s', group_tag = '%s', team_tag = '%s', " +
                                   "balance = '%s', coolcoins = '%s' WHERE login = '%s'; ",
                    student.getLogin().getValue(),
                    student.getGroup().getName(),
                    student.getTeamId(),
                    student.getWallet().getBalance(),
                    student.getLogin().getValue());

            stmt.executeUpdate(query);

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public Student getStudent(String userLogin) {
        String query = String.format("%s %s %s %s %s %s %s WHERE user.type = 'student' AND login.login = '%s';"
                , "SELECT user.login, user.email, user.name, user.surname, login.password, student.group_tag,"
                , "student.team_tag, student.balance"
                , "FROM user"
                ,     "INNER JOIN login"
                ,             "ON login.login = user.login"
                ,     "INNER JOIN student"
                ,             "ON student.login = user.login"
                , userLogin);


        Student student = new Student();
        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery(query);) {

            student.setName(rs.getString("name"));
            student.setSurname(rs.getString("surname"));
            student.setLogin(new Login(rs.getString("login")));
            student.setPassword(new Password(rs.getString("password")));
            student.setEmail(new Mail(rs.getString("email")));
            student.setGroup(new Group(rs.getString("group_tag")));
            student.setTeamId(rs.getInt("team_tag"));
            student.setWallet(new Wallet(rs.getInt("balance")));

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }

        return student;
    }

}
