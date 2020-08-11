package beans;

public class Amenities {
	private int id;
	private String name;
	
	public Amenities() {}

	public Amenities(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Amenities(Amenities amenities) {
		this.id = amenities.id;
		this.name = amenities.name;
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
	
}
