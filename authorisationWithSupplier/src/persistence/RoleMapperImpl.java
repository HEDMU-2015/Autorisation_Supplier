package persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import domain.Role;
import domain.RoleImpl;
import exception.PersistenceFailureException;
import util.CleanUpforSQL;

public class RoleMapperImpl implements RoleMapper {
	
	private final static String CREATE_ROLE = "INSERT INTO role(name) VALUES(?)";
	private final static String DELETE_ROLE = "DELETE FROM role WHERE id = ?";
	private final static String READ_ROLE = "SELECT * FROM role WHERE id = ?";
	private final static String UPDATE_ROLE =  "UPDATE permission SET name = ? WHERE id = ?";
	private CleanUpforSQL cleanup = new CleanUpforSQL();
	
	@Override
	public void createRole(Role role, DataAccess dataAccess) throws PersistenceFailureException {
		PreparedStatement statement = null;
		try {
			statement = dataAccess.getConnection().prepareStatement(CREATE_ROLE);
			statement.setString(1, role.getName());
			statement.setInt(2, role.getId());
			statement.execute();
			
		} catch (SQLException exc) {
			throw new PersistenceFailureException("Query has failed!");
		} finally {
			cleanup.cleanup(statement);
		}
	}

	@Override
	public void deleteRole(Role role, DataAccess dataAccess) throws PersistenceFailureException {
		PreparedStatement statement = null;
		try {
			statement = dataAccess.getConnection().prepareStatement(DELETE_ROLE);
			statement.setInt(1, role.getId());
			statement.execute();
		} catch (SQLException exc) {
			throw new PersistenceFailureException("Query has failed!");
		} finally {
			cleanup.cleanup(statement);
		}
	}

	@Override
	public Optional<Role> readRole(int id, DataAccess dataAccess) throws PersistenceFailureException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Optional<Role> role = Optional.empty();
		try {
			statement = dataAccess.getConnection().prepareStatement(READ_ROLE);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			if(resultSet.next()){
				Role role1 = new RoleImpl();
				role1.setId(resultSet.getInt("id"));
				role1.setName(resultSet.getString("name"));
				role = Optional.of(role1);
			}		
		} catch (SQLException e) {
			throw new PersistenceFailureException("Query has failed!");
		} finally {
			cleanup.cleanup(resultSet, statement);
		}
		return role;

	}

	@Override
	public void updateRole(Role role, DataAccess dataAccess) throws PersistenceFailureException {
		PreparedStatement statement = null;
		try {
			statement = dataAccess.getConnection().prepareStatement(UPDATE_ROLE);
			statement.setString(1, role.getName());
			statement.setInt(2, role.getId());
			statement.execute();
		} catch (SQLException exc) {
			throw new PersistenceFailureException("Query has failed");
		} finally {
			cleanup.cleanup(statement);
		}
	}

}
