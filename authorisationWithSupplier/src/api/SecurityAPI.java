package api;

import java.util.List;
import java.util.Optional;

import domain.Organisation;
import domain.Permission;
import domain.User;
import domain.UserPermission;
import exception.PersistenceFailureException;

public interface SecurityAPI {
	/**
	 * 
	 * @param email
	 * @return List<UserPermission>
	 * @throws PersistenceFailureException
	 */

	public List<UserPermission> fetchAllUserPermissions(String email) throws PersistenceFailureException;
	
 	public boolean login(String userId, String encryptedPassword) throws PersistenceFailureException;  
 	 
 	public User getUser(String userId) throws PersistenceFailureException; 
 	 
 	public String getIdOfUserLoggedIn() throws PersistenceFailureException; 
 	 
 	public Permission getPermission(int permissionId) throws PersistenceFailureException; 
 
 	public Permission getPermission(String permissionName) throws PersistenceFailureException; 
 	
 	public Organisation getOrganisationUnitForUserPermission(String userId, int userPermissionId) throws PersistenceFailureException; 

	public Optional<Boolean> hasUserAccessToOrganisationUnit(String email, int permissionId, int organisationId) throws PersistenceFailureException;

}
