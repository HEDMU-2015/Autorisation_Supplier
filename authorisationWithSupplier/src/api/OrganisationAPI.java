package api;

import java.util.List;
import java.util.Optional;

import domain.Organisation;
import exception.PersistenceFailureException;

public interface OrganisationAPI {
	public List<Organisation> getChildren(int organisationID) throws PersistenceFailureException;

	public List<Organisation> getOrganisationWithNoParents() throws PersistenceFailureException;
	
	public List<Integer> getAllChildren(int id) ;
	
	public Optional<Organisation> getOrganizationUnit(int id) throws PersistenceFailureException;
	
	public List<Organisation> searchOrganization(String search) throws PersistenceFailureException;
	
}
