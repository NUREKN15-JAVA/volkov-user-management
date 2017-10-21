package ua.nure.usermanagement.database.exception;

import java.sql.SQLException;

public class DatabaseException extends Exception {
    public DatabaseException(SQLException e) {
        super(e);
    }

    public DatabaseException() {

    }

    public DatabaseException(String s) {
        super(s);
    }
}
