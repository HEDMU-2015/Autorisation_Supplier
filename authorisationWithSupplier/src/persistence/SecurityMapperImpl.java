package persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import domain.Organisation;
import domain.OrganisationImpl;
import domain.Permission;
import domain.PermissionImpl;
import domain.User;
import domain.UserImpl;
import domain.UserPermission;
import domain.UserPermissionImpl;
import exception.PersistenceFailureException;
import persistence.DataAccess;
import util.CleanUpforSQL;

public class SecurityMapperImpl implements SecurityMapper {
	private final static String FETCH_ALL_USER_PERMISSIONS = "SELECT * FROM userpermission where email = ? ";
	private final static String GET_USER = "SELECT * FROM user WHERE email = ?";
	private final static String GET_PERMISSION_BY_ID = "SELECT * FROM permission where id = ?";
	private final static String GET_PERMISSION_BY_NAME = "SELECT * FROM permission where name = ?";
	private final static String LOGIN_CHECK = "SELECT * FROM user WHERE email = ? AND password = ?";
	private final static String ORGANISATION_FOR_USER = "SELECT email, organisationid FROM userpermission "
			+ "where email = ? AND id = ?";
//	private final static String CHECK_USER_ACCESS = "SELECT * FROM userpermission where email = ? AND permissionid = ? AND organisationid = ?";
	private final static String CHECK_USER_ACCESS = "SELECT organisationID from UserPermission where email = ? AND permissionid = ? "
			+ "UNION select organisationid from UserRole "
			+ "inner join RolePermission on UserRole.roleid = Rolepermission.roleid "
			+ "where userrole.email = ? AND rolepermission.permissionid = ?";

	private CleanUpforSQL cleanup = new CleanUpforSQL();
 
	
	@Override
	public List<UserPermission> fetchAllUserPermissions(String email, DataAccess dataAccess)
			throws PersistenceFailureException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<UserPermission> permissions = new ArrayList<UserPermission>();
		try {
			statement = dataAccess.getConnection().prepareStatement(FETCH_ALL_USER_PERMISSIONS);
			statement.setString(1, email);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				UserPermission userPermission = new UserPermissionImpl();
				userPermission.setUserID(resultSet.getString("email"));

				Permission permission = new PermissionImpl();
				permission.setId(resultSet.getInt("permissionID"));
				permission.setName(resultSet.getString("permissionName"));

				userPermission.setPermission(permission);

				permissions.add(userPermission);
			}
			return permissions;
		} catch (SQLException exc) {
			throw new PersistenceFailureException("Query has failed");
		} finally {
			cleanup.cleanup(resultSet, statement);

		}
	}

	@Override
	public Optional<Boolean> login(String email, String encryptedPassword, DataAccess dataAccess)
			throws PersistenceFailureException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Optional<Boolean> user = Optional.of(false); 

		try {
			statement = dataAccess.getConnection().prepareStatement(LOGIN_CHECK);
			statement.setString(1, email);
			statement.setString(2, encryptedPassword);
			resultSet = statement.executeQuery();
			if(resultSet.next()){
				user = Optional.of(true);
			}
			
		} catch (SQLException exc) {
			throw new PersistenceFailureException("Query has failed");
		} finally {
			cleanup.cleanup(statement);
		}
		return user;
	}

	@Override
	public Optional<User> getUser(String userId, DataAccess dataAccess) throws PersistenceFailureException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Optional<User> user = Optional.empty();

		try {
			statement = dataAccess.getConnection().prepareStatement(GET_USER);
			statement.setString(1, userId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				User user1 = new UserImpl();
				user1.setEmail(resultSet.getString("email"));
				user1.setEncryptedPassword(resultSet.getString("password"));
				user = Optional.of(user1);
			}
		} catch (SQLException e) {
			throw new PersistenceFailureException("Query has failed!");
		} finally {
			cleanup.cleanup(resultSet, statement);
		}
		return user;
	}

	
	@Override
	public Optional<Permission> getPermission(int permissionId, DataAccess dataAccess) throws PersistenceFailureException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Optional<Permission> permission = Optional.empty();

		try {
			statement = dataAccess.getConnection().prepareStatement(GET_PERMISSION_BY_ID);
			statement.setInt(1, permissionId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Permission permission1 = new PermissionImpl();
				permission1.setId(resultSet.getInt("id"));
				permission1.setName(resultSet.getString("name"));
				permission = Optional.of(permission1);
			}
			return permission;
		} catch (SQLException e) {
			throw new PersistenceFailureException("Query has failed!");
		} finally {
			cleanup.cleanup(resultSet, statement);
		}
	}

	@Override
	public Optional<Permission> getPermission(String permissionName, DataAccess dataAccess) throws PersistenceFailureException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Optional<Permission> permission = Optional.empty();
		try {
			statement = dataAccess.getConnection().prepareStatement(GET_PERMISSION_BY_NAME);
			statement.setString(1, permissionName);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Permission permission1 = new PermissionImpl();
				permission1.setId(resultSet.getInt("id"));
				permission1.setName(resultSet.getString("name"));
				permission = Optional.of(permission1);
			}
		} catch (SQLException e) {
			throw new PersistenceFailureException("Query has failed!");
		} finally {
			cleanup.cleanup(resultSet, statement);
		}
		return permission;
	}

	@Override
	public Optional<Organisation> getOrganisationUnitForUserPermission(String userId, int userPermissionId, DataAccess dataAccess)
			throws PersistenceFailureException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Optional<Organisation> organisation = Optional.empty();

		try {
			statement = dataAccess.getConnection().prepareStatement(ORGANISATION_FOR_USER);
			statement.setString(1, userId);
			statement.setInt(2, userPermissionId);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Organisation organisation1 = new OrganisationImpl();
				organisation1.setId(resultSet.getInt("organisationID"));
				organisation1.setName(resultSet.getString("organisationName"));
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
	public Optional<Boolean> hasUserAccessToOrganisationUnit(String email, int permissionId, int organisationId,
			DataAccess dataAccess) throws PersistenceFailureException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Optional<Boolean> access = Optional.empty();
		List<Integer> organisationIDs = new ArrayList<>();
		OrganisationMapper organisationMapper = new OrganisationMapperImpl();
		List<Integer> organisationChildrenIDs = new ArrayList<>();

		

		try {
			statement = dataAccess.getConnection().prepareStatement(CHECK_USER_ACCESS);
			statement.setString(1, email);
			statement.setInt(2, permissionId);
			statement.setString(3, email);
			statement.setInt(4, permissionId);
			resultSet = statement.executeQuery();
			
			while (resultSet.next()){
				organisationIDs.add(resultSet.getInt("organisationID"));
				organisationChildrenIDs.addAll(organisationMapper.getAllChildren(organisationId, dataAccess));
			}
			
			if(organisationIDs.contains(organisationId)){
				boolean access1 = true;
				access = Optional.of(access1);
			}else if(organisationChildrenIDs.contains(organisationId)){
				boolean access1 = true;
				access = Optional.of(access1);			
			}
//			if (resultSet.next()) {
//				boolean access1 = true;
//				access = Optional.of(access1);
//			}
		} catch (SQLException exc) {
			throw new PersistenceFailureException("Query has failed");
		} finally {
			cleanup.cleanup(resultSet, statement);
		}
		return access;
	}

}
