package ua.nure.usermanagement.web;

import org.junit.Test;
import ua.nure.usermanagement.User;

import java.util.Calendar;

public class DeleteServletTest extends MockServletTestCase {
    @Override
    public void setUp() throws Exception {
        super.setUp();
        createServlet(DeleteServlet.class);

    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testDelete() {
        User user = createJerry();
        getMockUserDao().expect("delete", user);
        setSessionAttribute("user", user);
        addRequestParameter("ok", "Ok");
        doPost();
    }

    private User createJerry() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        return new User(1000L, "Jerry", "Smith", calendar.getTime());
    }
}
