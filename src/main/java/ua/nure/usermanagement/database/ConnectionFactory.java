package ua.nure.usermanagement.database;

import ua.nure.usermanagement.database.exception.DatabaseException;

import java.sql.Connection;

public interface ConnectionFactory {
    public Connection createConnection() throws DatabaseException;
}
