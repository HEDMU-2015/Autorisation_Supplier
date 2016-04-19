package api;

import java.util.List;

import domain.Organisation;
import exception.PersistenceFailureException;

/**
 * Organisation api
 * 
 * @author Juyoung Choi
 *
 */

public interface OrganisationAPI {
	/**
	 * 
	 * @param organisationID
	 * @return
	 * @throws PersistenceFailureException
	 */
	public List<Organisation> getChildren(int organisationID) throws PersistenceFailureException;

	public List<Organisation> getOrganisationWithNoParents() throws PersistenceFailureException;
	
	public List<Integer> getAllChildren(int id) ;
	
	public Organisation getOrganizationUnit(int id) throws PersistenceFailureException;
	
	public List<Organisation> searchOrganization(String search) throws PersistenceFailureException;
	
	public List<Organisation> getAllOrganisation() throws PersistenceFailureException;
}
