package ua.nure.usermanagement.web;

import org.junit.Test;

import static org.junit.Assert.*;

import ua.nure.usermanagement.User;

import java.text.DateFormat;
import java.util.Calendar;

public class EditServletTest extends MockServletTestCase {

    @Override
    public void setUp() throws Exception {
        super.setUp();
        createServlet(EditServlet.class);
    }

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

    @Test
    public void testEditEmptyDateOfBirth() {
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

    private User createJerry() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new User(1000L, "Jerry", "Smith", calendar.getTime());
    }
}
