package com.markm;

import java.sql.Connection;

public class DBConnection
{
    private String name;
    private String url;
    private String username;
    private String password;
    private Connection connection;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Connection getConnection()
    {
        return connection;
    }

    public void setConnection(Connection connection)
    {
        this.connection = connection;
    }
}
