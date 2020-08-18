package dao;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Apartment;
import beans.Reservation;
import beans.ReservationStatus;

public class ReservationDAO {
	private String path;
	private File file;
	
	/*
	public static void main(String[] args) {
	
		
		
		Reservation r2 = new Reservation(1, 1, "2019-12-01", 2, 3000,
				"", "milica967", ReservationStatus.ACCEPTED);
		
		ReservationDAO reservationDAO = new ReservationDAO();
			reservationDAO.finishReservationByHost(r2);
		
	}
	*/
	
	public ReservationDAO() {
		path = "data/reservations.json";
		file = new File(path);
	}
	
	private ArrayList<Reservation> readFromFile() {
		ArrayList<Reservation> reservations = new ArrayList<Reservation>();
		ObjectMapper mapper = new ObjectMapper();	
		try {
			reservations = mapper.readValue(file, new TypeReference<ArrayList<Reservation>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
		return reservations;
	}
	
	private void writeInFile(ArrayList<Reservation> reservations) {
		ObjectMapper mapper = new ObjectMapper();
		try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, reservations);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public int getLastId() {
		ReservationDAO reservationDAO = new ReservationDAO();		
		ArrayList<Reservation> reservationsFromFile = reservationDAO.readFromFile();
		if(reservationsFromFile.size() == 0) {
			return 0;
		}
		
		return reservationsFromFile.get(reservationsFromFile.size()-1).getId();
	}
	
	public Reservation getReservationById(int id) {
		ReservationDAO reservationDAO = new ReservationDAO();		
		ArrayList<Reservation> reservationsFromFile = reservationDAO.readFromFile();
		
		for(int i=0; i<reservationsFromFile.size(); i++) {
			if(reservationsFromFile.get(i).getId() == id) {
				return reservationsFromFile.get(i);				
			}
		}
		return null;
	}
	
	public ArrayList<Reservation> getReservationsByGuest(String guestUsername){
		ReservationDAO reservationDAO = new ReservationDAO();		
		ArrayList<Reservation> reservationsFromFile = reservationDAO.readFromFile();
		ArrayList<Reservation> reservationsByGuest = new ArrayList<>();
		
		for(int i=0; i<reservationsFromFile.size(); i++) {
			if(reservationsFromFile.get(i).getGuestUsername().equals(guestUsername)) {
				reservationsByGuest.add(reservationsFromFile.get(i));				
			}
		}		
		return reservationsByGuest;	
	}
	
	public ArrayList<Reservation> getAllReservations(){
		ReservationDAO reservationDAO = new ReservationDAO();		
		ArrayList<Reservation> reservationsFromFile = reservationDAO.readFromFile();
		return reservationsFromFile;
	}
	
	
	public ArrayList<Reservation> getReservationByHostsApartments(String hostName){
		ReservationDAO reservationDAO = new ReservationDAO();
		//ovde treba da pozovem funkciju iz ApartmentDao.java koja vraca sve apartmane jednog domacina
		//funckija mi vrati listu svih apartmana, a prima domacinovo korisnicko ime
		ArrayList<Apartment> apartmentsByHost = new ArrayList<>();
		
		ArrayList<Reservation> reservationsFromFile = reservationDAO.readFromFile();
		ArrayList<Reservation> reservationsByHost = new ArrayList<>();
		for(int i=0; i<apartmentsByHost.size(); i++) {
			for(int j=0; j<reservationsFromFile.size(); j++) {
				if(apartmentsByHost.get(i).getId() == reservationsFromFile.get(j).getApartmentId()) {
					reservationsByHost.add(reservationsFromFile.get(j));
				}
			}
		}
		return reservationsByHost;
		
	}
	
	public void addNewReservation(Reservation reservation) {
		ReservationDAO reservationDAO = new ReservationDAO();		
		ArrayList<Reservation> reservationsFromFile = reservationDAO.readFromFile();
		
		reservationsFromFile.add(reservation);
	
		reservationDAO.writeInFile(reservationsFromFile);	
	}
	
	public void cancelReservationByGuest(Reservation reservation) {
		ReservationDAO reservationDAO = new ReservationDAO();		
		ArrayList<Reservation> reservationsFromFile = reservationDAO.readFromFile();
		
		for(Reservation r : reservationsFromFile) {
			if(r.getId() == reservation.getId()) {
				if(reservation.getStatus().equals(ReservationStatus.CREATED) || reservation.getStatus().equals(ReservationStatus.ACCEPTED)) {
					r.setStatus(ReservationStatus.CANCELED);
					break;
				}
			}
		}
		
		reservationDAO.writeInFile(reservationsFromFile);		
	}
	
	public void acceptReservationByHost(Reservation reservation) {
		ReservationDAO reservationDAO = new ReservationDAO();		
		ArrayList<Reservation> reservationsFromFile = reservationDAO.readFromFile();
		
		for(Reservation r : reservationsFromFile) {
			if(r.getId() == reservation.getId()) {
				if(reservation.getStatus().equals(ReservationStatus.CREATED)) {
					r.setStatus(ReservationStatus.ACCEPTED);
					break;
				}
			}
		}
		reservationDAO.writeInFile(reservationsFromFile);	
	}
	
	public void refuseReservationByHost(Reservation reservation) {
		ReservationDAO reservationDAO = new ReservationDAO();		
		ArrayList<Reservation> reservationsFromFile = reservationDAO.readFromFile();
		
		for(Reservation r : reservationsFromFile) {
			if(r.getId() == reservation.getId()) {
				if(reservation.getStatus().equals(ReservationStatus.CREATED) || reservation.getStatus().equals(ReservationStatus.ACCEPTED)) {
					r.setStatus(ReservationStatus.REFUSED);
					break;
				}
			}
		}
		reservationDAO.writeInFile(reservationsFromFile);	
	}

	public void finishReservationByHost(Reservation reservation) {
		ReservationDAO reservationDAO = new ReservationDAO();		
		ArrayList<Reservation> reservationsFromFile = reservationDAO.readFromFile();
			
		for(Reservation r : reservationsFromFile) {
			if(r.getId() == reservation.getId()) {
				if(reservation.getStatus().equals(ReservationStatus.ACCEPTED) && LocalDate.parse(reservation.getStartDate()).isBefore(LocalDate.now())) {
					r.setStatus(ReservationStatus.FINISHED);
					break;
				}
			}
		}
		reservationDAO.writeInFile(reservationsFromFile);
	}
	
		
}
