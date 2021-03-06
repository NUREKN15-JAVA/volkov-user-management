package ua.nure.usermanagement.database;

import ua.nure.usermanagement.User;
import ua.nure.usermanagement.database.exception.DatabaseException;

import java.util.Collection;

/**
 * An interface that defines DAO, that works with table users
 */
public interface UserDao {

    /**
     * add user into db
     * @param user all fields of user must be filled except of id
     *             id must be null
     * @return copy of user with id form db
     * @throws DatabaseException if has any problems with db
     */
    public User create(User user) throws DatabaseException;

    /**
     * update user in db
     * @param user all fields of user must be filled
     * @throws DatabaseException if has any problems with db
     */
    public void update(User user) throws DatabaseException;

    /**
     * delete user from db
     * @param user all fields of user must be filled
     * @throws DatabaseException if has any problems with db
     */
    public void delete(User user) throws DatabaseException;

    /**
     * Find user with id in db
     * @param id must not be null
     * @return a user with id
     * @throws DatabaseException
     */
    public User find(Long id) throws DatabaseException;

    /**
     *
     * @return returns a collection, that contains all data in table users of db
     * @throws DatabaseException
     */
    public Collection<User> findAll() throws DatabaseException;

    /**
     *
     * @param factory a factory that will be used to generate connections for this DAO
     */
    public void setConnectionFactory(ConnectionFactory factory);
}
