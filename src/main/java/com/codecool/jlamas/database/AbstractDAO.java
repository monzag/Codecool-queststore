package com.codecool.jlamas.database;

import java.sql.*;

public abstract class AbstractDAO {

    private Connection c;

    public void connectDB() throws SQLException {
        c = DriverManager.getConnection("jdbc:sqlite:./target/database.db");
        c.setAutoCommit(false);
    }

    public void closeDatabase() throws SQLException {
        c.commit();
        c.close();
    }
}
