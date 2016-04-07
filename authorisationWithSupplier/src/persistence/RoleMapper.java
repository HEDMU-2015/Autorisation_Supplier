package persistence;

import java.util.Optional;

import domain.Role;
import exception.PersistenceFailureException;

public interface RoleMapper {

	public void createRole(Role role, DataAccess dataAccess) throws PersistenceFailureException;

	public void deleteRole(Role role, DataAccess dataAccess) throws PersistenceFailureException;

	public Optional<Role> readRole(int id, DataAccess dataAccess) throws PersistenceFailureException;

	public void updateRole(Role role, DataAccess dataAccess) throws PersistenceFailureException;

}
