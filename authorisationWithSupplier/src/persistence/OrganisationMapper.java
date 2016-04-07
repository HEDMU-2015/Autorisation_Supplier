package persistence;

import java.util.List;
import java.util.Optional;

import domain.Organisation;
import exception.PersistenceFailureException;

public interface OrganisationMapper {
//	public List<Organisation> fetchAllOrganisation(DataAccess dataAccess) throws PersistenceFailureException;

	public List<Organisation> getChildren(int organisationID, DataAccess dataAccess) throws PersistenceFailureException;

	public List<Organisation> getOrganisationWithNoParents(DataAccess dataAccess) throws PersistenceFailureException;
	
	public List<Integer> getAllChildren(int organisationId,  DataAccess dataAccess) throws PersistenceFailureException;
	
	public Optional<Organisation> getOrganizationUnit(int organisationId,  DataAccess dataAccess) throws PersistenceFailureException;
	
	
	public List<Organisation> searchOrganization(String search,  DataAccess dataAccess) throws PersistenceFailureException;
	
}
