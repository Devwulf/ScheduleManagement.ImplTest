package com.markm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ConnectionManager
{
    private static final String DB_DEFAULT_URL = "jdbc:mysql://3.227.166.251/U06Wsv";
    private static final String DB_DEFAULT_USERNAME = "U06Wsv";
    private static final String DB_DEFAULT_PASSWORD = "53688888309";
    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";

    private ArrayList<DBConnection> connections = new ArrayList<>();

    public Connection getConnection()
    {
        return getConnection("default");
    }

    public Connection getConnection(String connectionName)
    {
        DBConnection connDetails = connections.stream()
                                       .filter(connection -> connection.getName()
                                                                       .equals(connectionName))
                                       .findFirst()
                                       .orElse(null);
        if (connDetails == null)
            throw new NoSuchElementException("The db connection with the name '" + connectionName + "' could not be found.");

        if (connDetails.getConnection() == null)
            refreshConnection(connDetails);

        return connDetails.getConnection();
    }

    public void addConnection(DBConnection connDetails)
    {
        refreshConnection(connDetails);
        connections.add(connDetails);
    }

    private void refreshConnection(DBConnection connDetails)
    {
        if (connDetails.getName()
                       .isEmpty())
            throw new IllegalArgumentException("The given connection details has an empty name.");
        if (connDetails.getUrl()
                       .isEmpty())
            throw new IllegalArgumentException("The given connection details has an empty url.");

        try
        {
            // Loads the driver class
            Class.forName(DB_DRIVER);

            Connection conn = DriverManager.getConnection(connDetails.getUrl(), connDetails.getUsername(), connDetails.getPassword());
            System.out.println("Connection to DB created successfully!");

            connDetails.setConnection(conn);
        }
        catch (SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    /* Singleton implementation below */

    private static ConnectionManager instance = null;

    // Prevents instantiation of class outside here
    private ConnectionManager()
    {
        DBConnection connDetails = new DBConnection();
        connDetails.setName("default");
        connDetails.setUrl(DB_DEFAULT_URL);
        connDetails.setUsername(DB_DEFAULT_USERNAME);
        connDetails.setPassword(DB_DEFAULT_PASSWORD);

        addConnection(connDetails);
    }

    public static ConnectionManager instance()
    {
        if (instance == null)
            instance = new ConnectionManager();

        return instance;
    }
}
