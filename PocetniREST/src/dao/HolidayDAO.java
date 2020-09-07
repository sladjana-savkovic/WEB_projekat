package dao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import beans.Holiday;

public class HolidayDAO {

	private File file;
	private String path;
	
	public HolidayDAO() {
		try { 
		   path = Paths.get("WEB projekat\\PocetniREST\\WebContent\\data").toAbsolutePath().toString() + File.separator + "holidays.json";
		   file = new File(path);
			 
		   if (file.createNewFile()){
		    Holiday holiday = new Holiday();
		    writeInFile(holiday);
		   }
		  } catch (IOException e) {
		   e.printStackTrace();
		  }
	}
		
	public boolean isHoliday(String date) {
		Holiday holiday = readFromFile();
		return holiday.getHolidays().contains(date);
	}
	
	public void deleteHoliday(String date) {
		Holiday holiday = readFromFile();
		holiday.getHolidays().remove(date);
		writeInFile(holiday);
	}
	
	public void addHoliday(String date) {
		Holiday holiday = readFromFile();
		holiday.getHolidays().add(date);
		writeInFile(holiday);
	}
	
	public Holiday getAllHolidays() {
		Holiday holiday = readFromFile();
		return holiday;
	}
	
	private Holiday readFromFile() {
		Holiday holidays = new Holiday();
		ObjectMapper mapper = new ObjectMapper();	
		try {
			holidays = mapper.readValue(file, new TypeReference<Holiday>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
		return holidays;
	}
	
	private void writeInFile(Holiday holiday) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(file, holiday);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	/*public static void main(String[] args) {
		HolidayDAO holidayDao = new HolidayDAO();
		ArrayList<String> dates = new ArrayList<String>();
		dates.add("2020-09-07");
		
		holidayDao.writeInFile(new Holiday(dates));
	}*/
}
