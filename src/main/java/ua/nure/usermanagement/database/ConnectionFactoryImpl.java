package ua.nure.usermanagement.database;

import ua.nure.usermanagement.database.exception.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**An implementation of connection factory interface
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

    /**
     * @see ConnectionFactory#createConnection()
     * @return  eturns generated connection based on parameters driver, url, user, password
     * @throws DatabaseException
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
