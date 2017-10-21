package ua.nure.usermanagement.database;

import ua.nure.usermanagement.User;
import ua.nure.usermanagement.database.exception.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

class HSQLdbUserDao implements UserDao {
    public static final String INSERT_INTO_USERS_FIRSTNAME_LASTNAME_DATEOFBIRTH_VALUES = "insert INTO users(firstname,lastname,dateofbirth) values(?,?,?)";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String SELECT_ALL_USERS = "SELECT id,firstname,lastname,dateofbirth FROM users";
    public static final String UPDATE_USERS_SET_FIRSTNAME_LASTNAME_DATEOFBIRTH_WHERE_ID = "UPDATE users SET firstname = ?, lastname = ?, dateofbirth = ? WHERE id = ?";
    public static final String DELETE_FROM_USERS_WHERE_ID = "DELETE FROM users WHERE id = ?";
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

    @Override
    public User create(User user) throws DatabaseException {
        try {
            Connection connection = connectionFactory.createConnection();
            User createdUser = new User();
            PreparedStatement statement = connection.prepareStatement(INSERT_INTO_USERS_FIRSTNAME_LASTNAME_DATEOFBIRTH_VALUES);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setDate(3, new Date(user.getDateOfBirth().getTime()));
            int n = statement.executeUpdate();
            if (n != 1)
                throw new DatabaseException("Insertion not executed");
            CallableStatement callableStatement = connection.prepareCall("call IDENTITY()");
            ResultSet resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                createdUser = new User(user);
                createdUser.setId(new Long(resultSet.getLong(1)));
            }
            resultSet.close();
            callableStatement.close();
            statement.close();
            connection.close();
            return createdUser;
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } catch (DatabaseException e1) {
            throw e1;
        }
    }


    @Override
    public void update(User user) throws DatabaseException {
        try{
            Connection connection = connectionFactory.createConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SET_FIRSTNAME_LASTNAME_DATEOFBIRTH_WHERE_ID);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setDate(3, new Date(user.getDateOfBirth().getTime()));
            statement.setLong(4,user.getId());
            int n = statement.executeUpdate();
            if (n != 1)
                throw new DatabaseException("Update not executed");
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public void delete(User user) throws DatabaseException {
        try{
            Connection connection = connectionFactory.createConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_FROM_USERS_WHERE_ID);
            statement.setLong(1,user.getId());
            int n = statement.executeUpdate();
            if (n != 1)
                throw new DatabaseException("Delete not executed");
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }

    }

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

    @Override
    public Collection<User> findAll() throws DatabaseException {
        Collection<User> userList = new ArrayList<>();
        try {
            Connection connection = connectionFactory.createConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_USERS);
            while (resultSet.next()) {
                User createdUser = new User();
                createdUser.setId(new Long(resultSet.getLong(1)));
                createdUser.setFirstName(resultSet.getString(2));
                createdUser.setLastName(resultSet.getString(3));
                createdUser.setDateOfBirth(new java.util.Date(resultSet.getDate(4).getTime()));
                userList.add(createdUser);
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
}
