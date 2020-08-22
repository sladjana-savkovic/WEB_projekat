package dao;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Apartment;
import beans.Reservation;
import beans.ReservationStatus;

public class ReservationDAO {
	private String path;
	private File file;
	
	
	/*public static void main(String[] args) {
	
		Reservation r2 = new Reservation(1, 1, "2019-12-01", 2, 3000,
				"", "milica967", ReservationStatus.ACCEPTED);
		
		ReservationDAO reservationDAO = new ReservationDAO();
			ArrayList<Reservation> res = reservationDAO.readFromFile();
			ArrayList<Reservation> res11 = reservationDAO.filterReservationsByStatus(rsrarus);
	
			System.out.println(res11.get(0).getId());
			System.out.println(res11.get(1).getId());
		
	}*/
	
	
	public ReservationDAO() {
		path = "C:\\Users\\Jelena\\Desktop\\rest\\data" + File.separator + "reservations.json";
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
		ArrayList<Reservation> reservationsFromFile = readFromFile();
		if(reservationsFromFile.size() == 0) {
			return 0;
		}
		
		return reservationsFromFile.get(reservationsFromFile.size()-1).getId();
	}
	
	public Reservation getReservationById(int id) {		
		ArrayList<Reservation> reservationsFromFile = readFromFile();
		
		for(int i=0; i<reservationsFromFile.size(); i++) {
			if(reservationsFromFile.get(i).getId() == id) {
				return reservationsFromFile.get(i);				
			}
		}
		return null;
	}
	
	public ArrayList<Reservation> getReservationsByGuest(String guestUsername){		
		ArrayList<Reservation> reservationsFromFile = readFromFile();
		ArrayList<Reservation> reservationsByGuest = new ArrayList<>();
		
		for(int i=0; i<reservationsFromFile.size(); i++) {
			if(reservationsFromFile.get(i).getGuestUsername().equals(guestUsername)) {
				reservationsByGuest.add(reservationsFromFile.get(i));				
			}
		}		
		return reservationsByGuest;	
	}
	
	public ArrayList<Reservation> getAllReservations(){		
		ArrayList<Reservation> reservationsFromFile = readFromFile();
		return reservationsFromFile;
	}
	
	
	public ArrayList<Reservation> getReservationByHostsApartments(String hostName){
		ApartmentDAO apartmentDAO = new ApartmentDAO();
		ArrayList<Apartment> apartmentsByHost = apartmentDAO.getApartmentsByHost(hostName);
		
		ArrayList<Reservation> reservationsFromFile = readFromFile();
		ArrayList<Reservation> reservationsByHost = new ArrayList<>();
		for(Apartment a : apartmentsByHost) {
			for(Reservation r : reservationsFromFile) {
				if(a.getId() == r.getApartmentId()) {
					reservationsByHost.add(r);
				}
			}
		}
		return reservationsByHost;
		
	}
	
	public void addNewReservation(Reservation reservation) {	
		ArrayList<Reservation> reservationsFromFile = readFromFile();
		
		reservationsFromFile.add(reservation);
	
		writeInFile(reservationsFromFile);	
	}
	
	public void cancelReservationByGuest(Reservation reservation) {		
		ArrayList<Reservation> reservationsFromFile = readFromFile();
		
		for(Reservation r : reservationsFromFile) {
			if(r.getId() == reservation.getId()) {
				if(reservation.getStatus().equals(ReservationStatus.CREATED) || reservation.getStatus().equals(ReservationStatus.ACCEPTED)) {
					r.setStatus(ReservationStatus.CANCELED);
					break;
				}
			}
		}
		
		writeInFile(reservationsFromFile);		
	}
	
	public void acceptReservationByHost(Reservation reservation) {		
		ArrayList<Reservation> reservationsFromFile = readFromFile();
		
		for(Reservation r : reservationsFromFile) {
			if(r.getId() == reservation.getId()) {
				if(reservation.getStatus().equals(ReservationStatus.CREATED)) {
					r.setStatus(ReservationStatus.ACCEPTED);
					break;
				}
			}
		}
		writeInFile(reservationsFromFile);	
	}
	
	public void refuseReservationByHost(Reservation reservation) {		
		ArrayList<Reservation> reservationsFromFile = readFromFile();
		
		for(Reservation r : reservationsFromFile) {
			if(r.getId() == reservation.getId()) {
				if(reservation.getStatus().equals(ReservationStatus.CREATED) || reservation.getStatus().equals(ReservationStatus.ACCEPTED)) {
					r.setStatus(ReservationStatus.REFUSED);
					break;
				}
			}
		}
		writeInFile(reservationsFromFile);	
	}

	public void finishReservationByHost(Reservation reservation) {		
		ArrayList<Reservation> reservationsFromFile = readFromFile();
			
		for(Reservation r : reservationsFromFile) {
			if(r.getId() == reservation.getId()) {
				if(reservation.getStatus().equals(ReservationStatus.ACCEPTED) && LocalDate.parse(reservation.getStartDate()).isBefore(LocalDate.now())) {
					r.setStatus(ReservationStatus.FINISHED);
					break;
				}
			}
		}
		writeInFile(reservationsFromFile);
	}
	
	public ArrayList<Reservation> filterReservationsByStatus(ArrayList<ReservationStatus> status){
		ArrayList<Reservation> reservationsFromFile = readFromFile();
		ArrayList<Reservation> filteredReservations = new ArrayList<>();
		
		for(Reservation r : reservationsFromFile) {		
			if(status.contains(r.getStatus())) {
				filteredReservations.add(r);
			}	
		}
		
		return filteredReservations;
	}
	
	
	public ArrayList<Reservation> searchReservationsByGuestUsername(String hostName, String searchText){
		ArrayList<Reservation> hostsReservations = getReservationByHostsApartments(hostName);
		ArrayList<Reservation> searchedReservations = new ArrayList<>();
		
		if(searchText.equals("")) {
			return hostsReservations;
		}
		
		for(Reservation r : hostsReservations) {
			if(r.getGuestUsername().toLowerCase().contains(searchText.toLowerCase())) {				
				searchedReservations.add(r);
			}
		}
		
		return searchedReservations;	
	}
	
	public ArrayList<Reservation> sortReservationsAscending(){
		ArrayList<Reservation> sortedReservations = readFromFile();
		Collections.sort(sortedReservations);
		return sortedReservations;
	}
	public ArrayList<Reservation> sortReservationsDescending(){
		ArrayList<Reservation> sortedReservations = readFromFile();
		Collections.reverse(sortedReservations);
		return sortedReservations;
	}
}
