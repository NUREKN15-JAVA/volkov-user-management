package ua.nure.usermanagement.database;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.junit.Before;
import ua.nure.usermanagement.User;
import ua.nure.usermanagement.database.exception.DatabaseException;

import java.util.*;

public class HSQLdbUserDaoTest extends DatabaseTestCase {

    private HSQLdbUserDao dao;
    private ConnectionFactory connectionFactory;
    private final int AMOUNT_OF_ENTRIES_IN_DB = 2;

    @Override
    protected IDatabaseConnection getConnection() throws Exception {
        connectionFactory = new ConnectionFactoryImpl("org.hsqldb.jdbcDriver", "jdbc:hsqldb:file:db/usermanager", "sa", "");
        return new DatabaseConnection(connectionFactory.createConnection());
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new XmlDataSet(getClass().getClassLoader().getResourceAsStream("usersDataSet.xml"));
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        dao = new HSQLdbUserDao(connectionFactory);
    }


    public void testCreate() {
        try {
            User user = new User();
            user.setFirstName("Jerry");
            user.setLastName("Smith");
            user.setDateOfBirth(new Date());
            assertNull(user.getId());
            User createdUser = dao.create(user);
            assertNotNull(createdUser);
            assertNotNull(createdUser.getId());
            assertEquals(user.getFullName(), createdUser.getFullName());
        } catch (DatabaseException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    public void testUpdate() throws Exception {
    }

    public void testDelete() throws Exception {
    }

    public void testFind() throws Exception {
    }

    public void testFindAll() throws Exception {
        assertNotNull(dao.findAll());
        assertEquals("Collection size: ",AMOUNT_OF_ENTRIES_IN_DB, dao.findAll().size());
    }

}