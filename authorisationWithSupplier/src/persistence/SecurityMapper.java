package persistence;

import java.util.List;
import java.util.Optional;

import domain.Organisation;
import domain.Permission;
import domain.User;
import domain.UserPermission;
import exception.PersistenceFailureException;

public interface SecurityMapper {
public List<UserPermission> fetchAllUserPermissions(String email, DataAccess dataAccess) throws PersistenceFailureException;
	
 	public Optional<Boolean> login	(String userId, String encryptedPassword, DataAccess dataAccess) throws PersistenceFailureException;  
 	 
 	public Optional<User> getUser(String userId, DataAccess dataAccess) throws PersistenceFailureException; 
 	  	 
 	public Optional<Permission> getPermission(int permissionId, DataAccess dataAccess) throws PersistenceFailureException; 
 
 	public Optional<Permission> getPermission(String permissionName, DataAccess dataAccess) throws PersistenceFailureException; 
 	
 	public Optional<Organisation> getOrganisationUnitForUserPermission(String userId, int userPermissionId, DataAccess dataAccess) throws PersistenceFailureException; 

	public Optional<Boolean> hasUserAccessToOrganisationUnit(String email, int permissionId, int organisationId, DataAccess dataAccess) throws PersistenceFailureException;

}
