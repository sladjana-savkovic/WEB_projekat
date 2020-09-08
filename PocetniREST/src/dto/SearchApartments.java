package dto;

public class SearchApartments {
	private String city;
	private String startDate; 
	private String endDate; 
	private int minPrice;
	private int maxPrice;
	private int minRooms;
	private int maxRooms;
	private int persons;
	
	public SearchApartments() {}

	public SearchApartments(String city, String startDate, String endDate, int minPrice, int maxPrice,
			int minRooms, int maxRooms, int persons) {
		this.city = city;
		this.startDate = startDate;
		this.endDate = endDate;
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.minRooms = minRooms;
		this.maxRooms = maxRooms;
		this.persons = persons;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
	}

	public int getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(int maxPrice) {
		this.maxPrice = maxPrice;
	}

	public int getMinRooms() {
		return minRooms;
	}

	public void setMinRooms(int minRooms) {
		this.minRooms = minRooms;
	}

	public int getMaxRooms() {
		return maxRooms;
	}

	public void setMaxRooms(int maxRooms) {
		this.maxRooms = maxRooms;
	}

	public int getPersons() {
		return persons;
	}

	public void setPersons(int persons) {
		this.persons = persons;
	}
	
}
