package com.codecool.jlamas.database;

import com.codecool.jlamas.models.quest.Quest;
import com.codecool.jlamas.models.account.Student;
import com.codecool.jlamas.models.quest.Quest;
import java.sql.*;
import java.util.ArrayList;

public class DoneQuestDAO {
    public DoneQuestDAO() {

    }

    public void insert(Student student, Quest quest) {
        String sql = "INSERT INTO done_quest(quest_name, owner_name) VALUES (?, ?);";

        try (Connection c = ConnectDB.connect();
                PreparedStatement pstmt = c.prepareStatement(sql);) {

            pstmt.setString(1, quest.getName());
            pstmt.setString(2, student.getLogin().getValue());
            pstmt.executeUpdate();

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Quest> requestAllBy(Student student) {
        ArrayList<Quest> questList = new ArrayList<Quest>();
        String sql = "SELECT name, description, reward FROM quest "
                     + "INNER JOIN done_quest ON done_quest.quest_name = quest.name "
                     + "WHERE done_quest.owner_name = ?";

         try (Connection c = ConnectDB.connect();
                PreparedStatement pstmt = c.prepareStatement(sql);) {

            pstmt.setString(1, student.getLogin().getValue());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Quest quest = new Quest(rs.getString("name"), rs.getString("description"), rs.getInt("reward"));
                questList.add(quest);
            }

        } catch (ClassNotFoundException|SQLException e) {
            System.out.println(e.getMessage());
        }

        return questList;
    }
}
