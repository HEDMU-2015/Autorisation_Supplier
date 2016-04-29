package persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import domain.Organisation;
import domain.OrganisationImpl;
import exception.PersistenceFailureException;
import util.CleanUpforSQL;

public class OrganisationMapperImpl implements OrganisationMapper {
	private final static String FETCH_ORGANISATIONS_CHILDREN = "SELECT * FROM organisation WHERE parentID = ?";
	private final static String FETCH_ORGANISATIONS_NO_PARENT = "SELECT * FROM organisation WHERE parentID is NULL";
	private final static String FETCH_ALL_CHILDREN = "WITH RECURSIVE tree (level, parent, child, name) "
			+ "AS (SELECT 1, parentid AS parent, id AS child, name FROM organisation where id = ? "
			+ "UNION SELECT level + 1, parentid, id, name FROM organisation, tree WHERE parentid = child) "
			+ "SELECT * FROM tree ";
	private final static String FETCH_ORGANISATION_UNIT = "SELECT * FROM organisation WHERE id = ?";
	private final static String SEARCH_ORGANISATIONS = "SELECT * FROM organisation"
			+ " where UPPER(name) LIKE ? ORDER BY id";
	private final static String FETCH_ALL =  "WITH RECURSIVE tree (level, parent, child, name) "
			+ "AS (SELECT 1, parentid AS parent, id AS child, name FROM organisation where parentid IS NULL "
			+ "UNION SELECT level + 1, parentid, id, name FROM organisation, tree WHERE parentid = child) "
			+ "SELECT * FROM tree";

//	private final static String FETCH_ALL = " SELECT parentid, parentName, id, name FROM organisation ,"
//			+ " (Select id, name as parentName from organisation) parent"
//			+ " where organisation.parentid = parent.id";
//	
	private CleanUpforSQL cleanup = new CleanUpforSQL();


	@Override
	public List<Organisation> getChildren(int organisationID, DataAccess dataAccess)
			throws PersistenceFailureException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Organisation> organisationsChildren = new ArrayList<Organisation>();
		try {
			statement = dataAccess.getConnection().prepareStatement(FETCH_ORGANISATIONS_CHILDREN);
			statement.setInt(1, organisationID);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Organisation organisation = new OrganisationImpl();
				organisation.setId(resultSet.getInt("id"));
				organisation.setName(resultSet.getString("name"));
				organisation.setParentID(resultSet.getInt("parentID"));
				organisationsChildren.add(organisation);
			}
			return organisationsChildren;
		} catch (SQLException exc) {
			throw new PersistenceFailureException("Query has failed");
		} finally {
			cleanup.cleanup(resultSet, statement);
		}
	}

	@Override
	public List<Organisation> getOrganisationWithNoParents(DataAccess dataAccess) throws PersistenceFailureException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Organisation> organisationsChildren = new ArrayList<Organisation>();
		try {
			statement = dataAccess.getConnection().prepareStatement(FETCH_ORGANISATIONS_NO_PARENT);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Organisation organisation = new OrganisationImpl();
				organisation.setId(resultSet.getInt("id"));
				organisation.setName(resultSet.getString("name"));
				organisation.setParentID(resultSet.getInt("parentID"));
				organisationsChildren.add(organisation);
			}
			return organisationsChildren;
		} catch (SQLException exc) {
			throw new PersistenceFailureException("Query has failed");
		} finally {
			cleanup.cleanup(resultSet, statement);

		}
	
	}

	@Override
	public List<Integer> getAllChildren(int id, DataAccess dataAccess) throws PersistenceFailureException {
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Integer> childrenIdList = new ArrayList<>();
		try {
			statement = dataAccess.getConnection().prepareStatement(FETCH_ALL_CHILDREN);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {				
				childrenIdList.add(resultSet.getInt("child"));				
			}
		} catch (SQLException exc) {
			throw new PersistenceFailureException("Query has failed");
		} finally {
			cleanup.cleanup(resultSet, statement);
		}
		return childrenIdList;

	}

	@Override
	public Optional<Organisation> getOrganizationUnit(int organisationId, DataAccess dataAccess) throws PersistenceFailureException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Optional<Organisation> organisation = Optional.empty();

		try {
			statement = dataAccess.getConnection().prepareStatement(FETCH_ORGANISATION_UNIT);
			statement.setInt(1, organisationId);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				Organisation organisation1 = new OrganisationImpl();
				organisation1.setId(resultSet.getInt("id"));
				organisation1.setName(resultSet.getString("name"));
				organisation1.setParentID(resultSet.getInt("parentID"));
				organisation = Optional.of(organisation1);
			}
		} catch (SQLException exc) {
			throw new PersistenceFailureException("Query has failed");
		} finally {
			cleanup.cleanup(resultSet, statement);
		}
		return organisation;
	}

	@Override
	public List<Organisation> searchOrganization(String search, DataAccess dataAccess)
			throws PersistenceFailureException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Organisation> organisationsChildren = new ArrayList<Organisation>();
		try {
			statement = dataAccess.getConnection().prepareStatement(SEARCH_ORGANISATIONS);
			statement.setString(1, "%" + search.toUpperCase() + "%");
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Organisation organisation = new OrganisationImpl();
				organisation.setId(resultSet.getInt("id"));
				organisation.setName(resultSet.getString("name"));
				organisation.setParentID(resultSet.getInt("parentID"));
				organisationsChildren.add(organisation);
			}
			return organisationsChildren;
		} catch (SQLException exc) {
			throw new PersistenceFailureException("Query has failed");
		} finally {
			cleanup.cleanup(resultSet, statement);

		}
	
	}

	@Override
	public List<Organisation> getAllOrganisation(DataAccess dataAccess) throws PersistenceFailureException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Organisation> allOrganisation = new ArrayList<>();
		try {
			statement = dataAccess.getConnection().prepareStatement(FETCH_ALL);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {				
				Organisation organisation = new OrganisationImpl();
				organisation.setLevel(resultSet.getInt("level"));
				organisation.setId(resultSet.getInt("child"));
//				organisation.setId(resultSet.getInt("id"));

				organisation.setName(resultSet.getString("name"));
				organisation.setParentID(resultSet.getInt("parent"));
//				organisation.setParentName(resultSet.getString("parentName"));
				allOrganisation.add(organisation);			
			}
		} catch (SQLException exc) {
			throw new PersistenceFailureException("Query has failed");
		} finally {
			cleanup.cleanup(resultSet, statement);
		}
		return allOrganisation;
	}

}
