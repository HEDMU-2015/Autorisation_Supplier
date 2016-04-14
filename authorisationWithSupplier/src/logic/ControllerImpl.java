package logic;

import java.util.Optional;

import domain.Organisation;
import domain.Permission;
import domain.Role;
import domain.User;
import persistence.Crud;
import persistence.DataAccess;
import persistence.DataAccessImpl;
import persistence.OrganisationCrudImpl;
import persistence.PermissionCrudImpl;
import persistence.RoleMapper;
import persistence.RoleMapperImpl;
import persistence.UserMapper;
import persistence.UserMapperImpl;
import util.LogicTrans;

public class ControllerImpl implements Controller {

	private Crud<Organisation, Integer> organisationCrud = new OrganisationCrudImpl();

	private Crud<Permission, Integer> permissionCrud = new PermissionCrudImpl();

	private UserMapper userMapper = new UserMapperImpl();
	private RoleMapper roleMapper = new RoleMapperImpl();

	@Override
	public void createOrganisation(Organisation organisation) {
		DataAccess dataAccess = new DataAccessImpl();
	
		new LogicTrans<>(dataAccess)
		.transaction(() -> organisationCrud.create(dataAccess, organisation));

	}

	@Override
	public void deleteOrganisation(Organisation organisation) {
		DataAccess dataAccess = new DataAccessImpl();
		new LogicTrans<Organisation>(dataAccess)
		.transaction(d -> organisationCrud.delete(dataAccess, d), organisation);

	}

	@Override
	public 	Optional<Organisation> readOrganisation(int id) {

		DataAccess dataAccess = new DataAccessImpl();

		return new LogicTrans<Optional<Organisation>>(dataAccess)
				.transaction(() -> organisationCrud.read(dataAccess, id));	

	}

	@Override
	public void updateOrganisation(Organisation organisation) {
		DataAccess dataAccess = new DataAccessImpl();
		new LogicTrans<Organisation>(dataAccess)
		.transaction(d -> organisationCrud.delete(dataAccess, d), organisation);
	}



	@Override
	public void createUser(User user) {
		DataAccess dataAccess = new DataAccessImpl();
		new LogicTrans<User>(dataAccess)
		.transaction(d -> userMapper.createUser(d, dataAccess), user);

	}

	@Override
	public void deleteUser(User user) {
		DataAccess dataAccess = new DataAccessImpl();
		new LogicTrans<User>(dataAccess)
		.transaction(d -> userMapper.deleteUser(d, dataAccess), user);
	}



	@Override
	public Optional<User> readUser(String email) {
		DataAccess dataAccess = new DataAccessImpl();

		return new LogicTrans<Optional<User>>(dataAccess).transaction(()->userMapper.readUser(email, dataAccess));
	}

	@Override
	public void updateUser(User user) {
		DataAccess dataAccess = new DataAccessImpl();

		new LogicTrans<User>(dataAccess).transaction(d -> userMapper.updateUser(d, dataAccess), user);

	}

	@Override
	public void createRole(Role role) {
		DataAccess dataAccess = new DataAccessImpl();

		new LogicTrans<Role>(dataAccess).transaction(d -> roleMapper.createRole(d, dataAccess), role);

	}

	@Override
	public void deleteRole(Role role) {
		DataAccess dataAccess = new DataAccessImpl();

		new LogicTrans<Role>(dataAccess).transaction(d -> roleMapper.deleteRole(d, dataAccess), role);

	}

	@Override
	public Optional<Role> readRole(int id) {
		DataAccess dataAccess = new DataAccessImpl();

		return new LogicTrans<Optional<Role>>(dataAccess).transaction(()->roleMapper.readRole(id, dataAccess));

	}

	@Override
	public void updateRole(Role role) {
		DataAccess dataAccess = new DataAccessImpl();

		new LogicTrans<Role>(dataAccess).transaction(d -> roleMapper.updateRole(d, dataAccess), role);

	}

	@Override
	public void createPermission(Permission permission) {
		DataAccess dataAccess = new DataAccessImpl();

		new LogicTrans<Permission>(dataAccess).transaction(d -> permissionCrud.create(dataAccess, d), permission);

	}

	@Override
	public void deletePermission(Permission permission) {
		DataAccess dataAccess = new DataAccessImpl();

		new LogicTrans<Permission>(dataAccess).transaction(d -> permissionCrud.delete(dataAccess, d), permission);
	}

	@Override
	public Optional<Permission> readPermission(int id) {
		DataAccess dataAccess = new DataAccessImpl();

		return new LogicTrans<Optional<Permission>>(dataAccess).transaction(()->permissionCrud.read(dataAccess, id));

	}

	@Override
	public void updatePermission(Permission permission) {
		DataAccess dataAccess = new DataAccessImpl();

		new LogicTrans<Permission>(dataAccess).transaction(d -> permissionCrud.update(dataAccess, d), permission);
	}
}
