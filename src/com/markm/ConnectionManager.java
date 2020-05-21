package com.markm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager
{
    private static final String DB_URL = "jdbc:mysql://3.227.166.251/U06Wsv";
    private static final String DB_USERNAME = "U06Wsv";
    private static final String DB_PASSWORD = "53688888309";
    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";

    public static Connection getConnection() throws SQLException, ClassNotFoundException
    {
        // Loads the driver class
        Class.forName(DB_DRIVER);

        Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        System.out.println("Connection to DB created successfully!");

        return conn;
    }
}
