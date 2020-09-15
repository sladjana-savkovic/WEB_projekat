package dao;

import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
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
			/*ArrayList<Reservation> res = reservationDAO.readFromFile();
			ArrayList<Reservation> res11 = reservationDAO.filterReservationsByStatus(rsrarus);
	
			System.out.println(res11.get(0).getId());
			System.out.println(res11.get(1).getId());
		
		double d = reservationDAO.getTotalPrice("2020-09-10", 4, 1);
		int z = reservationDAO.getMaxNumberNight("2020-09-12", 1);
		System.out.println(z);
		
	}*/
		
	public ReservationDAO() {
		File dataDir = new File(System.getProperty("catalina.base") + File.separator + "data");
		if(!dataDir.exists()) {
			dataDir.mkdir();
		}
		this.path = System.getProperty("catalina.base") + File.separator + "data" + File.separator + "reservations.json";
		file = new File(path);
		 try {
		   if (file.createNewFile()){
		    ArrayList<Reservation> reservations = new ArrayList<Reservation>();
		    writeInFile(reservations);
		   }
		  } catch (IOException e) {
		   e.printStackTrace();
		  }
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
	
	public boolean checkIfApartmentHasReservation(int apartmentId) {
		ArrayList<Reservation> reservations = readFromFile();
		for(Reservation r:reservations) {
			if(r.getApartmentId() == apartmentId) {
				return true;
			}
		}
		return false;
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
				if(reservation.getStatus().equals(ReservationStatus.ACCEPTED)) {
					r.setStatus(ReservationStatus.FINISHED);
					break;
				}
			}
		}
		writeInFile(reservationsFromFile);
	}

	public ArrayList<Reservation> filterReservationsByStatus(ArrayList<ReservationStatus> status, ArrayList<Reservation> reservations){
		ArrayList<Reservation> filteredReservations = new ArrayList<Reservation>();
		
		for(Reservation r : reservations) {		
			if(status.contains(r.getStatus())) {
				filteredReservations.add(r);
			}	
		}
		
		return filteredReservations;
	}
	

	public ArrayList<Reservation> searchReservationsByGuestUsername(String searchText, ArrayList<Reservation> reservations){
		ArrayList<Reservation> searchedReservations = new ArrayList<Reservation>();
		
		if(searchText == null) {
			return reservations;
		}
		
		for(Reservation r : reservations) {
			if(r.getGuestUsername().toLowerCase().contains(searchText.toLowerCase())) {				
				searchedReservations.add(r);
			}
		}
			
		return searchedReservations;	
		
	}
	
	public ArrayList<Reservation> sortReservationsAscending(ArrayList<Reservation> sortedReservations){
		Collections.sort(sortedReservations);
		return sortedReservations;
	}
	public ArrayList<Reservation> sortReservationsDescending(ArrayList<Reservation> sortedReservations){
		Collections.sort(sortedReservations, Collections.reverseOrder());
		return sortedReservations;
	}
	
	public double getTotalPrice(String selectDateStr, int numberOfNights, int idApartment) {
		ApartmentDAO apartmentDAO = new ApartmentDAO();
		HolidayDAO holidayDAO = new HolidayDAO();
		Apartment apartment = apartmentDAO.getApartment(idApartment);
		if(apartment == null) {
			return 0;
		}
		double priceForNight = apartment.getPricePerNight();
		
		ArrayList<String> holidays = holidayDAO.getAllHolidays().getHolidays();
	
		
		ArrayList<String> dates = new ArrayList<String>();
		
		
		for(int i = 0; i < numberOfNights; i++) {
				String nextDate = LocalDate.parse(selectDateStr).plusDays(i).toString();
				dates.add(nextDate);
		}
		
		double total = 0;
		for(String d : dates) {
			
				if((LocalDate.parse(d).getDayOfWeek() == DayOfWeek.FRIDAY || LocalDate.parse(d).getDayOfWeek() == DayOfWeek.SATURDAY || LocalDate.parse(d).getDayOfWeek() == DayOfWeek.SUNDAY) && (holidays.contains(d))) {
					double price = priceForNight - priceForNight/10;
					total = total + (price + price/20);
				}
				else if(LocalDate.parse(d).getDayOfWeek() == DayOfWeek.FRIDAY || LocalDate.parse(d).getDayOfWeek() == DayOfWeek.SATURDAY || LocalDate.parse(d).getDayOfWeek() == DayOfWeek.SUNDAY) {
					total = total + (priceForNight - priceForNight/10);
				}
				else if(holidays.contains(d)) {
					total = total + (priceForNight + priceForNight/20);
				}else {
					total = total + priceForNight;
				}
			
			
		}
		
		return total;
	}
	
	public int getMaxNumberNight(String selectedDateStr, int apartmentId) {
		ApartmentDAO apartmentDAO = new ApartmentDAO();
		Apartment apartment = apartmentDAO.getApartment(apartmentId);
		if(apartment==null) {
			return 0;
		}
		ArrayList<String> dates = apartment.getAvailableDates();
		
		int i = 1;
		
		while(true) {
			
			if(dates.contains(LocalDate.parse(selectedDateStr).plusDays(i).toString())) {
				i = i + 1; 
			}else {
				break;
			}
		}

		return i;
		
	}
	
}
