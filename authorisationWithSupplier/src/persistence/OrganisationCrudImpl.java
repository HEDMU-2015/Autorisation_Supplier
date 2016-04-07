package persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import domain.Organisation;
import domain.OrganisationImpl;
import exception.PersistenceFailureException;
import util.CleanUpforSQL;

public class OrganisationCrudImpl implements Crud<Organisation, Integer> {
	private final static String CREATE_ORGANISATION = "INSERT INTO organisation(name, parentID) VALUES(?, ?)";
	private final static String DELETE_ORGANISATION = "DELETE FROM organisation WHERE id = ?";
	private final static String READ_ORGANISATION = "SELECT * FROM organisation WHERE id = ?";
	private final static String UPDATE_ORGANISATION = "UPDATE organisation SET name = ? WHERE id = ?";
	private CleanUpforSQL cleanup = new CleanUpforSQL();

	@Override
	public void create(DataAccess dataAccess, Organisation organisation) {
		PreparedStatement statement = null;
		try {
			statement = dataAccess.getConnection().prepareStatement(CREATE_ORGANISATION);
			statement.setString(1, organisation.getName());
			statement.setInt(2, organisation.getParentID());
			statement.execute();

		} catch (SQLException exc) {
			try {
				throw new PersistenceFailureException("Query has failed!");
			} catch (PersistenceFailureException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// throw new RuntimeException ("SQLException caught", exc);
		} finally {
			cleanup.cleanup(statement);
		}
	}

	@Override
	public Optional<Organisation> read(DataAccess dataAccess, Integer id) {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Optional<Organisation> organisation = Optional.empty();

		try {

			statement = dataAccess.getConnection().prepareStatement(READ_ORGANISATION);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				Organisation org = new OrganisationImpl();
				org.setId(resultSet.getInt("id"));
				org.setName(resultSet.getString("name"));
				org.setParentID(resultSet.getInt("parentID"));
				organisation = Optional.of(org);
			} 			
			
		} catch (SQLException exc) {
			 try {
			 throw new PersistenceFailureException("Query has failed!");
			 } catch (PersistenceFailureException e) {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
			 }
			// throw new RuntimeException ("SQLException caught", exc);

		} finally {
			cleanup.cleanup(resultSet, statement);
		}
		
		return organisation;
	}

	@Override
	public void update(DataAccess dataAccess, Organisation organisation) {
		PreparedStatement statement = null;
		try {
			statement = dataAccess.getConnection().prepareStatement(UPDATE_ORGANISATION);
			statement.setString(1, organisation.getName());
			statement.setInt(2, organisation.getId());
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
	public void delete(DataAccess dataAccess, Organisation organisation) {
		PreparedStatement statement = null;
		try {
			statement = dataAccess.getConnection().prepareStatement(DELETE_ORGANISATION);
			statement.setInt(1, organisation.getId());
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

	// private void cleanup(ResultSet rs, PreparedStatement stmt) {
	// if (rs != null) {
	// try {
	// rs.close();
	// } catch (SQLException e) {
	// }
	// }
	// if (stmt != null) {
	// try {
	// stmt.close();
	// } catch (SQLException e) {
	// }
	// }
	//
	// }
	//
	// private void cleanup(PreparedStatement stmt) {
	// if (stmt != null) {
	// try {
	// stmt.close();
	// } catch (SQLException e) {
	// }
	// }
	//
	// }

}
