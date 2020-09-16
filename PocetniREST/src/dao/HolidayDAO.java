package dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Holiday;

public class HolidayDAO {

	private File file;
	private String path;
	
	public HolidayDAO() {
		File dataDir = new File(System.getProperty("catalina.base") + File.separator + "data");
		if(!dataDir.exists()) {
			dataDir.mkdir();
		}
		this.path = System.getProperty("catalina.base") + File.separator + "data" + File.separator + "holidays.json";
		file = new File(path);
		try { 
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
			holidays = mapper.readValue(Paths.get(path).toFile(), new TypeReference<Holiday>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
		return holidays;
	}
	
	private void writeInFile(Holiday holiday) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(holiday);
			BufferedWriter writer = new BufferedWriter (new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8));
		    writer.write(json);  
		    writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
