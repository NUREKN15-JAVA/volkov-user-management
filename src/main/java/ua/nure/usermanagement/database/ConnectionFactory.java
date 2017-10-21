package ua.nure.usermanagement.database;

import ua.nure.usermanagement.database.exception.DatabaseException;

import java.sql.Connection;

/**
 * This interface defines all necessary methods to complete a connection factory
 */
public interface ConnectionFactory {
    /**
     * Generates a connection based on the current settings of the system
     * See settings.properties file for a list of used parameters
     * @return generated connection
     * @throws DatabaseException
     */
    public Connection createConnection() throws DatabaseException;
}
