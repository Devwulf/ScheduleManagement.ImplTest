package com.markm;

import java.sql.*;

public class User
{
    public enum UserField
    {
        UserID(1),
        Username(2),
        Password(3),
        IsActive(4),
        DateCreated(5),
        CreatedBy(6),
        DateModified(7),
        ModifiedBy(8);

        private final int value;

        private UserField(int value)
        {
            this.value = value;
        }

        public int getValue()
        {
            return value;
        }
    }

    private int userId;
    private String username;
    private String password;
    private boolean isActive;
    private Timestamp dateCreated;
    private String createdBy;
    private Timestamp dateModified;
    private String modifiedBy;

    public User()
    {
        userId = -1;
    }

    public static void SerializeToDB(User user, PreparedStatement statement)
    {
        // TODO: Should check if user is valid
        try
        {
            statement.setInt(UserField.UserID.getValue(), user.getUserId());
            statement.setString(UserField.Username.getValue(), user.getUsername());
            statement.setString(UserField.Password.getValue(), user.getPassword());
            statement.setBoolean(UserField.IsActive.getValue(), user.getIsActive());
            statement.setTimestamp(UserField.DateCreated.getValue(), user.getDateCreated());
            statement.setString(UserField.CreatedBy.getValue(), user.getCreatedBy());
            statement.setTimestamp(UserField.DateModified.getValue(), user.getDateModified());
            statement.setString(UserField.ModifiedBy.getValue(), user.getModifiedBy());
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static User DeserializeFromDB(ResultSet result)
    {
        try
        {
            if (result.next())
            {
                User user = new User();
                user.setUserId(result.getInt(UserField.UserID.getValue()));
                user.setUsername(result.getString(UserField.Username.getValue()));
                user.setPassword(result.getString(UserField.Password.getValue()));
                user.setIsActive(result.getBoolean(UserField.IsActive.getValue()));
                user.setDateCreated(result.getTimestamp(UserField.DateCreated.getValue()));
                user.setCreatedBy(result.getString(UserField.CreatedBy.getValue()));
                user.setDateModified(result.getTimestamp(UserField.DateModified.getValue()));
                user.setModifiedBy(result.getString(UserField.ModifiedBy.getValue()));

                return user;
            }

            return null;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    // Getters/Setters
    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
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

    public boolean getIsActive()
    {
        return isActive;
    }

    public void setIsActive(boolean active)
    {
        isActive = active;
    }

    public Timestamp getDateCreated()
    {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated)
    {
        this.dateCreated = dateCreated;
    }

    public String getCreatedBy()
    {
        return createdBy;
    }

    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }

    public Timestamp getDateModified()
    {
        return dateModified;
    }

    public void setDateModified(Timestamp dateModified)
    {
        this.dateModified = dateModified;
    }

    public String getModifiedBy()
    {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy)
    {
        this.modifiedBy = modifiedBy;
    }
}
