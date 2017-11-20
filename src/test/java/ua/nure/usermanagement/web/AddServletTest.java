package ua.nure.usermanagement.web;

import org.junit.Test;
import ua.nure.usermanagement.User;

import java.text.DateFormat;

import static org.junit.Assert.assertNotNull;

/**
 * An implementation of a {@link MockServletTestCase} for the {@link AddServlet}
 */
public class AddServletTest extends MockServletTestCase {

    /**
     * Sets up a new version of an {@link AddServlet} for each test
     *
     * @throws Exception If any malfunction occurs
     */
    @Override
    public void setUp() throws Exception {
        super.setUp();
        createServlet(AddServlet.class);
    }

    /**
     * Tests basic functionality of {@link AddServlet}, which is
     * adding an entry given the correct input of all parameters
     * and a proper signal
     */
    @Test
    public void testAdd() {
        User user = createJerry();
        User initUser = new User(user);
        initUser.setId(null);
        getMockUserDao().expectAndReturn("create", initUser, user);
        addRequestParameter("firstName", user.getFirstName());
        addRequestParameter("lastName", user.getLastName());
        addRequestParameter("dateOfBirth", DateFormat.getDateInstance().format(user.getDateOfBirth()));
        addRequestParameter("ok", "Ok");
        doPost();
    }

    /**
     * Tests a case, when firstName parameter wasn't added to the request
     */
    @Test
    public void testAddEmptyFirstName() {
        User user = createJerry();
        addRequestParameter("dateOfBirth", DateFormat.getDateInstance().format(user.getDateOfBirth()));
        addRequestParameter("lastName", user.getLastName());
        addRequestParameter("ok", "Ok");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull(errorMessage);
    }

    /**
     * Tests a case, when lastName parameter wasn't added to the request
     */
    @Test
    public void testAddEmptyLastName() {
        User user = createJerry();
        addRequestParameter("dateOfBirth", DateFormat.getDateInstance().format(user.getDateOfBirth()));
        addRequestParameter("firstName", user.getFirstName());
        addRequestParameter("ok", "Ok");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull(errorMessage);
    }

    /**
     * Tests a case, when dateOfBirth parameter is in a wrong format (must be date format by default)
     */
    @Test
    public void testAddIncorrectDateOfBirth() {
        User user = createJerry();
        addRequestParameter("lastName", user.getLastName());
        addRequestParameter("firstName", user.getFirstName());
        addRequestParameter("dateOfBirth", "bla-bla-bla");
        addRequestParameter("ok", "Ok");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull(errorMessage);
    }
}
