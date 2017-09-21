package com.codecool.jlamas.database;

import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class QuestDAO extends AbstractDAO {
    public QuestDAO() {

    }

    public boolean insertQuest(String name, String description, Integer reward) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "INSERT INTO quest(name, description, reward) VALUES (?, ?, ?);");
            prepStmt.setString(1, name);
            prepStmt.setString(2, description);
            prepStmt.setInteger(3, reward);
            prepStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error");
            return false;
        }
        return true;
    }
}
