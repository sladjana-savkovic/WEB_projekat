package dao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Gender;
import beans.Guest;


public class GuestDAO {
	
	private String path;
	private File file;
	
	public GuestDAO() {
		/*File data_dir = new File(System.getProperty("catalina.base") + File.separator + "data");
		if(!data_dir.exists()) {
			data_dir.mkdir();
		}
		path = System.getProperty("catalina.base") + File.separator + "data" + File.separator + "guests.json";*/
		
		path = Paths.get("WEB projekat\\data").toAbsolutePath().toString() + File.separator + "guests.json";
		file = new File(path);
	}
	
	
	public Guest getGuest(String guestUsername) {
		ArrayList<Guest> guests = readFromFile();
		for(Guest guest:guests) {
			if(guest.getUsername().equals(guestUsername))
				return guest;
		}
		return null;
	}
	
	public boolean checkPassword(String guestUsername,String password) {
		Guest guest = getGuest(guestUsername);
		if(guest == null || !guest.getPassword().equals(password))
			return false;
		
		return true;
	}
	
	public ArrayList<Guest> getGuestsByHost(String username){
		ArrayList<Guest> guests = readFromFile();
		ArrayList<Guest> guestsOfHost = new ArrayList<>();
		
		ApartmentDAO apartmentDAO = new ApartmentDAO();
		
		for(Guest g : guests) {
			for(int i : g.getRentedApartments()) {
				if(apartmentDAO.getApartment(i).getHostUsername().equals(username)) {
					guestsOfHost.add(g);
					break;
				}
			}
		}
		return guestsOfHost;
	}
	
	
	public ArrayList<Guest> getGuestsByUsernameAndGender(String guestUsername,Gender gender){
		ArrayList<Guest> guests = readFromFile();
		ArrayList<Guest> filtratedGuests = new ArrayList<Guest>();
		for(Guest g:guests) {
			if(guestUsername == null && gender == null) {
				return getAllGuests();
			}
			
			if(guestUsername == null && g.getGender() == gender) {
				filtratedGuests.add(g);
			}
			else if(gender == null && g.getUsername().toLowerCase().contains(guestUsername.toLowerCase())) {
				filtratedGuests.add(g);
			}
			else if(guestUsername != null && gender != null &&
					g.getUsername().toLowerCase().contains(guestUsername.toLowerCase()) && g.getGender() == gender){
				filtratedGuests.add(g);
			}
		}
		return filtratedGuests;
	}
	//TODO ove dvije iste metode spojiti u jednu 
	public ArrayList<Guest> getHostsGuestsByUsernameAndGender(String hostUsername, String guestUsername,Gender gender){
		ArrayList<Guest> guests = getGuestsByHost(hostUsername);
		ArrayList<Guest> filtratedGuests = new ArrayList<Guest>();
		for(Guest g:guests) {
			if(guestUsername == null && gender == null) {
				return getGuestsByHost(hostUsername);
			}
			
			if(guestUsername == null && g.getGender() == gender) {
				filtratedGuests.add(g);
			}
			else if(gender == null && g.getUsername().toLowerCase().contains(guestUsername.toLowerCase())) {
				filtratedGuests.add(g);
			}
			
			else if(guestUsername != null && gender != null &&
					g.getUsername().toLowerCase().contains(guestUsername.toLowerCase()) && g.getGender() == gender) 
				filtratedGuests.add(g);
		}
		return filtratedGuests;
	}
	
	
	public ArrayList<Guest> getAllGuests(){
		ArrayList<Guest> guests = readFromFile();
		return guests;
	}
	
	public void editGuest(Guest guest) {
		ArrayList<Guest> guests = readFromFile();
		for(Guest g:guests) {
			if(g.getUsername().equals(guest.getUsername())){
				g.setName(guest.getName());
				g.setSurname(guest.getSurname());
				g.setGender(guest.getGender());
				g.setPassword(guest.getPassword());
			}
		}
		writeInFile(guests);
	}
	
	public void addGuest(Guest guest) {
		ArrayList<Guest> guests = readFromFile();
		guests.add(guest);
		writeInFile(guests);
	}
	
	public void blockGuest(String guestUsername) {
		ArrayList<Guest> guests = readFromFile();
		for(Guest guest:guests) {
			if(guest.getUsername().equals(guestUsername))
				guest.setBlocked(true);
		}
		writeInFile(guests);
	}
	
	public void addRentedApartmentToGuest(String guestUsername,int apartmentId) {
		ArrayList<Guest> guests = readFromFile();
		for(Guest guest:guests) {
			if(guest.getUsername().equals(guestUsername)){
				ArrayList<Integer> rentedApartments  = guest.getRentedApartments();
				rentedApartments.add(apartmentId);
				guest.setRentedApartments(rentedApartments);
			}
		}
		writeInFile(guests);
	}
	
	public void addReservationToGuest(String guestUsername,int reservationId) {
		ArrayList<Guest> guests = readFromFile();
		for(Guest guest:guests) {
			if(guest.getUsername().equals(guestUsername)){
				ArrayList<Integer> reservations  = guest.getReservations();
				reservations.add(reservationId);
				guest.setReservations(reservations);
			}
		}
		writeInFile(guests);
	}
	
	private ArrayList<Guest> readFromFile() {
		ArrayList<Guest> guests = new ArrayList<Guest>();
		ObjectMapper mapper = new ObjectMapper();	
		try {
			guests = mapper.readValue(file, new TypeReference<ArrayList<Guest>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
		return guests;
	}
	private void writeInFile(ArrayList<Guest> guests) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(file, guests);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	/*public static void main(String[] args) {
		
		ArrayList<Guest> guests = new ArrayList<Guest>();
        
		guests.add(new Guest("pero123","sifra123","Pera","Perić",Gender.MALE));
		guests.add(new Guest("mika789","sifra789","Mika","Mikić",Gender.MALE));
        
        GuestDAO guestDao = new GuestDAO();
        
        guestDao.writeInFile(guests);
        
        ArrayList<Guest> guestsFromFile =  guestDao.getAllGuests();
        
        guestDao.addGuest(new Guest("mara","maramara","Mara","Marić",Gender.FEMALE));
        
        guestsFromFile =  guestDao.getAllGuests();
        
        for(Guest guest : guestsFromFile) {
        	System.out.println(guest.getName() + " " + guest.getSurname() );
        }
        
        guestDao.blockGuest("mara");
        
        guestDao.addRentedApartmentToGuest("mara", 1);
        guestDao.addReservationToGuest("mara", 2);
        guestDao.addRentedApartmentToGuest("mara", 3);
        
        guestDao.editGuest(new Guest("mara","maramica","Marica","Marić",Gender.FEMALE));
        
        System.out.println(guestDao.getGuest("pero123").getName() + " ima gost");
        
	}*/

}
