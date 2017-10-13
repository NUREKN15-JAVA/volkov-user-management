package ua.nure.usermanagement;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

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

    /**
     * Рассчётная формула в Expected как две капли воды похожа на ту, что используется в методе {@code user.getAge()}.
     * Причина тому в том, что это единственный нормальный способ точного получения возраста человека, по крайней мере
     * с использованием класса {@code Date}
     *
     */
    @Test
    public void getAge()  {
        birthYear = 1997;
        Calendar calendar = Calendar.getInstance();
        calendar.set(birthYear,Calendar.OCTOBER,24);
        user.setDateOfBirth(new Date(calendar.getTimeInMillis()));
        assertEquals((LocalDate.now().getLong(ChronoField.EPOCH_DAY) - calendar.getTimeInMillis()/1000/60/60/24)/365, user.getAge());
    }

}