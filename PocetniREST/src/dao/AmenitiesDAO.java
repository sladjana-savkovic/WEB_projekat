package dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Amenities;
import beans.Apartment;


public class AmenitiesDAO {
	private File file;
	private String path;
	
	public AmenitiesDAO() {
		File dataDir = new File(System.getProperty("catalina.base") + File.separator + "data");
		if(!dataDir.exists()) {
			dataDir.mkdir();
		}
		this.path = System.getProperty("catalina.base") + File.separator + "data" + File.separator + "amenities.json";
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
			amenities = mapper.readValue(Paths.get(path).toFile(), new TypeReference<ArrayList<Amenities>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
		return amenities;
	}
	
	private void writeInFile(ArrayList<Amenities> amenities) {
		ObjectMapper mapper = new ObjectMapper();
		try {
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(amenities);
            BufferedWriter writer = new BufferedWriter (new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8));
		    writer.write(json);  
		    writer.close();
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
		AmenitiesDAO amenitiesDAO = new AmenitiesDAO();
		
		Apartment apartment = apartmentDAO.getApartment(idApartment);
		
		if(apartment == null) {
			return new ArrayList<Amenities>();
		}
		ArrayList<Amenities> retVal = new ArrayList<Amenities>();
		
		for(int a:apartment.getAmenities()) {
			if(amenitiesDAO.getAmenitiesById(a) != null)
				retVal.add(amenitiesDAO.getAmenitiesById(a));
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
				break;
			}
		}
		writeInFile(amenitiesFromFile);
	}
}

