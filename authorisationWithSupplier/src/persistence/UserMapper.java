package persistence;

import java.util.Optional;

import domain.User;
import exception.PersistenceFailureException;

public interface UserMapper {

	public void createUser(User user, DataAccess dataAccess) throws PersistenceFailureException;

	public void deleteUser(User user, DataAccess dataAccess) throws PersistenceFailureException;

	public Optional<User> readUser(String email, DataAccess dataAccess) throws PersistenceFailureException;

	public void updateUser(User user, DataAccess dataAccess) throws PersistenceFailureException;

}
