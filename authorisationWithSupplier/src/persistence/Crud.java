package persistence;

import java.util.Optional;

import domain.Organisation;
import exception.PersistenceFailureException;

//import java.util.List;


public interface Crud <D, K>{ //no infomation but just input type, and key .return, D: domain object
	
//	public void create(DataAccess dataAccess, D domain)throws PersistenceFailureException;
//	public D read (DataAccess dataAccess, K key)throws PersistenceFailureException;
//	
//	public void update (DataAccess dataAccess,D domain)throws PersistenceFailureException;
//	
//	public void delete (DataAccess dataAccess,D domain)throws PersistenceFailureException;
//	
	
	
	public void create(DataAccess dataAccess, D domain);

	
	public Optional<D> read (DataAccess dataAccess, K key);
	
	public void update (DataAccess dataAccess,D domain);
	
	public void delete (DataAccess dataAccess,D domain);
	
	//public List <D> list(DataAccess DataAccess,String search); //Any method that are not used, throw new RuntimeException , "not implemented" e
	
}
