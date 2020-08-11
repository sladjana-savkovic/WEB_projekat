package beans;

public class Location {
	private double latitude;
	private double longitude;
	private Address address;
	
	public Location() {}
	
	public Location(double latitude, double longitude, Address address) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.address = new Address(address);
	}
	
	public Location(Location location) {
		this.latitude = location.latitude;
		this.longitude = location.longitude;
		this.address = new Address(location.address);
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	
}
