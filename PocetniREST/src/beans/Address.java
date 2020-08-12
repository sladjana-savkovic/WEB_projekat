package beans;

public class Address {
	private String country;
	private String city;
	private int zipCode;
	private String street;
	private int number;
	
	public Address() {}
	
	public Address(String country,String city, int zipCode, String street, int number) {
		this.country = country;
		this.city = city;
		this.zipCode = zipCode;
		this.street = street;
		this.number = number;
	}
	
	public Address(Address address) {
		this.country = address.country;
		this.city = address.city;
		this.zipCode = address.zipCode;
		this.street = address.street;
		this.number = address.number;
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

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	
	
}
