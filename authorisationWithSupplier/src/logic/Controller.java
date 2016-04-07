package logic;

import java.util.Optional;

import domain.Organisation;
import domain.Permission;
import domain.Role;
import domain.User;

public interface Controller {

	public void createOrganisation(Organisation organisation);

	public void deleteOrganisation(Organisation organisation);

	public Optional<Organisation> readOrganisation(int id);

	public void updateOrganisation(Organisation organisation);

	public void createUser(User user);

	public void deleteUser(User user);

	public Optional<User> readUser(String email);

	public void updateUser(User user);

	public void createRole(Role role);

	public void deleteRole(Role role);

	public Optional<Role> readRole(int id);

	public void updateRole(Role role);

	public void createPermission(Permission permission);

	public void deletePermission(Permission permission);

	public Optional<Permission> readPermission(int id);

	public void updatePermission(Permission permission);


}