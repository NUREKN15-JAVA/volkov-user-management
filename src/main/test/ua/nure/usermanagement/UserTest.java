package ua.nure.usermanagement;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class UserTest {

    private User user;
    private int birthYear;

    @Before
    public void setUp() throws Exception {
        user = new User();
    }

    @Test
    public void getFullName() {
        user.setFirstName("Yevhenii");
        user.setLastName("Volkov");
        assertEquals("Volkov, Yevhenii",user.getFullName());
    }

    @Test
    public void getFullNameIfNameIsEmpty(){
        try{
            assertEquals("",user.getFullName());
            fail("NullPointerException e");
        }catch (NullPointerException e){

        }
    }

    @Test
    public void getAge() throws Exception {
//        birthYear = 1997;
//        assertEquals(LocalDate.now().getYear() - birthYear,user.getAge());
    }

}