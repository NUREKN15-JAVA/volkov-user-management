package ua.nure.usermanagement.database;

import com.mockobjects.dynamic.Mock;

/**
 * Implements a mock version of DaoFactory interface for testing purposes
 * @see ua.nure.usermanagement.database.DaoFactory
 */
public class MockDaoFactory extends DaoFactory {
    private Mock mockUserDao;

    public MockDaoFactory(){
        mockUserDao = new Mock(UserDao.class);
    }

    @Override
    public UserDao getUserDao() {
        return ((UserDao) mockUserDao.proxy());
    }

    /**
     * Specialized method for retrieving actual mock object, that represents UserDao
     * @return A mock, that represents UserDao
     */
    public Mock getMockUserDao(){
        return mockUserDao;
    }
}
