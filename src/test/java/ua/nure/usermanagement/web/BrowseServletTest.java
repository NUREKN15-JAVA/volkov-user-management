package ua.nure.usermanagement.web;

import org.junit.Test;
import ua.nure.usermanagement.User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

/**
 * An implementation of a {@code MockServletTestCase} for the BrowseServlet
 */
public class BrowseServletTest extends MockServletTestCase {

    /**
     * Sets up a new version of a BrowseServlet for each test
     *
     * @throws Exception If any malfunction occurs
     */
    public void setUp() throws Exception {
        super.setUp();
        createServlet(BrowseServlet.class);

    }

    /**
     * Tests basic functionality of BrowseServlet, which is visualising
     * all the entries in "users" table of the database
     */
    @Test
    public void testBrowse() {
        User user = createJerry();
        List<User> usersList = Collections.singletonList(user);
        getMockUserDao().expectAndReturn("findAll", usersList);
        doGet();
        Collection collection = (Collection) getWebMockObjectFactory().getMockSession().getAttribute("users");
        assertNotNull(collection);
        assertSame(usersList, collection);
    }

    /**
     * Test a case, when user sends a request of editing a selected entry
     */
    @Test
    public void testEdit() {
        User user = createJerry();
        getMockUserDao().expectAndReturn("find", 1000L, user);
        addRequestParameter("id", String.valueOf(1000L));
        addRequestParameter("edit", "Edit");
        doPost();
        User returnedUser = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
        assertNotNull(returnedUser);
        assertSame(user, returnedUser);
    }

    /**
     * Test a case, when user sends a request of deleting a selected entry
     */
    @Test
    public void testDelete() {
        User user = createJerry();
        getMockUserDao().expectAndReturn("find", 1000L, user);
        addRequestParameter("id", String.valueOf(1000L));
        addRequestParameter("delete", "Delete");
        doPost();
        User returnedUser = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
        assertNotNull(returnedUser);
        assertSame(user, returnedUser);
    }

    /**
     * Test a case, when user sends a request of showing detailed information of a selected entry
     */
    @Test
    public void testDetails() {
        User user = createJerry();
        getMockUserDao().expectAndReturn("find", 1000L, user);
        addRequestParameter("id", String.valueOf(1000L));
        addRequestParameter("details", "Details");
        doPost();
        User returnedUser = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
        assertNotNull(returnedUser);
        assertSame(user, returnedUser);
    }
}
