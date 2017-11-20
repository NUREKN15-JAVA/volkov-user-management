package ua.nure.usermanagement.web;

import org.junit.Test;
import ua.nure.usermanagement.User;

import java.text.DateFormat;
import java.util.Calendar;

import static org.junit.Assert.assertNotNull;

public class AddServletTest extends MockServletTestCase {
    @Override
    public void setUp() throws Exception {
        super.setUp();
        createServlet(AddServlet.class);
    }

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

    @Test
    public void testAddEmptyDateOfBirth() {
        User user = createJerry();
        addRequestParameter("lastName", user.getLastName());
        addRequestParameter("firstName", user.getFirstName());
        addRequestParameter("dateOfBirth", "bla-bla-bla");
        addRequestParameter("ok", "Ok");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull(errorMessage);
    }

    private User createJerry() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        return new User(1000L, "Jerry", "Smith", calendar.getTime());
    }
}
