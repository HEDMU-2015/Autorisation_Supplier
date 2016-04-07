package domain;

public interface UserPermission {

	String getUserID();

	void setUserID(String userID);

	Permission getPermission();

	void setPermission(Permission permission);

	Organisation getOrganizationUnit();

	void setOrganizationUnit(Organisation organization);

}