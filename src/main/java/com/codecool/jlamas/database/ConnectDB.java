package com.codecool.jlamas.database;

import java.sql.*;

public final class ConnectDB {

    private static Connection c = null;

    private ConnectDB() {}

    public static Connection connect() throws ClassNotFoundException, SQLException {
        if (c == null) {
            c = getConnection();
        }
        return c;
    }

    private static Connection getConnection() throws ClassNotFoundException, SQLException {

        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:./target/database");
        return c;
    }
}
