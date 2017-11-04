package ua.nure.usermanagement.database;

import ua.nure.usermanagement.database.exception.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * An implementation of connection factory interface
 *
 * @see ua.nure.usermanagement.database.ConnectionFactory
 */
public class ConnectionFactoryImpl implements ConnectionFactory {

    private String driver;
    private String url;
    private String user;
    private String password;

    public ConnectionFactoryImpl(String driver, String url, String user, String password) throws DatabaseException {
        this.driver = driver;
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public ConnectionFactoryImpl(Properties properties) {
        driver = properties.getProperty("connection.driver");
        url = properties.getProperty("connection.url");
        user = properties.getProperty("connection.user");
        password = properties.getProperty("connection.password");
    }

    /**
     * @return returns generated connection based on parameters driver, url, user, password
     * @throws DatabaseException if an error comes up in a database
     * @see ConnectionFactory#createConnection()
     */
    @Override
    public Connection createConnection() throws DatabaseException {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException();
        }
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new DatabaseException();
        }
    }
}
