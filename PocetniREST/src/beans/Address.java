package beans;

public class Address {
	private String country;
	private String city;
	private int zipCode;
	private String streetAndNumber;
	
	public Address() {}
	
	public Address(String country,String city, int zipCode, String streetAndNumber) {
		this.country = country;
		this.city = city;
		this.zipCode = zipCode;
		this.streetAndNumber = streetAndNumber;
	}
	
	public Address(Address address) {
		this.country = address.country;
		this.city = address.city;
		this.zipCode = address.zipCode;
		this.streetAndNumber = address.streetAndNumber;
	}
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	public String getStreetAndNumber() {
		return streetAndNumber;
	}

	public void setStreetAndNumber(String streetAndNumber) {
		this.streetAndNumber = streetAndNumber;
	}
}
