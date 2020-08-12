package beans;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Apartment {
	private int id;
	private TypeOfApartment type;
	private int numberOfRooms;
	private int numberOfGuests;
	private Location location;
	private ArrayList<LocalDate> rentingDates;
	private ArrayList<LocalDate> availableDates;
	private String hostUsername;
	private ArrayList<Comment> comments;
	private ArrayList<String> pictures;
	private double pricePerNight;
	private LocalTime checkInTime;
	private LocalTime checkOutTime;
	private boolean isActive;
	private ArrayList<Integer> amenities;
	private ArrayList<Integer> reservations;
	
	public Apartment() {}
	
	public Apartment(int id, TypeOfApartment type, int numberOfRooms, int numberOfGuests, Location location, String hostUsername,
			double pricePerNight, LocalTime checkInTime,
			LocalTime checkOutTime, boolean isActive) {
		this.id = id;
		this.type = type;
		this.numberOfRooms = numberOfRooms;
		this.numberOfGuests = numberOfGuests;
		this.location = location;
		this.rentingDates = new ArrayList<LocalDate>();
		this.availableDates = new ArrayList<LocalDate>();
		this.hostUsername = hostUsername;
		this.comments = new ArrayList<Comment>();
		this.pictures = new ArrayList<String>();
		this.pricePerNight = pricePerNight;
		this.checkInTime = checkInTime;
		this.checkOutTime = checkOutTime;
		this.isActive = isActive;
		this.amenities = new ArrayList<Integer>();
		this.reservations = new ArrayList<Integer>();
	}



	public Apartment(Apartment apartment) {
		this.id = apartment.id;
		this.type = apartment.type;
		this.numberOfRooms = apartment.numberOfRooms;
		this.numberOfGuests = apartment.numberOfGuests;
		this.location = apartment.location;
		this.rentingDates = new ArrayList<LocalDate>();
		this.availableDates = new ArrayList<LocalDate>();
		this.hostUsername = apartment.hostUsername;
		this.comments = new ArrayList<Comment>();
		this.pictures = new ArrayList<String>();
		this.pricePerNight = apartment.pricePerNight;
		this.checkInTime = apartment.checkInTime;
		this.checkOutTime = apartment.checkOutTime;
		this.isActive = apartment.isActive;
		this.amenities = new ArrayList<Integer>();
		this.reservations = new ArrayList<Integer>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public ArrayList<LocalDate> getRentingDates() {
		return rentingDates;
	}

	public void setRentingDates(ArrayList<LocalDate> rentingDates) {
		this.rentingDates = rentingDates;
	}

	public ArrayList<LocalDate> getAvailableDates() {
		return availableDates;
	}

	public void setAvailableDates(ArrayList<LocalDate> availableDates) {
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

	public LocalTime getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(LocalTime checkInTime) {
		this.checkInTime = checkInTime;
	}

	public LocalTime getCheckOutTime() {
		return checkOutTime;
	}

	public void setCheckOutTime(LocalTime checkOutTime) {
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

	public ArrayList<Comment> getComments() {
		return comments;
	}

	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}

	public ArrayList<String> getPictures() {
		return pictures;
	}

	public void setPictures(ArrayList<String> pictures) {
		this.pictures = pictures;
	}
	
	
}
