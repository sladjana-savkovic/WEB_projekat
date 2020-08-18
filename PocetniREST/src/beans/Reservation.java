package beans;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import dao.LocalDateDeserializer;
import dao.LocalDateSerializer;

public class Reservation {
	private int id;
	private int apartmentId;	
	private String startDate;
	private int numberOfNights;
	private double totalPrice;
	private String message;
	private String guestUsername;
	private ReservationStatus status;
	
	public Reservation() {}

	public Reservation(int id, int apartmentId, String startDate, int numberOfNights, double totalPrice,
			String message, String guestUsername, ReservationStatus status) {
		this.id = id;
		this.apartmentId = apartmentId;
		this.startDate = startDate;
		this.numberOfNights = numberOfNights;
		this.totalPrice = totalPrice;
		this.message = message;
		this.guestUsername = guestUsername;
		this.status = status;
	}
	
	public Reservation(Reservation reservation) {
		this.id = reservation.id;
		this.apartmentId = reservation.apartmentId;
		this.startDate = reservation.startDate;
		this.numberOfNights = reservation.numberOfNights;
		this.totalPrice = reservation.totalPrice;
		this.message = reservation.message;
		this.guestUsername = reservation.guestUsername;
		this.status = reservation.status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getApartmentId() {
		return apartmentId;
	}

	public void setApartmentId(int apartmentId) {
		this.apartmentId = apartmentId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public int getNumberOfNights() {
		return numberOfNights;
	}

	public void setNumberOfNights(int numberOfNights) {
		this.numberOfNights = numberOfNights;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getGuestUsername() {
		return guestUsername;
	}

	public void setGuestUsername(String guestUsername) {
		this.guestUsername = guestUsername;
	}

	public ReservationStatus getStatus() {
		return status;
	}

	public void setStatus(ReservationStatus status) {
		this.status = status;
	}
	
}
