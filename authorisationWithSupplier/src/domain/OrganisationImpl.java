package domain;

public class OrganisationImpl implements Organisation {

	private int id;
	private int parentID;
	private String name;

	@Override
	public int getId() {
		return id;
	}

	
	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int getParentID() {
		return parentID;
	}

	
	@Override
	public void setParentID(int parentID) {
		this.parentID = parentID;
	}


	@Override
	public String getName() {
		return name;
	}


	@Override
	public void setName(String name) {
		this.name = name;
	}


	@Override
	public String toString() {
		return "OrganisationImpl [id=" + id + ", parentID=" + parentID + ", name=" + name + "]";
	}

	
	
}