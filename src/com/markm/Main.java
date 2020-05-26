package com.markm;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main
{
    public static void main(String[] args) throws SQLException, ClassNotFoundException
    {
        //TestDBService.CreateUser("edc", "123456");

        Timestamp now = new Timestamp(System.currentTimeMillis());
        User user = new User();
        user.setUserId(0);
        user.setUsername("asdf");
        user.setPassword("345678");
        user.setActive(false);
        user.setDateCreated(now);
        user.setCreatedBy("asdf");
        user.setDateModified(now);
        user.setModifiedBy("asdf");

        DBContext context = new DBContext();
        //User newUser = context.Users.createEntity(user);

        List<NameValuePair> query = new ArrayList<>();
        query.add(new NameValuePair("userName", "asdf"));
        query.add(new NameValuePair("password", "345678"));

        List<User> users = context.Users.readEntity(query);

        //context.Users.updateEntity(user, query);

        //context.Users.deleteEntity(query);

        /*
        System.out.println("Hello World!");

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "show tables";
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://3.227.166.251/U06Wsv", "U06Wsv", "53688888309");
            System.out.println(conn);
            if (conn instanceof JDBC4Connection)
            {
                JDBC4Connection jdbcConn = (JDBC4Connection) conn;

                com.mysql.jdbc.DatabaseMetaData meta = (com.mysql.jdbc.DatabaseMetaData) jdbcConn.getMetaData();
                resultSet = meta.getTables(null, null, null, new String[]{
                        "TABLE"
                });

                int count = 0;
                System.out.println("List of all tables in the database '" + jdbcConn.getCatalog() + "':");
                while (resultSet.next())
                {
                    System.out.println(resultSet.getString("TABLE_NAME"));
                    count++;
                }
                System.out.println(count + " rows in set");

                // Uses PreparedStatement here to prevent SQL injection and faster query execution
                statement = jdbcConn.prepareStatement(query);
                resultSet = statement.executeQuery();

                ResultSetMetaData meta = resultSet.getMetaData();
                System.out.println("Executing the query '" + query + "'...");
                int columnsNumber = meta.getColumnCount();
                while (resultSet.next())
                {
                    for (int i = 1; i <= columnsNumber; i++)
                    {
                        if (i > 1) System.out.print(",  ");
                        String columnValue = resultSet.getString(i);
                        System.out.print(columnValue + " " + meta.getColumnName(i));
                    }
                    System.out.println("");
                }
            }
        }
        finally
        {
            if (resultSet != null)
                resultSet.close();

            if (statement != null)
                statement.close();

            if (conn != null)
                conn.close();
        }
        /**/
    }
}
