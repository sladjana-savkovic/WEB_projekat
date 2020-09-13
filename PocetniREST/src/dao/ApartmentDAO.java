package dao;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import beans.Apartment;
import beans.Comment;
import beans.Reservation;
import beans.TypeOfApartment;

public class ApartmentDAO {

	private File file;
	private String path;
	
	public ApartmentDAO() {
		File dataDir = new File(System.getProperty("catalina.base") + File.separator + "data");
		if(!dataDir.exists()) {
			dataDir.mkdir();
		}
		this.path = System.getProperty("catalina.base") + File.separator + "data" + File.separator + "apartments.json";
		file = new File(path);
		 try {
		   if (file.createNewFile()){
		    ArrayList<Apartment> apartments = new ArrayList<Apartment>();
		    writeInFile(apartments);
		   }
		  } catch (IOException e) {
		   e.printStackTrace();
		  }
	}
	
	public int getLastId() {
		ArrayList<Apartment> apartments = readFromFile();
		if(apartments.size() == 0) {
			return 0;
		}
		return apartments.get(apartments.size() - 1).getId();
	}
	
	public void deleteApartmentImages(int id) {
		ArrayList<Apartment> apartments = readFromFile();
		for(Apartment apartment:apartments) {
			if(apartment.getId() == id)
				apartment.getPictures().clear();
		}
		writeInFile(apartments);
	}
	
	public void deleteAmenitiesFromAllApartments(int amenitiesId) {
		ArrayList<Apartment> apartments = readFromFile();
		for(Apartment apartment:apartments) {
			if(apartment.getAmenities().contains(amenitiesId)) {
				int index = apartment.getAmenities().indexOf(amenitiesId);
				apartment.getAmenities().remove(index);
			}
		}
		writeInFile(apartments);
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
		ArrayList<Apartment> undeletedApartments = new ArrayList<Apartment>();
		
		for(Apartment a:apartments) {
			if(!a.isDeleted())
				undeletedApartments.add(a);
		}
		return undeletedApartments;
	}
	
	public ArrayList<Apartment> getApartmentsByHost(String hostUsername){
		ArrayList<Apartment> allApartments = readFromFile();
		ArrayList<Apartment> filtratedApartmens = new ArrayList<Apartment>();
		
		for(Apartment apartment:allApartments) {
			if(apartment.getHostUsername().equals(hostUsername) && !apartment.isDeleted()) {
				filtratedApartmens.add(apartment);
			}
		}
		return filtratedApartmens;
	}
	
	public ArrayList<Apartment> getActiveApartments(){
		ArrayList<Apartment> apartments = readFromFile();
		ArrayList<Apartment> filteredApartments = new ArrayList<Apartment>();
		
		for(Apartment a:apartments) {
			if(a.isActive() && !a.isDeleted()) 
				filteredApartments.add(a);
		}
		return filteredApartments;
	}
	
	public ArrayList<Apartment> getInactiveApartments(){
		ArrayList<Apartment> apartments = readFromFile();
		ArrayList<Apartment> filteredApartments = new ArrayList<Apartment>();
		
		for(Apartment a:apartments) {
			if(!a.isActive() && !a.isDeleted()) 
				filteredApartments.add(a);
		}
		return filteredApartments;
	}
	
	public void addImageToApartment(int apartmentId, String image) {
		ArrayList<Apartment> apartments = readFromFile();
		
		for(Apartment a:apartments) {
			if(a.getId() == apartmentId) {
				a.getPictures().add(image);
				break;
			}
		}
		writeInFile(apartments);
	}
	
	public ArrayList<Apartment> filterApartmentsByTypeAmenitiesAndStatus(ArrayList<TypeOfApartment> types, ArrayList<Integer> amenities,
			ArrayList<String> status, ArrayList<Apartment> apartments){
		
		ArrayList<Apartment> filteredApartments = new ArrayList<Apartment>();
		
		for(Apartment a:apartments) {
			
			if((types.size() != 0 && types.contains(a.getType())) || types.size() == 0){
				if((status.size() != 0 && a.isActive() && status.contains("active")) 
						|| (status.size() != 0 && !a.isActive() && status.contains("inactive")) 
						|| status.size() == 0) {
					if(amenities.size() != 0) {
						for(int id:a.getAmenities()) {
							if(amenities.contains(id) && !filteredApartments.contains(a)) {
								filteredApartments.add(a);
								break;
							}
						}
					}
					else if(amenities.size() == 0 && !filteredApartments.contains(a)) {
						filteredApartments.add(a);
					}
				}
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
				a.setAvailableDates(apartment.getAvailableDates());
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
	
	public ArrayList<Apartment> searchApartments(String city, String startDate, String endDate, int minPrice, int maxPrice,
								int minRooms, int maxRooms, int persons,ArrayList<Apartment> apartments) {
		
		ArrayList<Apartment> filtratedApartments = new ArrayList<Apartment>();
		
		for(Apartment a:apartments) {
			if(!city.equals("null") && a.getLocation().getAddress().getCity().toLowerCase().equals(city.toLowerCase())) {
					filtratedApartments.add(a);
			}
			else if(!city.equals("null") && !a.getLocation().getAddress().getCity().toLowerCase().equals(city.toLowerCase())) {
				if(filtratedApartments.contains(a))
					filtratedApartments.remove(a);
				continue;
			}
					
			if (!startDate.equals("null") && a.getAvailableDates().contains(startDate) && !filtratedApartments.contains(a)) {
				filtratedApartments.add(a);
			}
			else if (!startDate.equals("null") && !a.getAvailableDates().contains(startDate)) {
				if(filtratedApartments.contains(a))
					filtratedApartments.remove(a);
				continue;
			}
				
			if (!endDate.equals("null") && a.getAvailableDates().contains(endDate.toString()) && !filtratedApartments.contains(a)) {
				filtratedApartments.add(a);
			}
			else if(!endDate.equals("null") && !a.getAvailableDates().contains(endDate.toString())) {
				if(filtratedApartments.contains(a))
					filtratedApartments.remove(a);
				continue;
			}
			
			if(!startDate.equals("null") && !endDate.equals("null")) {
				int difference = Period.between(LocalDate.parse(startDate), LocalDate.parse(endDate)).getDays();
				for(int i=1; i<difference; i++) {
					LocalDate date = LocalDate.parse(startDate).plusDays(i);
					if(!a.getAvailableDates().contains(date.toString())) {
						if(filtratedApartments.contains(a))
							filtratedApartments.remove(a);
						continue;
					}
				}
			}
				
			if (minPrice != 0 && a.getPricePerNight() >= minPrice && !filtratedApartments.contains(a)) {
				filtratedApartments.add(a);
			}
			else if(minPrice != 0 && a.getPricePerNight() < minPrice) {
				if(filtratedApartments.contains(a))
					filtratedApartments.remove(a);
				continue;
			}
				
			if (maxPrice != 0 && a.getPricePerNight() <= maxPrice && !filtratedApartments.contains(a)) {
				filtratedApartments.add(a);
			}
			else if(maxPrice != 0 && a.getPricePerNight() > maxPrice) {
				if(filtratedApartments.contains(a))
					filtratedApartments.remove(a);
				continue;
			}
				
			if (minRooms != 0 && a.getNumberOfRooms() >= minRooms && !filtratedApartments.contains(a)) {
				filtratedApartments.add(a);
			}
			else if(minRooms !=0 && a.getNumberOfRooms() < minRooms) {
				if(filtratedApartments.contains(a))
					filtratedApartments.remove(a);
				continue;
			}
			
			if (maxRooms != 0 && a.getNumberOfRooms() <= maxRooms && !filtratedApartments.contains(a)) {
				filtratedApartments.add(a);
			}
			else if(maxRooms !=0 && a.getNumberOfRooms() > maxRooms) {
				if(filtratedApartments.contains(a))
					filtratedApartments.remove(a);
				continue;
			}
			
		    if (persons != 0 && a.getNumberOfGuests() == persons && !filtratedApartments.contains(a)) {
				filtratedApartments.add(a);
		    }
			else if(persons !=0 && a.getNumberOfGuests() != persons) {
				if(filtratedApartments.contains(a))
					filtratedApartments.remove(a);
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
	
	public void backAvailableDates(int apartmentId, String startDate, int numberNight) {
		Apartment apartment = getApartment(apartmentId);
		ArrayList<String> dates = apartment.getAvailableDates();
		for(int i=0; i<numberNight; i++) {
			dates.add(LocalDate.parse(startDate).plusDays(i).toString());
		}
		
		setAvailable(apartmentId, dates);
		sortAvailableDates(apartmentId);
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
	
	public void sortAvailableDates(int id) {
		ArrayList<Apartment> apartments = readFromFile();
		ArrayList<LocalDate> dates = new ArrayList<LocalDate>();
		ArrayList<String> datesStr = new ArrayList<String>();
		
		for(Apartment a:apartments) {
			if(a.getId() == id) {
				for(String d : a.getAvailableDates()) {
					dates.add(LocalDate.parse(d));
				}
			}
		}
		Collections.sort(dates);
		
		for(LocalDate d : dates) {
			datesStr.add(d.toString());
		}
		
		for(Apartment a:apartments) {
			if(a.getId() == id) {
				a.setAvailableDates(datesStr);
			}
		}
		
		writeInFile(apartments);
		
	}
	
	public void changeAvailableAndRentingDates(int apartmentId, ArrayList<LocalDate> newRentingDates, ArrayList<LocalDate> oldRentingDates) {
		ArrayList<Apartment> apartments = readFromFile();
		for(Apartment a:apartments) {
			if(a.getId() == apartmentId) {
				ArrayList<LocalDate> newDates = findDifferencesBetweenDates(newRentingDates, oldRentingDates);
				ArrayList<LocalDate> deletedDates = findDifferencesBetweenDates(oldRentingDates, newRentingDates);	
				
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
	
	public ArrayList<Apartment> sortApartmentsAscending(ArrayList<Apartment> apartments){
		Collections.sort(apartments);
		return apartments;
	}
	
	public ArrayList<Apartment> sortApartmentsDescending(ArrayList<Apartment> apartments){
		Collections.sort(apartments,Collections.reverseOrder());
		return apartments;
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
