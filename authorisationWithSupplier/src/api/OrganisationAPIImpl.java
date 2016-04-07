package api;


import java.util.List;
import java.util.Optional;

import domain.Organisation;
import exception.PersistenceFailureException;
import persistence.DataAccess;
import persistence.DataAccessImpl;
import persistence.OrganisationMapper;
import persistence.OrganisationMapperImpl;
import util.LogicTrans;

public class OrganisationAPIImpl implements OrganisationAPI {
	OrganisationMapper organisationMapper = new OrganisationMapperImpl();

	@Override
	public List<Organisation> getChildren(int organisationID)
			throws PersistenceFailureException {
		DataAccess dataAccess =  new DataAccessImpl();		
		return new LogicTrans<List<Organisation>>(dataAccess).transaction(()-> organisationMapper.getChildren(organisationID, dataAccess));
	
	}

	@Override
	public List<Organisation> getOrganisationWithNoParents() throws PersistenceFailureException {
		DataAccess dataAccess =  new DataAccessImpl();		
		return new LogicTrans<List<Organisation>>(dataAccess).transaction(()-> organisationMapper.getOrganisationWithNoParents(dataAccess));
	
	}

	@Override
	public List<Integer> getAllChildren(int organisationID)  {
		DataAccess dataAccess =  new DataAccessImpl();		
		return new LogicTrans<List<Integer>>(dataAccess).transaction(()-> organisationMapper.getAllChildren(organisationID, dataAccess));	
	}

	@Override
	public Optional<Organisation> getOrganizationUnit(int organisationId) throws PersistenceFailureException {
		DataAccess dataAccess =  new DataAccessImpl();		
		return new LogicTrans<Optional<Organisation>>(dataAccess).transaction(()-> organisationMapper.getOrganizationUnit(organisationId, dataAccess));
	
	}

	@Override
	public List<Organisation> searchOrganization(String search)
			throws PersistenceFailureException {
		DataAccess dataAccess =  new DataAccessImpl();		
		return new LogicTrans<List<Organisation>>(dataAccess).transaction(()-> organisationMapper.searchOrganization(search, dataAccess));
	
	}	
}
