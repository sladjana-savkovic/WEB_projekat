package dao;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import beans.Address;
import beans.Apartment;
import beans.Comment;
import beans.Location;
import beans.Reservation;
import beans.TypeOfApartment;

public class ApartmentDAO {

	private String path;
	private File file;
	
	public ApartmentDAO() {
		path = "data/apartments.json";
		file = new File(path);
	}
	
	public int getLastId() {
		ArrayList<Apartment> apartments = readFromFile();
		if(apartments.size() == 0) {
			return 0;
		}
		return apartments.get(apartments.size() - 1).getId();
	}
	
	public Apartment getApartment(int id) {
		ArrayList<Apartment> apartments = readFromFile();
		for(Apartment apartment:apartments) {
			if(apartment.getId() == id)
				return apartment;
		}
		return null;
	}
	
	public ArrayList<Apartment> getAllApartments(){
		ArrayList<Apartment> apartments = readFromFile();
		return apartments;
	}
	
	public ArrayList<Apartment> getApartmentsByHost(String hostUsername){
		ArrayList<Apartment> allApartments = readFromFile();
		ArrayList<Apartment> filtratedApartmens = new ArrayList<Apartment>();
		
		for(Apartment apartment:allApartments) {
			if(apartment.getHostUsername().equals(hostUsername)) {
				filtratedApartmens.add(apartment);
			}
		}
		return filtratedApartmens;
	}
	
	public void addApartment(Apartment apartment) {
		ArrayList<Apartment> apartments = readFromFile();
		apartments.add(apartment);
		writeInFile(apartments);
	}
	
	public void editApartment(Apartment apartment) {
		ArrayList<Apartment> apartments = readFromFile();
		for(Apartment a:apartments) {
			if(a.getId() == apartment.getId()) {
				a.setActive(apartment.isActive());
				a.setAmenities(apartment.getAmenities());
				a.setCheckInTime(apartment.getCheckInTime());
				a.setCheckOutTime(apartment.getCheckOutTime());
				a.setDeleted(apartment.isDeleted());
				a.setLocation(apartment.getLocation());
				a.setName(apartment.getName());
				a.setNumberOfGuests(apartment.getNumberOfGuests());
				a.setNumberOfRooms(apartment.getNumberOfRooms());
				a.setPictures(apartment.getPictures());
				a.setPricePerNight(apartment.getPricePerNight());
				a.setRentingDates(apartment.getRentingDates());
				a.setType(apartment.getType());
			}
		}
		writeInFile(apartments);
	}
	
	public void deleteApartment(int id) {
		ArrayList<Apartment> apartments = readFromFile();
		for(Apartment a:apartments) {
			if(a.getId() == id) {
				a.setDeleted(true);
			}
		}
		writeInFile(apartments);
	}
	
	public void addCommentToApartment(Comment comment) {
		ArrayList<Apartment> apartments = readFromFile();
		for(Apartment a:apartments) {
			if(a.getId() == comment.getApartmentId()) {
				ArrayList<Integer> comments = a.getComments();
				comments.add(comment.getId());
				a.setComments(comments);
			}
		}
		writeInFile(apartments);
	}
	
	public void addReservationToApartment(Reservation reservation) {
		ArrayList<Apartment> apartments = readFromFile();
		for(Apartment a:apartments) {
			if(a.getId() == reservation.getApartmentId()) {
				ArrayList<Integer> reservations = a.getReservations();
				reservations.add(reservation.getId());
				a.setReservations(reservations);
			}
		}
		writeInFile(apartments);
	}
	
	public void initializeAvailableAndRentingDates(int apartmentId, ArrayList<String> dates) {
		ArrayList<Apartment> apartments = readFromFile();
		for(Apartment a:apartments) {
			if(a.getId() == apartmentId) {
				a.setAvailableDates(dates);
				a.setRentingDates(dates);
			}
		}
		writeInFile(apartments);
	}
	
	private ArrayList<LocalDate> parseStringToDate(ArrayList<String> dates){
		ArrayList<LocalDate> result = new ArrayList<LocalDate>();
		for(String date:dates) {
			result.add(LocalDate.parse(date));
		}
		return result;
	}
	
	private ArrayList<String> parseDateToString(ArrayList<LocalDate> dates){
		ArrayList<String> result = new ArrayList<String>();
		for(LocalDate date:dates) {
			result.add(date.toString());
		}
		return result;
	}

	public void reduceAvailableDates(int apartmentId,LocalDate date, int numberOfNights) {
		ArrayList<Apartment> apartments = readFromFile();
		for(Apartment a:apartments) {
			if(a.getId() == apartmentId) {
				ArrayList<String> availableDates = a.getAvailableDates();
				ArrayList<LocalDate> datesForRemove = new ArrayList<LocalDate>();
				for(int i=0; i<numberOfNights; i++) {
					datesForRemove.add(date.plusDays(i));
				}
				availableDates.removeAll(parseDateToString(datesForRemove));
			}
		}
		writeInFile(apartments);
	}
	
	public void setAvailable(int id,ArrayList<String> available) {
		ArrayList<Apartment> apartments = readFromFile();
		for(Apartment a:apartments) {
			if(a.getId() == id) {
				a.setAvailableDates(available);
			}
		}
		writeInFile(apartments);
	}
	
	public void changeAvailableAndRentingDates(int apartmentId, ArrayList<LocalDate> newRentingDates) {
		ArrayList<Apartment> apartments = readFromFile();
		for(Apartment a:apartments) {
			if(a.getId() == apartmentId) {
				ArrayList<LocalDate> newDates = findDifferencesBetweenDates(newRentingDates,parseStringToDate(a.getRentingDates()));
				ArrayList<LocalDate> deletedDates = findDifferencesBetweenDates(parseStringToDate(a.getRentingDates()), newRentingDates);
				
				ArrayList<String> availableDates = a.getAvailableDates();
				availableDates.addAll(parseDateToString(newDates));
				availableDates.removeAll(parseDateToString(deletedDates));
				a.setRentingDates(parseDateToString(newRentingDates));
			}
		}
		writeInFile(apartments);
	}
	
	private ArrayList<LocalDate> findDifferencesBetweenDates(ArrayList<LocalDate> firstList,ArrayList<LocalDate> secondList){
		ArrayList<LocalDate> differences = new ArrayList<LocalDate>();
		
		for(LocalDate first:firstList) {
			if(!secondList.contains(first)) {
				differences.add(first);
			}
		}
		
		return differences;
	}
	
	private ArrayList<Apartment> readFromFile() {
		ArrayList<Apartment> apartments = new ArrayList<Apartment>();
		ObjectMapper mapper = new ObjectMapper();	
		try {
			apartments = mapper.readValue(file, new TypeReference<ArrayList<Apartment>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
		return apartments;
	}
	private void writeInFile(ArrayList<Apartment> apartments) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(file, apartments);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	/*public static void main(String[] args) {
		
		ArrayList<Apartment> apartments = new ArrayList<Apartment>();
		apartments.add(new Apartment(1,"Sunce",TypeOfApartment.WHOLE_APARTMENT,2,4,
				new Location(100,200,new Address("Srbija","Beograd",22000,"Pašićeva",50)),
				"gaga998",1500,
				LocalTime.now().toString(),LocalTime.now().toString(),true,false));
		
		ApartmentDAO apartmentDAO = new ApartmentDAO();
		
		apartmentDAO.writeInFile(apartments);
		
		ArrayList<Apartment> apartmentFromFile = apartmentDAO.getAllApartments();
		
		for(Apartment a:apartmentFromFile) {
			System.out.println(a.getName() + " " + a.getHostUsername());
		}
		
		apartmentDAO.addApartment(new Apartment(2,"Mjesec",TypeOfApartment.ROOM,2,4,
				new Location(100,200,new Address("Srbija","Beograd",22000,"Pašićeva",50)),
				"gaga998",1500,
				LocalTime.now().toString(),LocalTime.now().toString(),true,false));
		
		apartmentFromFile = apartmentDAO.getApartmentsByHost("sladja997");
		System.out.println(apartmentFromFile.size());
		
		apartmentDAO.addCommentToApartment(new Comment(400,"gaga998",1,"opis",5,true));
		
		ArrayList<String> initializing = new ArrayList<String>();
		initializing.add(LocalDate.of(2020, 05, 01).toString());
		initializing.add(LocalDate.of(2020, 05, 02).toString());
		initializing.add(LocalDate.of(2020, 05, 03).toString());
		initializing.add(LocalDate.of(2020, 05, 05).toString());
		initializing.add(LocalDate.of(2020, 05, 10).toString());
		initializing.add(LocalDate.of(2020, 05, 12).toString());
		
		apartmentDAO.initializeAvailableAndRentingDates(1, initializing);
		
		ArrayList<String> available = new ArrayList<String>();
		available.add(LocalDate.of(2020, 05, 01).toString());
		available.add(LocalDate.of(2020, 05, 05).toString());
		available.add(LocalDate.of(2020, 05, 10).toString());
		available.add(LocalDate.of(2020, 05, 12).toString());
		
		apartmentDAO.setAvailable(1, available);
		
		ArrayList<LocalDate> newDates = new ArrayList<LocalDate>();
		newDates.add(LocalDate.of(2020, 05, 01));
		newDates.add(LocalDate.of(2020, 05, 02));
		newDates.add(LocalDate.of(2020, 05, 03));
		newDates.add(LocalDate.of(2020, 05, 04));
		newDates.add(LocalDate.of(2020, 05, 06));
		newDates.add(LocalDate.of(2020, 05, 10));
		newDates.add(LocalDate.of(2020, 05, 11));
		newDates.add(LocalDate.of(2020, 05, 12));
		newDates.add(LocalDate.of(2020, 05, 13));
		
	    apartmentDAO.changeAvailableAndRentingDates(1, newDates);
	    
	    apartmentDAO.reduceAvailableDates(1, LocalDate.of(2020, 05, 10), 4);
	    
		
	}*/

}
