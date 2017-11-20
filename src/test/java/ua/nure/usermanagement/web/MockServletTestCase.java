package ua.nure.usermanagement.web;

import com.mockobjects.dynamic.Mock;
import com.mockrunner.servlet.BasicServletTestCaseAdapter;
import ua.nure.usermanagement.User;
import ua.nure.usermanagement.database.DaoFactory;
import ua.nure.usermanagement.database.MockDaoFactory;

import java.util.Calendar;
import java.util.Properties;

/**
 * A class, that implements a basic test-case template for the testing of servlets
 */
public abstract class MockServletTestCase extends BasicServletTestCaseAdapter {

    private Mock mockUserDao;

    /**
     * Sets up a mock version of a Dao, that's going to be used in tests.
     * @throws Exception If some malfunction occurs
     */
    public void setUp() throws Exception {
        super.setUp();
        Properties properties = new Properties();
        properties.setProperty("dao.factory", MockDaoFactory.class.getName());
        DaoFactory.init(properties);
        setMockUserDao(((MockDaoFactory) DaoFactory.getInstance()).getMockUserDao());
    }

    /**
     * Checks, whether or not the mock has received all of the necessary calls
     * @throws Exception If some malfunction occurs
     */
    public void tearDown() throws Exception {
        super.tearDown();
        mockUserDao.verify();
    }

    public Mock getMockUserDao() {
        return mockUserDao;
    }

    public void setMockUserDao(Mock mockUserDao) {
        this.mockUserDao = mockUserDao;
    }

    /**
     * Creates one simple user.
     * @return A new User object
     */
    protected User createJerry() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new User(1000L, "Jerry", "Smith", calendar.getTime());
    }
}
