package com.markm;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

public class PreparedStatementExtension
{
    public static void setValue(PreparedStatement statement, int parameterIndex, Object value)
    {
        try
        {
            if (String.class.isInstance(value))
                statement.setString(parameterIndex, (String)value);
            else if (Integer.class.isInstance(value))
                statement.setInt(parameterIndex, (Integer)value);
            else if (Timestamp.class.isInstance(value))
                statement.setTimestamp(parameterIndex, (Timestamp)value);
            else if (Boolean.class.isInstance(value))
                statement.setBoolean(parameterIndex, (Boolean)value);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void setValue(String value, int parameterIndex, PreparedStatement statement)
    {
        try
        {
            statement.setString(parameterIndex, value);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void setValue(int value, int parameterIndex, PreparedStatement statement)
    {
        try
        {
            statement.setInt(parameterIndex, value);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void setValue(Timestamp value, int parameterIndex, PreparedStatement statement)
    {
        try
        {
            statement.setTimestamp(parameterIndex, value);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void setValue(Timestamp value, Calendar calendar, int parameterIndex, PreparedStatement statement)
    {
        try
        {
            statement.setTimestamp(parameterIndex, value, calendar);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void setValue(boolean value, int parameterIndex, PreparedStatement statement)
    {
        try
        {
            statement.setBoolean(parameterIndex, value);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
