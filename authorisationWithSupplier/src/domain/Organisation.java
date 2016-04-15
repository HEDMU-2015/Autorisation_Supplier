package domain;

public interface Organisation {

	int getId();

	void setId(int id);

	int getParentID();

	void setParentID(int parentID);

	String getName();

	void setName(String name);
	
	String getParentName();
	
	void setParentName(String parentName);
	
	int getLevel();

	void setLevel(int level);

}