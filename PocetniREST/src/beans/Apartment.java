package beans;

import java.util.ArrayList;

public class Apartment {
	private int id;
	private String name;
	private TypeOfApartment type;
	private int numberOfRooms;
	private int numberOfGuests;
	private Location location;
	private ArrayList<String> rentingDates;
	private ArrayList<String> availableDates;
	private String hostUsername;
	private ArrayList<Integer> comments;
	private ArrayList<String> pictures;
	private double pricePerNight;
	private String checkInTime;
	private String checkOutTime;
	private boolean isActive;
	private ArrayList<Integer> amenities;
	private ArrayList<Integer> reservations;
	private boolean isDeleted;
	
	public Apartment() {}
	
	public Apartment(int id, String name, TypeOfApartment type, int numberOfRooms, int numberOfGuests,
			Location location, String hostUsername, double pricePerNight,
			String checkInTime, String checkOutTime, boolean isActive, boolean isDeleted) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.numberOfRooms = numberOfRooms;
		this.numberOfGuests = numberOfGuests;
		this.location = location;
		this.rentingDates = new ArrayList<String>();
		this.availableDates = new ArrayList<String>();
		this.hostUsername = hostUsername;
		this.comments = new ArrayList<Integer>();
		this.pictures = new ArrayList<String>();
		this.pricePerNight = pricePerNight;
		this.checkInTime = checkInTime;
		this.checkOutTime = checkOutTime;
		this.isActive = isActive;
		this.amenities = new ArrayList<Integer>();
		this.reservations = new ArrayList<Integer>();
		this.isDeleted = isDeleted;
	}


	public Apartment(Apartment apartment) {
		this.id = apartment.id;
		this.name = apartment.name;
		this.type = apartment.type;
		this.numberOfRooms = apartment.numberOfRooms;
		this.numberOfGuests = apartment.numberOfGuests;
		this.location = apartment.location;
		this.rentingDates = new ArrayList<String>();
		this.availableDates = new ArrayList<String>();
		this.hostUsername = apartment.hostUsername;
		this.comments = new ArrayList<Integer>();
		this.pictures = new ArrayList<String>();
		this.pricePerNight = apartment.pricePerNight;
		this.checkInTime = apartment.checkInTime;
		this.checkOutTime = apartment.checkOutTime;
		this.isActive = apartment.isActive;
		this.amenities = new ArrayList<Integer>();
		this.reservations = new ArrayList<Integer>();
		this.isDeleted = apartment.isDeleted;
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

	public TypeOfApartment getType() {
		return type;
	}

	public void setType(TypeOfApartment type) {
		this.type = type;
	}

	public int getNumberOfRooms() {
		return numberOfRooms;
	}

	public void setNumberOfRooms(int numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}

	public int getNumberOfGuests() {
		return numberOfGuests;
	}

	public void setNumberOfGuests(int numberOfGuests) {
		this.numberOfGuests = numberOfGuests;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public ArrayList<String> getRentingDates() {
		return rentingDates;
	}

	public void setRentingDates(ArrayList<String> rentingDates) {
		this.rentingDates = rentingDates;
	}

	public ArrayList<String> getAvailableDates() {
		return availableDates;
	}

	public void setAvailableDates(ArrayList<String> availableDates) {
		this.availableDates = availableDates;
	}

	public String getHostUsername() {
		return hostUsername;
	}

	public void setHostUsername(String hostUsername) {
		this.hostUsername = hostUsername;
	}

	public double getPricePerNight() {
		return pricePerNight;
	}

	public void setPricePerNight(double pricePerNight) {
		this.pricePerNight = pricePerNight;
	}

	public String getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(String checkInTime) {
		this.checkInTime = checkInTime;
	}

	public String getCheckOutTime() {
		return checkOutTime;
	}

	public void setCheckOutTime(String checkOutTime) {
		this.checkOutTime = checkOutTime;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public ArrayList<Integer> getAmenities() {
		return amenities;
	}

	public void setAmenities(ArrayList<Integer> amenities) {
		this.amenities = amenities;
	}

	public ArrayList<Integer> getReservations() {
		return reservations;
	}

	public void setReservations(ArrayList<Integer> reservations) {
		this.reservations = reservations;
	}

	public ArrayList<Integer> getComments() {
		return comments;
	}

	public void setComments(ArrayList<Integer> comments) {
		this.comments = comments;
	}

	public ArrayList<String> getPictures() {
		return pictures;
	}

	public void setPictures(ArrayList<String> pictures) {
		this.pictures = pictures;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
}
