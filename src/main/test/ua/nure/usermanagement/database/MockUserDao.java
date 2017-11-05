package ua.nure.usermanagement.database;

import ua.nure.usermanagement.User;
import ua.nure.usermanagement.database.exception.DatabaseException;

import java.util.Collection;
import java.util.HashMap;

/**
 * Implements a mock version of UserDao interface for testing purposes
 * @see ua.nure.usermanagement.database.UserDao
 */
public class MockUserDao implements UserDao {
    private HashMap<Long, User> users = new HashMap<>();
    private long id = 0;

    @Override
    public User create(User user) throws DatabaseException {
        id++;
        user.setId(id);
        users.put(id, user);
        return user;
    }

    @Override
    public void update(User user) throws DatabaseException {
        Long currentId = user.getId();
        users.remove(currentId);
        users.put(currentId, user);
    }

    @Override
    public void delete(User user) throws DatabaseException {
        Long currentId = user.getId();
        users.remove(currentId);
    }

    @Override
    public User find(Long id) throws DatabaseException {
        return users.get(id);
    }

    @Override
    public Collection<User> findAll() throws DatabaseException {
        return users.values();
    }

    @Override
    public void setConnectionFactory(ConnectionFactory factory) {
        //Empty on purpose
    }
}
