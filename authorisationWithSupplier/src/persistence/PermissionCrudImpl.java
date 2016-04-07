package persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import domain.Permission;
import domain.PermissionImpl;
import exception.PersistenceFailureException;
import util.CleanUpforSQL;

public class PermissionCrudImpl implements Crud<Permission, Integer> {

	private final static String CREATE_PERMISSION = "INSERT INTO permission(name) VALUES(?)";
	private final static String DELETE_PERMISSION = "DELETE FROM permission WHERE id = ?";
	private final static String READ_PERMISSION = "SELECT FROM permission WHERE id = ?";
	private final static String UPDATE_PERMISSION = "UPDATE permission SET name = ? WHERE id = ?";
	private CleanUpforSQL cleanup = new CleanUpforSQL();
	@Override
	public void create(DataAccess dataAccess, Permission permission) {
		PreparedStatement statement = null;
		try {
			statement = dataAccess.getConnection().prepareStatement(CREATE_PERMISSION);
			statement.setString(1, permission.getName());
			statement.execute();
		} catch (SQLException exc) {
			throw new RuntimeException("Query has failed!" , exc);
		} finally {
			cleanup.cleanup(statement);
		}
	}

	@Override
	public Optional<Permission> read(DataAccess dataAccess, Integer id) {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Optional<Permission> permission = Optional.empty();
		try {
			statement = dataAccess.getConnection().prepareStatement(READ_PERMISSION);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			if(resultSet.next()){
				Permission permission1 = new PermissionImpl();
				permission1.setId(resultSet.getInt("id"));
				permission1.setName(resultSet.getString("name"));
				permission = Optional.of(permission1);
			}

		} catch (SQLException exc) {
			try {
				throw new PersistenceFailureException("Query has failed!");
			} catch (PersistenceFailureException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} finally {
			cleanup.cleanup(resultSet, statement);

		}	
		return permission;

	}

	@Override
	public void update(DataAccess dataAccess, Permission permission) {
		PreparedStatement statement = null;
		try {
			statement = dataAccess.getConnection().prepareStatement(UPDATE_PERMISSION);
			statement.setString(1, permission.getName());
			statement.setInt(2, permission.getId());
			statement.execute();
		} catch (SQLException exc) {
			try {
				throw new PersistenceFailureException("Query has failed");
			} catch (PersistenceFailureException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} finally {
			cleanup.cleanup(statement);

		}
	}

	@Override
	public void delete(DataAccess dataAccess, Permission permission) {
		PreparedStatement statement = null;
		try {
			statement = dataAccess.getConnection().prepareStatement(DELETE_PERMISSION);
			statement.setInt(1, permission.getId());
			statement.execute();
		} catch (SQLException exc) {
			try {
				throw new PersistenceFailureException("Query has failed!");
			} catch (PersistenceFailureException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} finally {
			cleanup.cleanup(statement);

		}
	}
}
