package ua.nure.usermanagement;

import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * A test case for checking User class
 */
public class UserTest {

    private User user;
    private int birthYear;

    @Before
    public void setUp() throws Exception {
        user = new User();
    }

    /**
     * Checks general algorithm for getFullName method.
     */
    @Test
    public void getFullName() {
        user.setFirstName("Yevhenii");
        user.setLastName("Volkov");
        assertEquals("Volkov, Yevhenii",user.getFullName());
    }

    /**
     * Checks, if the method throws exception, if any of first or last name weren't added.
     */
    @Test
    public void getFullNameIfNameIsEmpty(){
        try{
            assertEquals("",user.getFullName());
            fail("NullPointerException e");
        }catch (NullPointerException e){

        }
    }

    /**
     * Checks, whether method getAge returns correct age of a user
     */
    @Test
    public void getAge()  {
        birthYear = 1997;
        Calendar calendar = Calendar.getInstance();
        calendar.set(birthYear,Calendar.OCTOBER,24);
        user.setDateOfBirth(new Date(calendar.getTimeInMillis()));
        assertEquals((LocalDate.now().getYear() - birthYear), user.getAge());
    }

}