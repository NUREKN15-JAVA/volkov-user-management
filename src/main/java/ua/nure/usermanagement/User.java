package ua.nure.usermanagement;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.Date;


public class User implements Comparable<User> {
    private Long id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;

    public User() {
    }

    public User(User user) {
        this.id = user.id;
        this.firstName = user.firstName;
        this.lastName = user.lastName;

        this.dateOfBirth = user.dateOfBirth;
    }

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
        return (LocalDate.now().getLong(ChronoField.EPOCH_DAY) - dateOfBirth.getTime() / 1000 / 60 / 60 / 24) / 365;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (!firstName.equals(user.firstName)) return false;
        if (!lastName.equals(user.lastName)) return false;
        return dateOfBirth.equals(user.dateOfBirth);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + dateOfBirth.hashCode();
        return result;
    }

    @Override
    public int compareTo(User o) {
        if (this.equals(o)) {
            return 0;
        }
        if (this.id < o.id) {
            return -1;
        }
        if (this.firstName.compareTo(o.firstName) < 0) {
            return -1;
        }
        if (this.lastName.compareTo(o.lastName) < 0) {
            return -1;
        }
        if (this.dateOfBirth.compareTo(o.dateOfBirth) < 0) {
            return -1;
        }
        return 1;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}
