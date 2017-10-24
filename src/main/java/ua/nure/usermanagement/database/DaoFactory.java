package ua.nure.usermanagement.database;

import ua.nure.usermanagement.database.exception.DatabaseException;

import java.io.IOException;
import java.util.Properties;

/**
 * A singleton used for producing DAOs
 */
public class DaoFactory {
    private static final String USER_DAO_PROP = "ua.nure.usermanagement.database.userDao";
    private final Properties properties = new Properties();
    private static DaoFactory instance = new DaoFactory();

    private DaoFactory() {
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("settings.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return current instance of DaoFactory
     */
    public static DaoFactory getInstance() {
        return instance;
    }

    /**
     * A private method that generates a connection factory based on the current settings, listed in settings.properties file
     * @return generated connection factory
     */
    private ConnectionFactory getConnectionFactory() {
        ConnectionFactory connectionFactory = null;
        try {
            connectionFactory = new ConnectionFactoryImpl(properties.getProperty("driver"), properties.getProperty("url"), properties.getProperty("user"), properties.getProperty("password"));
            return connectionFactory;
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        return connectionFactory;
    }

    /**
     * Generates a new user DAO based on the current parameters of the system.
     * @return a new DAO for a currently used database (defined by USER_DAO_PROP variable)
     */
    public UserDao getUserDao() {
        UserDao result = null;
        try {
            Class clazz = Class.forName(properties.getProperty(USER_DAO_PROP));
            UserDao userDao = (UserDao) clazz.newInstance();
            userDao.setConnectionFactory(getConnectionFactory());
            result = userDao;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
}
