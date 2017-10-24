package ua.nure.usermanagement.database;

import ua.nure.usermanagement.database.exception.DatabaseException;

import java.io.IOException;
import java.util.Properties;

/**
 * A singleton used for producing DAOs
 */
public abstract class DaoFactory {
    protected static final String USER_DAO_PROP = "ua.nure.usermanagement.database.UserDao";
    protected static Properties properties;
    protected static final String DAO_FACTORY = "dao.factory";

    protected static DaoFactory instance;

    static {
        properties = new Properties();
        try {
            properties.load(DaoFactory.class.getClassLoader().getResourceAsStream("settings.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @return current instance of DaoFactory
     */
    public static synchronized DaoFactory getInstance() {
        if(instance == null){
            try{
                Class factoryClass = Class.forName(properties.getProperty(DAO_FACTORY));
                instance = ((DaoFactory)factoryClass.newInstance());
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    /**
     * A private method that generates a connection factory based on the current settings, listed in settings.properties file
     * @return generated connection factory
     */
    protected ConnectionFactory getConnectionFactory() throws DatabaseException {
        return new ConnectionFactoryImpl(properties);
    }

    /**
     * Generates a new user DAO based on the current parameters of the system.
     * @return a new DAO for a currently used database (defined by USER_DAO_PROP variable)
     */
//    {

    public abstract UserDao getUserDao();

    public static void init(Properties prop){
        DaoFactory.properties = prop;
        instance = null;
    }
}
