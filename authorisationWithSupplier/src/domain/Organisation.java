package domain;

import java.util.Map;

public interface Organisation {
	
	public void addChild(Integer id, Organisation organisation); 

	public Map<Integer, Organisation> getChildren() ;

	public void setChildren(Map<Integer, Organisation> children) ;
	
	

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