package com.markm;

import java.sql.*;

public class TestDBService
{
    public static int CreateUser(String username, String password)
    {
        // TODO: If needed, check if the username already exists

        Timestamp now = new Timestamp(System.currentTimeMillis());
        User user = new User();
        user.setUserId(0);
        user.setUsername(username);
        user.setPassword(password);
        user.setIsActive(false);
        user.setDateCreated(now);
        user.setCreatedBy(username);
        user.setDateModified(now);
        user.setModifiedBy(username);

        String query = "insert into user " +
                "values (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement statement = conn.prepareStatement(query))
        {
            // Setting the query values
            User.SerializeToDB(user, statement);

            statement.executeUpdate();

            ResultSet result = statement.getGeneratedKeys();
            int userId = -1;
            if (result.next())
                userId = result.getInt(1);

            System.out.println("User " + username + " created successfully!");

            return userId;
        }
        catch (SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
            return -1;
        }
    }

    public static User ReadUser(int userId)
    {
        String query = "select * in user " +
                "where userId = ?";
        try (Connection conn = ConnectionManager.getConnection();
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
        catch (SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static User ReadUser(String username)
    {
        String query = "select * in user " +
                "where userName = ?";
        try (Connection conn = ConnectionManager.getConnection();
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
        catch (SQLException | ClassNotFoundException e)
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
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement statement = conn.prepareStatement(query))
        {
            User.SerializeToDB(user, statement);
            statement.setInt(9, user.getUserId());

            statement.executeUpdate();

            System.out.println("User '" + user.getUsername() + "' updated successfully!");
        }
        catch (SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public static void DeleteUser(int userId)
    {
        String query = "delete from user " +
                       "where userId = ?";
        try(Connection conn = ConnectionManager.getConnection();
            PreparedStatement statement = conn.prepareStatement(query))
        {
            statement.setInt(1, userId);

            statement.executeUpdate();

            System.out.println("User with id '" + userId + "' deleted successfully!");
        }
        catch (SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public static void DeleteUser(String username)
    {
        String query = "delete from user " +
                "where userName = ?";
        try(Connection conn = ConnectionManager.getConnection();
            PreparedStatement statement = conn.prepareStatement(query))
        {
            statement.setString(1, username);

            statement.executeUpdate();

            System.out.println("User '" + username + "' deleted successfully!");
        }
        catch (SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
