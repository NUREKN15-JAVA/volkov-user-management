package ua.nure.usermanagement.database;

import ua.nure.usermanagement.User;
import ua.nure.usermanagement.database.exception.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A UserDao implementation for HSQL database
 *
 * @see ua.nure.usermanagement.database.UserDao
 */
class HSQLdbUserDao implements UserDao {
    private static final String INSERT_INTO_USERS_FIRSTNAME_LASTNAME_DATEOFBIRTH_VALUES = "insert INTO users(firstname,lastname,dateofbirth) values(?,?,?)";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM users";
    private static final String UPDATE_USERS_SET_FIRSTNAME_LASTNAME_DATEOFBIRTH_WHERE_ID = "UPDATE users SET firstname = ?, lastname = ?, dateofbirth = ? WHERE id = ?";
    private static final String DELETE_FROM_USERS_WHERE_ID = "DELETE FROM users WHERE id = ?";
    private static final String SELECT_USERS_BY_FULL_NAME = "SELECT * FROM users WHERE FIRSTNAME = ? AND LASTNAME = ?";
    ConnectionFactory connectionFactory;

    public HSQLdbUserDao(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public HSQLdbUserDao() {
    }

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }


    /**
     * @param user all fields of user must be filled except of id
     *             id must be null
     * @return a copy of user, that contains id assigned to it by db
     * @throws DatabaseException
     */
    @Override
    public User create(User user) throws DatabaseException {
        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
            User createdUser = new User();
            PreparedStatement statement = connection.prepareStatement(INSERT_INTO_USERS_FIRSTNAME_LASTNAME_DATEOFBIRTH_VALUES);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setDate(3, new Date(user.getDateOfBirth().getTime()));
            int n = statement.executeUpdate();
            if (n != 1)
                throw new DatabaseException("Insertion not executed");
            connection.commit();
            CallableStatement callableStatement = connection.prepareCall("call IDENTITY()");
            ResultSet resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                createdUser = new User(user);
                createdUser.setId(new Long(resultSet.getLong(1)));
            }
            resultSet.close();
            callableStatement.close();
            statement.close();
            return createdUser;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } catch (DatabaseException e1) {
            throw e1;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Updates values of user entry, that has id that equals the the one in the parameter
     *
     * @param user all fields of user must be filled
     * @throws DatabaseException
     */
    @Override
    public void update(User user) throws DatabaseException {
        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SET_FIRSTNAME_LASTNAME_DATEOFBIRTH_WHERE_ID);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setDate(3, new Date(user.getDateOfBirth().getTime()));
            statement.setLong(4, user.getId());
            int n = statement.executeUpdate();
            if (n != 1)
                throw new DatabaseException("Update not executed");
            connection.commit();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Deletes user entry, that has id that equals the the one in the parameter
     *
     * @param user all fields of user must be filled
     * @throws DatabaseException
     */
    @Override
    public void delete(User user) throws DatabaseException {
        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_FROM_USERS_WHERE_ID);
            statement.setLong(1, user.getId());
            int n = statement.executeUpdate();
            if (n != 1)
                throw new DatabaseException("Delete not executed");
            connection.commit();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * Finds and returns a user entry based on its id
     *
     * @param id must not be null
     * @return user with id equal to the one, sent to the method
     * @throws DatabaseException
     */
    @Override
    public User find(Long id) throws DatabaseException {
        try {
            Connection connection = connectionFactory.createConnection();
            User createdUser = new User();
            PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                createdUser = new User();
                createdUser.setId(new Long(resultSet.getLong(1)));
                createdUser.setLastName(resultSet.getString(3));
                createdUser.setFirstName(resultSet.getString(2));
                createdUser.setDateOfBirth(new java.util.Date(resultSet.getDate(4).getTime()));
            }
            resultSet.close();
            statement.close();
            connection.close();
            return createdUser;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } catch (DatabaseException e1) {
            throw e1;
        }
    }

    /**
     * @return returns all entries in users table as a collection
     * @throws DatabaseException
     */
    @Override
    public Collection<User> findAll() throws DatabaseException {
        Collection<User> userList = new ArrayList<>();
        try {
            Connection connection = connectionFactory.createConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_USERS);
            while (resultSet.next()) {
                userList.add(extractData(resultSet));
            }
            resultSet.close();
            statement.close();
            connection.close();
            return userList;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } catch (DatabaseException e1) {
            throw e1;
        }
    }

    /**
     * Finds and returns a user entry based on its first name and last name
     *
     * @param firstName a first name of user. Must not be null
     * @param lastName a last name of user. Must not be null
     * @return A list of users, that have first name equal to {@code firstName} and
     * last name equal to {@code lastName}
     */
    @Override
    public Collection<User> find(String firstName, String lastName) {
        Collection<User> userList = new ArrayList<>();
        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_USERS_BY_FULL_NAME);
            statement.setString(1, firstName);
            statement.setString(2, lastName);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                userList.add(extractData(resultSet));
            }
            resultSet.close();
            statement.close();
            connection.close();
            return userList;
        } catch (DatabaseException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return userList;
    }

    /**
     * A utility method for extraction of data form a result set
     * @param resultSet a result set
     * @return A {@link User} object, that represents data, extracted
     * from {@code resultSet}
     * @throws SQLException if something is wrong with result set
     */
    private User extractData(ResultSet resultSet) throws SQLException {
        User createdUser = new User();
        createdUser.setId(new Long(resultSet.getLong(1)));
        createdUser.setFirstName(resultSet.getString(2));
        createdUser.setLastName(resultSet.getString(3));
        createdUser.setDateOfBirth(new java.util.Date(resultSet.getDate(4).getTime()));
        return createdUser;
    }
}
