package dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Gender;
import beans.Reservation;
import beans.TypeOfUser;
import beans.User;

public class UserDAO {

	private File file;
	private String path;
	
	public UserDAO() {
		File dataDir = new File(System.getProperty("catalina.base") + File.separator + "data");
		if(!dataDir.exists()) {
			dataDir.mkdir();
		}
		this.path = System.getProperty("catalina.base") + File.separator + "data" + File.separator + "users.json";
		 file = new File(path);
		 try {
		   if (file.createNewFile()){
		    ArrayList<User> users = new ArrayList<User>();
		    writeInFile(users);
		   }
		  } catch (IOException e) {
		   e.printStackTrace();
		  }
	}
	
	public User getUser(String username) {
		ArrayList<User> users = readFromFile();
		for(User user:users) {
			if(user.getUsername().equals(username))
				return user;
		}
		return null;
	}
	
	public ArrayList<User> getAllGuests(){
		ArrayList<User> users = readFromFile();
		ArrayList<User> filtrated = new ArrayList<User>();
		for(User u:users) {
			if(u.getTypeOfUser() == TypeOfUser.GUEST)
				filtrated.add(u);
		}
		return filtrated;
	}
	
	public ArrayList<User> getAllHosts(){
		ArrayList<User> users = readFromFile();
		ArrayList<User> filtrated = new ArrayList<User>();
		for(User u:users) {
			if(u.getTypeOfUser() == TypeOfUser.HOST)
				filtrated.add(u);
		}
		return filtrated;
	}
	
	public ArrayList<User> getAllGuestsAndHosts(){
		ArrayList<User> users = readFromFile();
		ArrayList<User> filtrated = new ArrayList<User>();
		for(User u:users) {
			if(u.getTypeOfUser() == TypeOfUser.GUEST || u.getTypeOfUser() == TypeOfUser.HOST)
				filtrated.add(u);
		}
		return filtrated;
	}
	
	public ArrayList<User> getGuestsByHost(String hostUsername){
		ArrayList<User> guestsOfHost = new ArrayList<User>();
		
		ReservationDAO reservationDAO = new ReservationDAO();
		ApartmentDAO apartmentDAO = new ApartmentDAO();
		
		for(Reservation r: reservationDAO.getAllReservations()) {
			if(apartmentDAO.getApartment(r.getApartmentId()).getHostUsername().equals(hostUsername)) {
				guestsOfHost.add(getUser(r.getGuestUsername()));
			}
		}
		
		return guestsOfHost;
	}
	
	public ArrayList<User> getGuestsByUsernameAndGender(String guestUsername,Gender gender,ArrayList<User> users){
		ArrayList<User> filtrated = new ArrayList<User>();
		
		if(guestUsername == null && gender == null) {
			return getAllGuests();
		}
		for(User u:users) {
			if(u.getTypeOfUser() == TypeOfUser.GUEST) {
				if(guestUsername == null && u.getGender() == gender) {
					filtrated.add(u);
				}
				else if(gender == null && u.getUsername().toLowerCase().contains(guestUsername.toLowerCase())) {
					filtrated.add(u);
				}
				else if(guestUsername != null && gender != null &&
						u.getUsername().toLowerCase().contains(guestUsername.toLowerCase()) && u.getGender() == gender){
					filtrated.add(u);
				}
			}
		}
		return filtrated;
	}
	
	public ArrayList<User> getHostsByUsernameAndGender(String hostUsername, Gender gender,ArrayList<User> users){
		ArrayList<User> filtrated = new ArrayList<User>();
		
		if(hostUsername == null && gender == null) {
			return getAllHosts();
		}
		
		for(User u:users) {
			if(u.getTypeOfUser() == TypeOfUser.HOST) {
				if(hostUsername == null && u.getGender() == gender) {
					filtrated.add(u);
				}
				else if(gender == null && u.getUsername().toLowerCase().contains(hostUsername.toLowerCase())) {
					filtrated.add(u);
				}
				else if(hostUsername != null && gender != null &&
						u.getUsername().toLowerCase().contains(hostUsername.toLowerCase()) && u.getGender() == gender){
					filtrated.add(u);
				}
			}
			
		}
		return filtrated;
	}
	
	public boolean checkPassword(String username,String password) {
		User user = getUser(username);
		if(user == null || !user.getPassword().equals(password))
			return false;
		
		return true;
	}
	
	public ArrayList<User> getAllUsers(){
		ArrayList<User> users = readFromFile();
		return users;
	}
	
	public void addUser(User user) {
		ArrayList<User> users = readFromFile();
		users.add(user);
		writeInFile(users);
	}
	
	public void editUser(User user) {
		ArrayList<User> users = readFromFile();
		for(User u:users) {
			if(u.getUsername().equals(user.getUsername())){
				u.setName(user.getName());
				u.setSurname(user.getSurname());
				u.setGender(user.getGender());
				u.setPassword(user.getPassword());
			}
		}
		writeInFile(users);
	}
	
	public void blockUser(String username) {
		ArrayList<User> users = readFromFile();
		for(User user:users) {
			if(user.getUsername().equals(username))
				user.setBlocked(true);
		}
		writeInFile(users);
	}
	
	private ArrayList<User> readFromFile() {
		ArrayList<User> users = new ArrayList<User>();
		ObjectMapper mapper = new ObjectMapper();	
		try {
			users = mapper.readValue(file, new TypeReference<ArrayList<User>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
		return users;
	}
	private void writeInFile(ArrayList<User> users) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(file, users);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
