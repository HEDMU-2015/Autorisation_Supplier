package api;

import java.util.List;
import java.util.Optional;

import domain.Organisation;
import domain.Permission;
import domain.User;
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
		return new LogicTrans<List<UserPermission>>(dataAccess)
				.transaction(()-> securityMapper.fetchAllUserPermissions(email, dataAccess));
	
	}

	@Override
	public boolean login(String userId, String encryptedPassword) throws PersistenceFailureException {		
		DataAccess da = new DataAccessImpl();
		return new LogicTrans<Boolean>(da).transaction(()->checkUser(da, userId, encryptedPassword));	
	}
	
	private boolean checkUser(DataAccess dataAccess, String userId, String encryptedPassword) {
	
		Optional<Boolean> loginChecked = securityMapper.login(userId, encryptedPassword, dataAccess);

		if(loginChecked.get()==true){
			LoginSingleton.instance().setUserEmail(userId);
		}			
		return loginChecked.get();

	} 

	@Override
	public User getUser(String userId) throws PersistenceFailureException {
		DataAccess dataAccess =  new DataAccessImpl();		
		return new LogicTrans<Optional<User>>(dataAccess)
				.transaction(()-> securityMapper.getUser(userId, dataAccess)).get();
	
	}

	@Override
	public String getIdOfUserLoggedIn() throws PersistenceFailureException {
		if(LoginSingleton.instance().getUserEmail() != null){
			return LoginSingleton.instance().getUserEmail();
		}else{
			throw new RuntimeException("getIdOfUserLoggedIn() : No loggedIn user found");
		}		
	}

	@Override
	public Permission getPermission(int permissionId) throws PersistenceFailureException {
		DataAccess dataAccess =  new DataAccessImpl();		
		return new LogicTrans<Optional<Permission>>(dataAccess)
				.transaction(()-> securityMapper.getPermission(permissionId, dataAccess)).get();
	
	}

	@Override
	public Permission getPermission(String permissionName) throws PersistenceFailureException {
		DataAccess dataAccess =  new DataAccessImpl();		
		return new LogicTrans<Optional<Permission>>(dataAccess)
				.transaction(()-> securityMapper.getPermission(permissionName, dataAccess)).get();
	
	}

	@Override
	public Organisation getOrganisationUnitForUserPermission(String userId, int userPermissionId)
			throws PersistenceFailureException {
		DataAccess dataAccess =  new DataAccessImpl();		
		return new LogicTrans<Optional<Organisation>>(dataAccess)
				.transaction(()-> securityMapper.getOrganisationUnitForUserPermission(userId, userPermissionId, dataAccess)).get();
	
	}

	@Override
	public boolean hasUserAccessToOrganisationUnit(String email, int permissionId, int organisationId) throws PersistenceFailureException {
		DataAccess dataAccess =  new DataAccessImpl();		
		return new LogicTrans<Optional<Boolean>>(dataAccess)
				.transaction(()-> securityMapper.hasUserAccessToOrganisationUnit(email, permissionId, organisationId, dataAccess)).get();
	
	}
	
}

//boolean loginChecked = false;
//DataAccess dataAccess =  new DataAccessImpl();		

//Optional<User> loginUser = securityMapper.getUser(encryptedPassword, dataAccess);
//Optional<Boolean> loginChecked = securityMapper.login(userId, encryptedPassword, dataAccess);
//if (loginUser.isPresent()) {
//	loginChecked = true;
//	User user = new UserImpl();
//	user.setEmail(userId);
//	LoginSingleton.instance().setUser(user);
//}
//return loginChecked;
//
