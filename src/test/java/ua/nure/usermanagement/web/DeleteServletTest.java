package ua.nure.usermanagement.web;

import org.junit.Test;
import ua.nure.usermanagement.User;

/**
 * An implementation of a {@code MockServletTestCase} for the DeleteServlet
 */
public class DeleteServletTest extends MockServletTestCase {

    /**
     * Sets up a new instance of {@code DeleteServlet}
     *
     * @throws Exception If any malfunction occurs
     */
    @Override
    public void setUp() throws Exception {
        super.setUp();
        createServlet(DeleteServlet.class);

    }

    /**
     * Tests basic functionality of DeleteServlet, which is
     * deleting an entry if the there is a selected entry
     * (must be stored in a session scope) and proper signal is given
     */
    @Test
    public void testDelete() {
        User user = createJerry();
        getMockUserDao().expect("delete", user);
        setSessionAttribute("user", user);
        addRequestParameter("ok", "Ok");
        doPost();
    }
}
