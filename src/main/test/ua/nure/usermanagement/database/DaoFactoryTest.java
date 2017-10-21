package ua.nure.usermanagement.database;

import junit.framework.TestCase;
import ua.nure.usermanagement.User;

public class DaoFactoryTest extends TestCase {

    private DaoFactory factory;

    public void setUp() throws Exception {
        super.setUp();
        factory = DaoFactory.getInstance();
    }

    public void testGetUserDao() throws Exception {
        assertNotNull("DaoFactory is null", factory);
        UserDao dao = factory.getUserDao();
        assertNotNull("UserDao instance is null", dao);

    }

}