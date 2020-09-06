package dao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Admin;


public class AdminDAO {
	
	private String path;
	private File file;
	
	public AdminDAO() {
		path = Paths.get("WEB projekat\\data").toAbsolutePath().toString() + File.separator + "admins.json";
		file = new File(path);
	}
	
	public Admin getAdmin(String adminUsername) {
		ArrayList<Admin> admins = readFromFile();
		for(Admin admin:admins) {
			if(admin.getUsername().equals(adminUsername))
				return admin;
		}
		return null;
	}
	
	public boolean checkPassword(String adminUsername,String password) {
		Admin admin = getAdmin(adminUsername);
		if(admin == null || !admin.getPassword().equals(password))
			return false;
		
		return true;
	}
	
	public void editAdmin(Admin admin) {
		ArrayList<Admin> admins = readFromFile();
		for(Admin a:admins) {
			if(a.getUsername().equals(admin.getUsername())) {
				a.setName(admin.getName());
				a.setSurname(admin.getSurname());
				a.setGender(admin.getGender());
				a.setPassword(admin.getPassword());
			}
		}
		writeInFile(admins);
	}
	
	private ArrayList<Admin> readFromFile() {
		ArrayList<Admin> admins = new ArrayList<Admin>();
		ObjectMapper mapper = new ObjectMapper();	
		try {
			admins = mapper.readValue(file, new TypeReference<ArrayList<Admin>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
		return admins;
	}
	private void writeInFile(ArrayList<Admin> admins) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(file, admins);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	/*public static void main(String[] args) {
		
		ArrayList<Admin> admins = new ArrayList<Admin>();
        
		admins.add(new Admin("marko88","sifra123","Marko","Marković",Gender.MALE));
		admins.add(new Admin("zarko65","sifra789","Žarko","Žarkić",Gender.MALE));
        
        AdminDAO adminDao = new AdminDAO();
        
        adminDao.writeInFile(admins);
        
        Admin adm =  adminDao.getAdmin("marko88");
        
        System.out.println(adm.getName() + " " + adm.getSurname());
        
        adm.setPassword("lozinka");
        
        adminDao.editAdmin(adm);     

	}*/

}
