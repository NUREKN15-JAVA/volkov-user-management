package ua.nure.usermanagement.web;

import org.junit.Test;
import ua.nure.usermanagement.User;

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class BrowseServletTest extends MockServletTestCase {


    public void setUp() throws Exception {
        super.setUp();
        createServlet(BrowseServlet.class);

    }


    public void tearDown() throws Exception {
        super.tearDown();
    }

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

    private User createJerry(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new User(1000L, "Jerry", "Smith", calendar.getTime());
    }
}
