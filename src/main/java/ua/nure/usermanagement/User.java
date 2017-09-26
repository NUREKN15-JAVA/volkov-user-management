package ua.nure.usermanagement;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;


public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFullName() {

        return lastName.concat(", ").concat(firstName);
    }

    public int getAge() throws Exception {
//        throw new Exception("Method is not implemented");
        Calendar calendar = Calendar.getInstance();
        LocalDate date = LocalDate.now();
        calendar.set(date.getYear(),date.getMonthValue(),date.getDayOfMonth());
        return 0;
    }
}
