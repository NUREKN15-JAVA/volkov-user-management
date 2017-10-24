package ua.nure.usermanagement.gui;

import com.mockobjects.dynamic.Mock;
import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import ua.nure.usermanagement.User;
import ua.nure.usermanagement.database.DaoFactory;
import ua.nure.usermanagement.database.MockDaoFactory;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.util.*;
import java.util.List;

public class MainFrameTest extends JFCTestCase {

    public static final int ROW_AMM = 2;
    private Container mainFrame;
    private final int COLUMN_AMM = 3;
    private Mock mockUserDao;
    private List<User> users;

    private Component find(Class componentClass, String name) {
        NamedComponentFinder finder = new NamedComponentFinder(componentClass, name);
        finder.setWait(0);
        Component component = finder.find(mainFrame, 0);
        assertNotNull(component);
        return component;
    }

    public void testBrowseControl() {
        find(JPanel.class, "browsePanel");
        JTable table = (JTable) find(JTable.class, "userTable");
        assertEquals(COLUMN_AMM, table.getColumnCount());
        assertEquals("ID", table.getColumnName(0));
        assertEquals("First name", table.getColumnName(0));
        assertEquals("Last name", table.getColumnName(0));
        assertEquals(ROW_AMM, table.getRowCount());
        find(JButton.class, "addButton");
        find(JButton.class, "editButton");
        find(JButton.class, "deleteButton");
        find(JButton.class, "detailsButton");
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        try {
            Properties properties = new Properties();
            properties.setProperty("dao.factory", MockDaoFactory.class.getName());
            DaoFactory.init(properties);
            mockUserDao = ((MockDaoFactory) DaoFactory.getInstance()).getMockUserDao();
            User expectedUser = new User("Rick", "Sanchez", new Date());
            users = Collections.singletonList(expectedUser);
            mockUserDao.expectAndReturn("findAll", users);
            setHelper(new JFCTestHelper());
            mainFrame = new MainFrame();
        }catch (Exception e){
            e.printStackTrace();
        }
        mainFrame.setVisible(true);
    }

    @Override
    protected void tearDown() throws Exception {
        mockUserDao.verify();
        mainFrame.setVisible(false);
        JFCTestHelper.cleanUp(this);
        super.tearDown();
    }

    public void testAddUser() {
        String firstName = "Jerry";
        String lastName = "Smith";
        Date birthDate = new Date();

        User user = new User(firstName, lastName, birthDate);
        User expectedUser = new User(new Long(1), firstName, lastName, birthDate);

        mockUserDao.expectAndReturn("create", user, expectedUser);

        List<User> users = new ArrayList<>(this.users);

        users.add(expectedUser);
        mockUserDao.expectAndReturn("findAll", users);

        JTable table = (JTable) find(JTable.class, "userTable");
        assertEquals(1, table.getRowCount());

        JButton addButton = (JButton) find(JButton.class, "addButton");
        getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

        find(JPanel.class, "addPanel");
        fillField(firstName, lastName, birthDate);

        find(JButton.class, "cancelButton");
        JButton okButton = ((JButton) find(JButton.class, "addButton"));

        getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

        find(JPanel.class, "browsePanel");

        table = (JTable) find(JTable.class, "userTable");
        assertEquals(2, table.getRowCount());

        mockUserDao.verify();
    }

    public void testAddUserIfCancel() {
        String firstName = "Jerry";
        String lastName = "Smith";
        Date birthDate = new Date();

        User user = new User(firstName, lastName, birthDate);
        User expectedUser = new User(new Long(1), firstName, lastName, birthDate);

        mockUserDao.expectAndReturn("create", user, expectedUser);

        List<User> users = new ArrayList<>(this.users);

        users.add(expectedUser);
        mockUserDao.expectAndReturn("findAll", users);

        JTable table = (JTable) find(JTable.class, "userTable");
        assertEquals(1, table.getRowCount());

        JButton addButton = (JButton) find(JButton.class, "addButton");
        getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

        find(JPanel.class, "addPanel");
        fillField(firstName, lastName, birthDate);

        JButton cancelButton = (JButton) find(JButton.class, "cancelButton");
        find(JButton.class, "addButton");

        getHelper().enterClickAndLeave(new MouseEventData(this, cancelButton));

        find(JPanel.class, "browsePanel");

        table = (JTable) find(JTable.class, "userTable");
        assertEquals(1, table.getRowCount());

        mockUserDao.verify();
    }

    private void fillField(String firstName, String lastName, Date birthDate) {
        JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
        JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
        JTextField dateOfBirthField = (JTextField) find(JTextField.class, "dateOfBirthField");

        getHelper().sendString(new StringEventData(this, firstNameField, firstName));
        getHelper().sendString(new StringEventData(this, lastNameField, lastName));
        String date = DateFormat.getDateInstance().format(birthDate);
        getHelper().sendString(new StringEventData(this, dateOfBirthField, date));


    }
    //Нужно ещё 6 методов на кнопки edit, remove, details
}
