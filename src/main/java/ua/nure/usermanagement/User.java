package ua.nure.usermanagement;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
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

    public long getAge() {
        return (LocalDate.now().getLong(ChronoField.EPOCH_DAY) - dateOfBirth.getTime()/1000/60/60/24)/365;
    }
}
