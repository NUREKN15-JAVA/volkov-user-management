package ua.nure.usermanagement.database;

public class DaoFactoryImpl extends DaoFactory {

    @Override
    public UserDao getUserDao() {
        UserDao result = null;
        try {
            Class clazz = Class.forName(properties.getProperty(USER_DAO_PROP));
            UserDao userDao = (UserDao) clazz.newInstance();
            userDao.setConnectionFactory(getConnectionFactory());
            result = userDao;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;

    }
}

