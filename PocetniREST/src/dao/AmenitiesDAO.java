package dao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Amenities;
import beans.Apartment;


public class AmenitiesDAO {
	private File file;
	private String path;
	
	/*public static void main(String[] args) {
	Amenities a1 = new Amenities(0, "Klima", false);
	Amenities a2 = new Amenities(1, "TV", false);
	Amenities a3 = new Amenities(2, "Kada", false);
	
	ArrayList<Amenities> amenities = new ArrayList<>();
	amenities.add(a1);
	amenities.add(a2);
	amenities.add(a3);
	
	AmenitiesDAO aDAO = new AmenitiesDAO();
	aDAO.writeInFile(amenities);
	//Amenities a4 = new Amenities(3, "Veš mašina", false);
	//aDAO.addNewAmenities(a4);
	//aDAO.deleteAmenities(3);
	
	}*/
	
	public AmenitiesDAO() {
		File dataDir = new File(System.getProperty("catalina.base") + File.separator + "data");
		if(!dataDir.exists()) {
			dataDir.mkdir();
		}
		this.path = System.getProperty("catalina.base") + File.separator + "data" + File.separator + "amenities.json";
		//path = Paths.get("WEB projekat\\PocetniREST\\WebContent\\data").toAbsolutePath().toString() + File.separator + "amenities.json";
		file = new File(path);
		 try {
		   if (file.createNewFile()){
		    ArrayList<Amenities> amenities = new ArrayList<Amenities>();
		    writeInFile(amenities);
		   }
		  } catch (IOException e) {
		   e.printStackTrace();
		  }
	}
	
	private ArrayList<Amenities> readFromFile() {
		ArrayList<Amenities> amenities = new ArrayList<Amenities>();
		ObjectMapper mapper = new ObjectMapper();	
		try {
			amenities = mapper.readValue(file, new TypeReference<ArrayList<Amenities>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
		return amenities;
	}
	
	private void writeInFile(ArrayList<Amenities> amenities) {
		ObjectMapper mapper = new ObjectMapper();
		try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, amenities);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public int getLastId() {		
		ArrayList<Amenities> amenitiesFromFile = readFromFile();
		if(amenitiesFromFile.size() == 0) {
			return 0;
		}
		return amenitiesFromFile.get(amenitiesFromFile.size()-1).getId();
	}
	
	public Amenities getAmenitiesById(int id) {
		ArrayList<Amenities> amenitiesFromFile = readFromFile();
		for(Amenities a : amenitiesFromFile) {
			if(a.getId() == id) {
				return a;
			}
		}
		return null;
	}
	
	public ArrayList<Amenities> getAllAmenities(){
		ArrayList<Amenities> amenitiesFromFile = readFromFile();
		ArrayList<Amenities> retVal = new ArrayList<Amenities>();
		for(Amenities a:amenitiesFromFile) {
			if(!a.isDeleted())
				retVal.add(a);
		}
		return retVal;
	}
	
	public ArrayList<Amenities> getApartmentAmenities(int idApartment){
		ApartmentDAO apartmentDAO = new ApartmentDAO();
		Apartment apartment = apartmentDAO.getApartment(idApartment);
		ArrayList<Integer> amenitiesId = apartment.getAmenities();
		
		ArrayList<Amenities> amenitiesFromFile = readFromFile();
		ArrayList<Amenities> retVal = new ArrayList<Amenities>();
		
		for(Amenities a:amenitiesFromFile) {
			for(int i=0; i<amenitiesId.size(); i++) {
				if(a.getId() == amenitiesId.get(i)) {
					retVal.add(a);
				}
			}
				
		}
		return retVal;
	}
	
	public void addNewAmenities(Amenities amenities) {
		ArrayList<Amenities> amenitiesFromFile = readFromFile();
		
		amenitiesFromFile.add(amenities);
	
		writeInFile(amenitiesFromFile);
	}
	
	public void editNameOfAmenities(Amenities amenities) {
		ArrayList<Amenities> amenitiesFromFile = readFromFile();
		for(Amenities a: amenitiesFromFile) {
			if(a.getId() == amenities.getId()) {
				a.setName(amenities.getName());
				break;
			}
		}
		writeInFile(amenitiesFromFile);
	}
	
	public void deleteAmenities(int id) {
		ArrayList<Amenities> amenitiesFromFile = readFromFile();
		for(Amenities a: amenitiesFromFile) {
			if(a.getId() == id) {
				a.setDeleted(true);
				//TODO ovde treba pozvati metodu iz ApartmentDAO koja brise sadrzaj iz apartmana
				break;
			}
		}
		writeInFile(amenitiesFromFile);
	}
}

