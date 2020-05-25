package com.markm;

import java.sql.*;

public class TestDBService
{
    // TODO: Create a system that makes the CRUD calls for all entities generic
    // TODO: Make all entities be able to override their own CRUD calls if needed
    // TODO: Grab the table description from db and use it to enforce validation on the objects

    public static int CreateUser(String username, String password)
    {
        // TODO: If needed, check if the username already exists

        Timestamp now = new Timestamp(System.currentTimeMillis());
        User user = new User();
        user.setUserId(0);
        user.setUsername(username);
        user.setPassword(password);
        user.setActive(false);
        user.setDateCreated(now);
        user.setCreatedBy(username);
        user.setDateModified(now);
        user.setModifiedBy(username);

        String query = "insert into user " +
                "values (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionManager.instance()
                                                .getConnection();
             PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS))
        {
            // Setting the query values
            User.SerializeToDB(user, statement);

            statement.executeUpdate();

            ResultSet result = statement.getGeneratedKeys();
            int userId = -1;
            if (result != null && result.next())
            {
                int count = result.getMetaData()
                                  .getColumnCount();

                System.out.println(result.getObject(1));
                for (int i = 1; i <= count; i++)
                {
                    System.out.println(result.getMetaData().getColumnLabel(i));
                    System.out.println(result.getMetaData().getColumnName(i));
                    System.out.println(result.getMetaData().getCatalogName(i));
                    System.out.println(result.getMetaData().getColumnClassName(i));
                    System.out.println(result.getMetaData().getColumnDisplaySize(i));
                    System.out.println(result.getMetaData().getColumnType(i));
                    System.out.println(result.getMetaData().getColumnTypeName(i));
                    System.out.println(result.getMetaData().getPrecision(i));
                    System.out.println(result.getMetaData().getScale(i));
                    System.out.println(result.getMetaData().getSchemaName(i));
                    System.out.println(result.getMetaData().getTableName(i));
                }
            }

            System.out.println("User " + username + " created successfully!");

            return userId;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return -1;
        }
    }

    public static User ReadUser(int userId)
    {
        String query = "select * in user " +
                "where userId = ?";
        try (Connection conn = ConnectionManager.instance()
                                                .getConnection();
             PreparedStatement statement = conn.prepareStatement(query))
        {
            statement.setInt(1, userId);

            ResultSet result = statement.executeQuery();
            // Using only an if here, because we're only expecting one result
            if (result.next())
            {
                return User.DeserializeFromDB(result);
            }

            return null;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static User ReadUser(String username)
    {
        String query = "select * in user " +
                "where userName = ?";
        try (Connection conn = ConnectionManager.instance()
                                                .getConnection();
             PreparedStatement statement = conn.prepareStatement(query))
        {
            statement.setString(1, username);

            ResultSet result = statement.executeQuery();
            if (result.next())
            {
                return User.DeserializeFromDB(result);
            }

            return null;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static void UpdateUser(User user)
    {
        // TODO: Maybe validate the User object here

        String query = "update user " +
                "set userId = ?, userName = ?, password = ?, active = ?, createDate = ?, createdBy = ?, lastUpdate = ?, lastUpdateBy = ? " +
                "where userId = ?";
        try (Connection conn = ConnectionManager.instance()
                                                .getConnection();
             PreparedStatement statement = conn.prepareStatement(query))
        {
            User.SerializeToDB(user, statement);
            statement.setInt(9, user.getUserId());

            statement.executeUpdate();

            System.out.println("User '" + user.getUsername() + "' updated successfully!");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void DeleteUser(int userId)
    {
        String query = "delete from user " +
                "where userId = ?";
        try (Connection conn = ConnectionManager.instance()
                                                .getConnection();
             PreparedStatement statement = conn.prepareStatement(query))
        {
            statement.setInt(1, userId);

            statement.executeUpdate();

            System.out.println("User with id '" + userId + "' deleted successfully!");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void DeleteUser(String username)
    {
        String query = "delete from user " +
                "where userName = ?";
        try (Connection conn = ConnectionManager.instance()
                                                .getConnection();
             PreparedStatement statement = conn.prepareStatement(query))
        {
            statement.setString(1, username);

            statement.executeUpdate();

            System.out.println("User '" + username + "' deleted successfully!");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
