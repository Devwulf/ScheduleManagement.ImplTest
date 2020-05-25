package com.markm;

public class DBContext
{
    public DBSet<User> Users;

    public DBContext()
    {
        Users = new DBSet<>(User.class);
    }
}
