package com.codecool.jlamas.database;

import java.sql.*;
import java.util.ArrayList;

import com.codecool.jlamas.models.account.Mentor;
import com.codecool.jlamas.models.accountdata.*;


public class MentorDAO {

    public MentorDAO() {

    }

    public ArrayList<Mentor> requestAll() {
        String query = String.format("%s %s %s %s %s %s %s"
            , "SELECT user.login, user.email, user.name, user.surname, login.password, mentor.group_id"
            , "FROM user"
            ,     "INNER JOIN login"
            ,             "ON login.login = user.login"
            ,     "INNER JOIN mentor"
            ,             "ON mentor.login = user.login"
            , "WHERE user.type = 'mentor';");


        ArrayList<Mentor> mentors = new ArrayList<Mentor>();
        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery(query);) {

            while (rs.next()) {
                Mentor mentor = getMentorFromResultSet(rs);
                mentors.add(mentor);
            }

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }

        return mentors;
    }

    public boolean insert(Mentor mentor) {
    // true if was successful
    String query;

        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement();) {

            query = String.format("INSERT INTO `user` VALUES('%s', '%s', '%s', '%s', 'mentor'); ",
                    mentor.getLogin().getValue(),
                    mentor.getEmail().getValue(),
                    mentor.getName(),
                    mentor.getSurname());

            query += String.format("INSERT INTO `login` VALUES('%s', '%s'); ",
                    mentor.getLogin().getValue(),
                    mentor.getPassword().getValue());

            query += String.format("INSERT INTO `mentor` VALUES('%s', %d); ",
                    mentor.getLogin().getValue(),
                    mentor.getGroup().getID());

            stmt.executeUpdate(query);

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());

            return false;
        }
        return true;

    }

    public boolean update(Mentor mentor) {
        // true if was successful
        String query;

        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement();) {

            query = String.format("UPDATE `user` SET login = '%s', email = '%s', name = '%s', surname = '%s', type = 'mentor' WHERE login = '%s'; ",
                    mentor.getLogin().getValue(),
                    mentor.getEmail().getValue(),
                    mentor.getName(),
                    mentor.getSurname(),
                    mentor.getLogin().getValue());

            query += String.format("UPDATE `login` SET login = '%s', password = '%s' WHERE login = '%s'; ",
                    mentor.getLogin().getValue(),
                    mentor.getPassword().getValue(),
                    mentor.getLogin().getValue());

            query += String.format("UPDATE `mentor` SET login = '%s', group_id = %d WHERE login = '%s'; ",
                    mentor.getLogin().getValue(),
                    mentor.getGroup().getID(),
                    mentor.getLogin().getValue());
            stmt.executeUpdate(query);

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());

            return false;
        }
        return true;
    }

    public boolean delete(Mentor mentor) {
        // true if was successful
        String query;

        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement();) {

            query = String.format("DELETE FROM `user` WHERE login = '%s'; ",
                    mentor.getLogin().getValue());

            query += String.format("DELETE FROM `login` WHERE login = '%s'; ",
                    mentor.getLogin().getValue());

            query += String.format("DELETE FROM `mentor` WHERE login = '%s'; ",
                    mentor.getLogin().getValue());

            stmt.executeUpdate(query);

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());

            return false;
        }
        return true;


    }

    public Mentor getMentor(String userLogin) {

        Mentor mentor = null;
        String query = String.format("%s %s %s %s %s %s WHERE user.type = 'mentor' AND login.login = '%s';"
            , "SELECT user.login, user.email, user.name, user.surname, login.password, mentor.group_id"
            , "FROM user"
            ,     "INNER JOIN login"
            ,             "ON login.login = user.login"
            ,     "INNER JOIN mentor"
            ,             "ON mentor.login = user.login"
            , userLogin);

        try (Connection c = ConnectDB.connect();
             Statement stmt = c.createStatement();

             ResultSet rs = stmt.executeQuery(query);) {
             mentor = getMentorFromResultSet(rs);

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }

        return mentor;
    }

    public Mentor getMentorFromResultSet(ResultSet rs) throws SQLException{
        GroupDAO groupDAO = new GroupDAO();

        Mentor mentor = new Mentor();

        mentor.setName(rs.getString("name"));
        mentor.setSurname(rs.getString("surname"));
        mentor.setLogin(new Login(rs.getString("login")));
        mentor.setPassword(new Password(rs.getString("password")));
        mentor.setEmail(new Mail(rs.getString("email")));
        mentor.setGroup(groupDAO.getGroup(rs.getInt("group_id")));

        return mentor;
    }
}
