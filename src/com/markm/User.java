package com.markm;

import com.markm.Annotations.Key;
import com.markm.Annotations.Column;
import com.markm.Annotations.Table;

import java.sql.*;

@Table(name = "user")
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

    @Key(isAutoGen = true)
    private int userId = 0;

    @Column(name = "userName")
    private String username;

    // This won't have a column annotation because the column name
    // is already the same as the field name
    private String password;

    @Column(name = "active")
    private boolean isActive = false;

    @Column(name = "createDate")
    private Timestamp dateCreated;
    private String createdBy;

    @Column(name = "lastUpdate")
    private Timestamp dateModified;
    @Column(name = "lastUpdateBy")
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
            statement.setBoolean(UserField.IsActive.getValue(), user.isActive());
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
                user.setActive(result.getBoolean(UserField.IsActive.getValue()));
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

    public boolean isActive()
    {
        return isActive;
    }

    public void setActive(boolean active)
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
