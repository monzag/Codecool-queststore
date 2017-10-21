package com.codecool.jlamas.database;

import java.sql.*;

public final class ConnectDB {

    private ConnectDB() {}

    public static Connection connect() throws ClassNotFoundException, SQLException {

        Class.forName("org.sqlite.JDBC");
        Connection c = DriverManager.getConnection("jdbc:sqlite:./target/database");
        return c;
    }
}
