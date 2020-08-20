package dao;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import beans.Address;
import beans.Amenities;
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
	
	private ArrayList<Apartment> getActiveApartments(){
		ArrayList<Apartment> apartments = readFromFile();
		ArrayList<Apartment> filteredApartments = new ArrayList<Apartment>();
		
		for(Apartment a:apartments) {
			if(a.isActive()) 
				filteredApartments.add(a);
		}
		return filteredApartments;
	}
	
	private ArrayList<Apartment> getInactiveApartments(){
		ArrayList<Apartment> apartments = readFromFile();
		ArrayList<Apartment> filteredApartments = new ArrayList<Apartment>();
		
		for(Apartment a:apartments) {
			if(!a.isActive()) 
				filteredApartments.add(a);
		}
		return filteredApartments;
	}
	
	private ArrayList<Apartment> filterApartmentsByTypeAndAmenities(ArrayList<TypeOfApartment> types, ArrayList<Integer> amenities){
		ArrayList<Apartment> apartments = readFromFile();
		ArrayList<Apartment> filteredApartments = new ArrayList<Apartment>();
		
		for(Apartment a:apartments) {
			if(types.contains(a.getType())) {
				filteredApartments.add(a);
			}
			for(int id:a.getAmenities()) {
				if(amenities.contains(id) && !filteredApartments.contains(a)) {
					filteredApartments.add(a);
				}
			}
		}
		
		return filteredApartments;
	}
	
	private ArrayList<Apartment> filterApartmentsByTypeAmenitiesAndStatus(ArrayList<TypeOfApartment> types, ArrayList<Integer> amenities,
			ArrayList<Boolean> status){
		
		ArrayList<Apartment> apartments = readFromFile();
		ArrayList<Apartment> filteredApartments = new ArrayList<Apartment>();
		
		for(Apartment a:apartments) {
			if(types.contains(a.getType())) {
				filteredApartments.add(a);
			}
			for(int id:a.getAmenities()) {
				if(amenities.contains(id) && !filteredApartments.contains(a)) {
					filteredApartments.add(a);
				}
			}
			if(status.contains(a.isActive()) && !filteredApartments.contains(a)) {
				filteredApartments.add(a);
			}
		}
		
		return filteredApartments;
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
	
	public void deleteAmenitiesFromAllApartments(int amenitiesId) {
		ArrayList<Apartment> apartments = readFromFile();
		for(Apartment a:apartments) {
			ArrayList<Integer> amenities = a.getAmenities();
			if(amenities.contains(amenitiesId)) {
				amenities.remove(amenitiesId);
			}
		}
		writeInFile(apartments);
	}
	
	public ArrayList<Apartment> searchApartments(String location, LocalDate startDate, LocalDate endDate, int minPrice, int maxPrice,
								int minRooms, int maxRooms, int persons,ArrayList<Apartment> apartments) {
		
		ArrayList<Apartment> filtratedApartments = new ArrayList<Apartment>();
		
		for(Apartment a:apartments) {
			if(!location.equals("") && (a.getLocation().getAddress().getCity().toLowerCase().equals(location.toLowerCase()) || 
			   a.getLocation().getAddress().getCountry().toLowerCase().equals(location.toLowerCase()))) {
					filtratedApartments.add(a);
					System.out.println("Dodalo lokaciju");
			}
					
			if (startDate != null && a.getAvailableDates().contains(startDate.toString()) && !filtratedApartments.contains(a))
				filtratedApartments.add(a);
			else if (startDate != null && !a.getAvailableDates().contains(startDate.toString())  && filtratedApartments.contains(a)) {
				filtratedApartments.remove(a);
				System.out.println("Ukoloniko start date");
				continue;
			}
				
			if (endDate !=null && a.getAvailableDates().contains(endDate.toString()) && !filtratedApartments.contains(a))
				filtratedApartments.add(a);
			else if(endDate != null && !a.getAvailableDates().contains(endDate.toString()) && filtratedApartments.contains(a)) {
				filtratedApartments.remove(a);
				System.out.println("Ukoloniko end date");
				continue;
			}
				
			if (minPrice != 0 && a.getPricePerNight() >= minPrice && !filtratedApartments.contains(a))
				filtratedApartments.add(a);
			else if(a.getPricePerNight() < minPrice && filtratedApartments.contains(a)) {
				filtratedApartments.remove(a);
				System.out.println("Ukoloniko min price");
				continue;
			}
				
			if (maxPrice != 0 && a.getPricePerNight() <= maxPrice && !filtratedApartments.contains(a))
				filtratedApartments.add(a);
			else if(a.getPricePerNight() > maxPrice && filtratedApartments.contains(a)) {
				filtratedApartments.remove(a);
				System.out.println("Ukoloniko max price");
				continue;
			}
				
			if (minRooms != 0 && a.getNumberOfRooms() >= minRooms && !filtratedApartments.contains(a))
				filtratedApartments.add(a);
			else if(a.getNumberOfRooms() < minRooms && filtratedApartments.contains(a)) {
				filtratedApartments.remove(a);
				System.out.println("Ukoloniko min rooms");
				continue;
			}
			
			if (maxRooms != 0 && a.getNumberOfRooms() <= maxRooms && !filtratedApartments.contains(a))
				filtratedApartments.add(a);
			else if(a.getNumberOfRooms() > maxRooms && filtratedApartments.contains(a)) {
				filtratedApartments.remove(a);
				System.out.println("Ukoloniko max rooms");
				continue;
			}
			
		    if (persons != 0 && a.getNumberOfGuests() == persons && !filtratedApartments.contains(a))
				filtratedApartments.add(a);
			else if(a.getNumberOfGuests() != persons && filtratedApartments.contains(a)) {
				filtratedApartments.remove(a);
				System.out.println("Ukoloniko persons");
				continue;
			}			
		}
		
		return filtratedApartments;
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
	
	private ArrayList<Apartment> sortApartmentsAscending(){
		ArrayList<Apartment> sortedApartments = readFromFile();
		Collections.sort(sortedApartments);
		return sortedApartments;
	}
	
	private ArrayList<Apartment> sortApartmentsDescending(){
		ArrayList<Apartment> sortedApartments = readFromFile();
		Collections.reverse(sortedApartments);
		return sortedApartments;
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
				LocalTime.of(10, 0).toString(),LocalTime.of(14, 0).toString(),true,false));
		
		ApartmentDAO apartmentDAO = new ApartmentDAO();
		
		apartmentDAO.writeInFile(apartments);
		
		ArrayList<Apartment> apartmentFromFile = apartmentDAO.getAllApartments();
		
		for(Apartment a:apartmentFromFile) {
			System.out.println(a.getName() + " " + a.getHostUsername());
		}
		
		apartmentDAO.addApartment(new Apartment(2,"Mjesec",TypeOfApartment.ROOM,2,4,
				new Location(100,200,new Address("Srbija","Beograd",22000,"Pašićeva",50)),
				"gaga998",2000,
				LocalTime.of(10, 0).toString(),LocalTime.of(14, 0).toString(),false,false));
		
		apartmentFromFile = apartmentDAO.getApartmentsByHost("sladja997");
		System.out.println(apartmentFromFile.size());
		
		//apartmentDAO.addCommentToApartment(new Comment(400,"gaga998",1,"opis",5,true));
		
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
	    
	    ArrayList<Apartment> filter = apartmentDAO.searchApartments("Beograd", LocalDate.of(2020,05,01), LocalDate.of(2020,05,04), 1000, 1500, 1, 5, 4,
	    		apartmentDAO.getAllApartments());
	    
	    for(Apartment f:filter) {
	    	System.out.println(f.getId() + " " + f.getName());
	    }
	    
	    System.out.println("-------------------------------------------------------------");
	    
	    ArrayList<Apartment> rastuce = apartmentDAO.sortApartmentsAscending();
	    for(Apartment a:rastuce) {
	    	System.out.println(a.getId() + " " + a.getPricePerNight());
	    }
	    
	    System.out.println("-------------------------------------------------------------");
	    
	    ArrayList<Apartment> opadajuce = apartmentDAO.sortApartmentsDescending();
	    for(Apartment a:opadajuce) {
	    	System.out.println(a.getId() + " " + a.getPricePerNight());
	    }
	    
	    System.out.println("-------------------------------------------------------------");
	    
	    System.out.println(apartmentDAO.getActiveApartments().get(0).getName());
	    System.out.println(apartmentDAO.getInactiveApartments().get(0).getName());
	    
	    System.out.println("-------------------------------------------------------------");
	    ArrayList<TypeOfApartment> types = new ArrayList<TypeOfApartment>();
	    types.add(TypeOfApartment.ROOM);
	    types.add(TypeOfApartment.WHOLE_APARTMENT);
	    ArrayList<Integer> amenities = new ArrayList<Integer>();
	    amenities.add(400);
	    
	    ArrayList<Apartment> filteri = apartmentDAO.filterApartmentsByTypeAndAmenities(types, amenities);
	    
	    System.out.println(filteri.size());
	    
	    System.out.println("-------------------------------------------------------------");
	   
		
	}*/

}
