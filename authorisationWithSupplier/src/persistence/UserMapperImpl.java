package persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import domain.Organisation;
import domain.User;
import domain.UserImpl;
import exception.PersistenceFailureException;
import util.CleanUpforSQL;

public class UserMapperImpl implements UserMapper{
	private final static String CREATE_USER = "INSERT INTO user(email, password) VALUES(?, ?)";
	private final static String DELETE_USER = "DELETE FROM user WHERE email = ?";
	private final static String READ_USER = "SELECT * FROM user WHERE email = ?";
	private final static String UPDATE_USER =  "UPDATE user SET password = ? WHERE email = ?";
	
	private CleanUpforSQL cleanup = new CleanUpforSQL();
	
	@Override
	public void createUser(User user, DataAccess dataAccess) throws PersistenceFailureException {
		PreparedStatement statement = null;
		try {
			statement = dataAccess.getConnection().prepareStatement(CREATE_USER);
			statement.setString(1, user.getEmail());
			statement.setString(2, user.getEncryptedPassword());
			statement.execute();
		} catch (SQLException exc) {
			throw new PersistenceFailureException("Query has failed!");
		} finally {
			cleanup.cleanup(statement);

		}

	}

	@Override
	public void deleteUser(User user, DataAccess dataAccess) throws PersistenceFailureException {
		PreparedStatement statement = null;
		try {
			statement = dataAccess.getConnection().prepareStatement(DELETE_USER);
			statement.setString(1, user.getEmail());
			statement.execute();
		} catch (SQLException exc) {
			throw new PersistenceFailureException("Query has failed!");
		} finally {
			cleanup.cleanup(statement);

		}		
	}

	@Override
	public Optional<User> readUser(String email, DataAccess dataAccess) throws PersistenceFailureException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Optional<User> user = Optional.empty();

		try {
			statement = dataAccess.getConnection().prepareStatement(READ_USER);
			statement.setString(1, email);
			resultSet = statement.executeQuery();
			if(resultSet.next()){
				User user1 = new UserImpl();
				user1.setEmail(resultSet.getString("email"));
				user1.setEncryptedPassword(resultSet.getString("password"));
				user1.setLogin(resultSet.getBoolean("login"));
				user = Optional.of(user1);
			}
		} catch (SQLException exc) {
			throw new PersistenceFailureException("Query has failed!");
		} finally {
			cleanup.cleanup(resultSet, statement);

		}
		return user;

	}

	@Override
	public void updateUser(User user, DataAccess dataAccess) throws PersistenceFailureException {
		PreparedStatement statement = null;
		try {
			statement = dataAccess.getConnection().prepareStatement(UPDATE_USER);
			statement.setString(1, user.getEncryptedPassword());
			statement.setString(2, user.getEmail());
			statement.execute();
			statement.close();
		} catch (SQLException exc) {
			throw new PersistenceFailureException("Query has failed");
		} finally {
			cleanup.cleanup(statement);

		}
	}
}


