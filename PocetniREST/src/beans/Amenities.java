package beans;

public class Amenities {
	private int id;
	private String name;
	private boolean isDeleted;
	
	public Amenities() {}

	public Amenities(int id, String name, boolean isDeleted) {
		this.id = id;
		this.name = name;
		this.isDeleted = isDeleted;
	}
	
	public Amenities(Amenities amenities) {
		this.id = amenities.id;
		this.name = amenities.name;
		this.isDeleted = amenities.isDeleted;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}
