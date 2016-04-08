package api;

import java.util.List;
import java.util.Optional;

import domain.Organisation;
import domain.Permission;
import domain.User;
import domain.UserImpl;
import domain.UserPermission;
import exception.PersistenceFailureException;
import persistence.DataAccess;
import persistence.DataAccessImpl;
import persistence.SecurityMapper;
import persistence.SecurityMapperImpl;
import util.LogicTrans;
import util.LoginSingleton;

public class SecurityAPIImpl implements SecurityAPI {
	SecurityMapper securityMapper = new SecurityMapperImpl();

	@Override
	public List<UserPermission> fetchAllUserPermissions(String email) throws PersistenceFailureException {
		DataAccess dataAccess =  new DataAccessImpl();		
		return new LogicTrans<List<UserPermission>>(dataAccess).transaction(()-> securityMapper.fetchAllUserPermissions(email, dataAccess));
	
	}

	@Override
	public boolean login(String userId, String encryptedPassword) throws PersistenceFailureException {		
		DataAccess da = new DataAccessImpl();
		return new LogicTrans<Boolean>(da).transaction(()->checkUser(userId, encryptedPassword));	
	}
	
	private Boolean checkUser(String userId, String encryptedPassword) {
		boolean loginChecked = false;
		DataAccess dataAccess =  new DataAccessImpl();		

		Optional<User> loginUser = securityMapper.getUser(encryptedPassword, dataAccess);
		if (loginUser.isPresent()) {
			loginChecked = true;
			User user = new UserImpl();
			user.setEmail(userId);
			LoginSingleton.instance().setUser(user);
		}
		return loginChecked;
		
	} 

	@Override
	public Optional<User> getUser(String userId) throws PersistenceFailureException {
		DataAccess dataAccess =  new DataAccessImpl();		
		return new LogicTrans<Optional<User>>(dataAccess).transaction(()-> securityMapper.getUser(userId, dataAccess));
	
	}

	@Override
	public String getIdOfUserLoggedIn() throws PersistenceFailureException {
		return LoginSingleton.instance().getUser().getEmail();
	}

	@Override
	public Optional<Permission> getPermission(int permissionId) throws PersistenceFailureException {
		DataAccess dataAccess =  new DataAccessImpl();		
		return new LogicTrans<Optional<Permission>>(dataAccess).transaction(()-> securityMapper.getPermission(permissionId, dataAccess));
	
	}

	@Override
	public Optional<Permission> getPermission(String permissionName) throws PersistenceFailureException {
		DataAccess dataAccess =  new DataAccessImpl();		
		return new LogicTrans<Optional<Permission>>(dataAccess).transaction(()-> securityMapper.getPermission(permissionName, dataAccess));
	
	}

	@Override
	public Optional<Organisation> getOrganisationUnitForUserPermission(String userId, int userPermissionId)
			throws PersistenceFailureException {
		DataAccess dataAccess =  new DataAccessImpl();		
		return new LogicTrans<Optional<Organisation>>(dataAccess).transaction(()-> securityMapper.getOrganisationUnitForUserPermission(userId, userPermissionId, dataAccess));
	
	}

	@Override
	public Optional<Boolean> hasUserAccessToOrganisationUnit(String email, int permissionId, int organisationId) throws PersistenceFailureException {
		DataAccess dataAccess =  new DataAccessImpl();		
		return new LogicTrans<Optional<Boolean>>(dataAccess).transaction(()-> securityMapper.hasUserAccessToOrganisationUnit(email, permissionId, organisationId, dataAccess));
	
	}

	
}
