package domain;

public class RoleImpl implements Role {

	private int id;
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
	public String getName() {
		return name;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "RoleImpl [id=" + id + ", name=" + name + "]";
	}
	
	
}
