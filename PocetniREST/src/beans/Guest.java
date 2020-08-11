package beans;

import java.util.ArrayList;

public class Guest extends User{
	
	private ArrayList<Integer> rentedApartments;
	private ArrayList<Integer> reservations;

	public Guest() {
		super();
		rentedApartments = new ArrayList<Integer>();
		reservations = new ArrayList<Integer>();
	}
	
	public Guest(String username, String password, String name, String surname, Gender gender) {
		super(username, password, name, surname, gender, TypeOfUser.GUEST);
		rentedApartments = new ArrayList<Integer>();
		reservations = new ArrayList<Integer>();
	}
	
	public Guest(Guest guest) {
		super((User)guest);
		rentedApartments = new ArrayList<Integer>();
		reservations = new ArrayList<Integer>();
	}

	public ArrayList<Integer> getRentedApartments() {
		return rentedApartments;
	}

	public void setRentedApartments(ArrayList<Integer> rentedApartments) {
		this.rentedApartments = rentedApartments;
	}

	public ArrayList<Integer> getReservations() {
		return reservations;
	}

	public void setReservations(ArrayList<Integer> reservations) {
		this.reservations = reservations;
	}
	
}
