package ua.nure.usermanagement.database;

import ua.nure.usermanagement.database.exception.DatabaseException;

import java.io.IOException;
import java.util.Properties;

public class DaoFactory {
    public static final String USER_DAO_PROP = "ua.nure.usermanagement.database.userDao";
    private final Properties properties = new Properties();
    private static DaoFactory instance = new DaoFactory();

    private DaoFactory() {
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("settings.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DaoFactory getInstance() {
        return instance;
    }

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
