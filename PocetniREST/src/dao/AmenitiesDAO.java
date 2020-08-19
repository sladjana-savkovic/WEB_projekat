package dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Amenities;


public class AmenitiesDAO {
	private String path;
	private File file;
	
	/*public static void main(String[] args) {
	Amenities a1 = new Amenities(0, "Klima", false);
	Amenities a2 = new Amenities(1, "TV", false);
	Amenities a3 = new Amenities(2, "Kada", false);
	
	ArrayList<Amenities> amenities = new ArrayList<>();
	amenities.add(a1);
	amenities.add(a2);
	amenities.add(a3);
	
	AmenitiesDAO aDAO = new AmenitiesDAO();
	//aDAO.writeInFile(amenities);
	Amenities a4 = new Amenities(3, "Veš mašina", false);
	//aDAO.addNewAmenities(a4);
	aDAO.deleteAmenities(3);
	
	}*/
	
	public AmenitiesDAO() {
		path = "data/amenities.json";
		file = new File(path);
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
		return amenitiesFromFile;
	}
	
	public void addNewAmenities(Amenities amenities) {
		ArrayList<Amenities> amenitiesFromFile = readFromFile();
		
		amenitiesFromFile.add(amenities);
	
		writeInFile(amenitiesFromFile);
	}
	
	public void editNameOfAmenities(Amenities amenities, String newName) {
		ArrayList<Amenities> amenitiesFromFile = readFromFile();
		for(Amenities a: amenitiesFromFile) {
			if(a.getId() == amenities.getId()) {
				a.setName(newName);
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

