package domain;

public class UserPermissionImpl implements UserPermission {

	private int userPermissionID;
	private String userID;
	private Permission permission;
	private Organisation organisation;

	@Override
	public String getUserID() {
		return userID;
	}

	public int getUserPermissionID() {
		return userPermissionID;
	}

	public void setUserPermissionID(int userPermissionID) {
		this.userPermissionID = userPermissionID;
	}

	@Override
	public void setUserID(String userID) {
		this.userID = userID;
	}

	@Override
	public Permission getPermission() {
		return permission;
	}

	@Override
	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	@Override
	public Organisation getOrganizationUnit() {
		return organisation;
	}

	@Override
	public void setOrganizationUnit(Organisation organization) {
		this.organisation = organization;
	}

	@Override
	public String toString() {
		return "UserPermissionImpl [userID=" + userID + ", permission=" + permission + ", organisation=" + organisation
				+ "]";
	}

}
