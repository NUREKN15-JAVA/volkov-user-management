package ua.nure.usermanagement.web;

import org.junit.Test;

import static org.junit.Assert.*;

import ua.nure.usermanagement.User;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * An implementation of a {@code MockServletTestCase} for the {@link EditServlet}
 */
public class EditServletTest extends MockServletTestCase {

    /**
     * Sets up a new version of an {@link EditServlet} for each test
     *
     * @throws Exception If any malfunction occurs
     */
    @Override
    public void setUp() throws Exception {
        super.setUp();
        createServlet(EditServlet.class);
    }

    /**
     * Tests basic functionality of {@link EditServlet}, which is
     * updating an entry if the correct input and signal is given
     */
    @Test
    public void testEdit() {
        User user = createJerry();
        getMockUserDao().expect("update", user);
        addRequestParameter("id", user.getId().toString());
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
    public void testEditEmptyFirstName() {
        User user = createJerry();
        addRequestParameter("id", user.getId().toString());
        addRequestParameter("lastName", user.getLastName());
        addRequestParameter("dateOfBirth", DateFormat.getDateInstance().format(user.getDateOfBirth()));
        addRequestParameter("ok", "Ok");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull(errorMessage);
    }

    /**
     * Tests a case, when lastName parameter wasn't added to the request
     */
    @Test
    public void testEditEmptyLastName() {
        User user = createJerry();
        addRequestParameter("id", user.getId().toString());
        addRequestParameter("firstName", user.getFirstName());
        addRequestParameter("dateOfBirth", DateFormat.getDateInstance().format(user.getDateOfBirth()));
        addRequestParameter("ok", "Ok");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull(errorMessage);
    }

    /**
     * Tests a case, when dateOfBirth parameter is in a wrong format (must be date format by default)
     */
    @Test
    public void testEditIncorrectDateOfBirth() {
        User user = createJerry();
        addRequestParameter("id", user.getId().toString());
        addRequestParameter("firstName", user.getFirstName());
        addRequestParameter("lastName", user.getLastName());
        addRequestParameter("dateOfBirth", "bla-bla-bla");
        addRequestParameter("ok", "Ok");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull(errorMessage);
    }
}
