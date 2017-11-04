package ua.nure.usermanagement.database;

import junit.framework.TestCase;
import ua.nure.usermanagement.User;

/**
 * A test case for DaoFactory class
 */
public class DaoFactoryTest extends TestCase {

    private DaoFactory factory;

    public void setUp() throws Exception {
        super.setUp();
        factory = DaoFactory.getInstance();
    }

    /**
     * Tests, whether or not method getUserDao() returns a UserDao item, or not. Also, it checks, if
     * the DaoFactory is created via getInstance method.
     *
     * @throws Exception
     */
    public void testGetUserDao() throws Exception {
        assertNotNull("DaoFactory is null", factory);
        UserDao dao = factory.getUserDao();
        assertNotNull("UserDao instance is null", dao);

    }

}