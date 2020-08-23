package dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Gender;
import beans.Host;

public class HostDAO {
	
	private String path;
	private File file;
	
	public HostDAO() {
		path = "C:\\Users\\pc\\Desktop\\WEB projekat\\data" + File.separator + "hosts.json";
		file = new File(path);
	}
	
	public Host getHost(String hostUsername) {
		ArrayList<Host> hosts = readFromFile();
		for(Host host:hosts) {
			if(host.getUsername().equals(hostUsername))
				return host;
		}
		return null;
	}
	
	public boolean checkPassword(String hostUsername,String password) {
		Host host = getHost(hostUsername);
		if(host == null || !host.getPassword().equals(password))
			return false;
		
		return true;
	}
	
	public ArrayList<Host> getAllHosts(){
		ArrayList<Host> hosts = readFromFile();
		return hosts;
	}
	
	public ArrayList<Host> getHostsByUsernameAndGender(String hostUsername, Gender gender){
		ArrayList<Host> hosts = readFromFile();
		ArrayList<Host> filtratedHosts = new ArrayList<Host>();
		for(Host h:hosts) {
			if(hostUsername.equals(" ") && h.getGender() == gender) {
				filtratedHosts.add(h);
			}
			else if(!hostUsername.equals(" ") && h.getUsername().toLowerCase().contains(hostUsername.toLowerCase()) && h.getGender() == gender)
				filtratedHosts.add(h);
		}
		return filtratedHosts;
	}
	
	public void editHost(Host host) {
		ArrayList<Host> hosts = readFromFile();
		for(Host h:hosts) {
			if(h.getUsername().equals(host.getUsername())) {
				h.setName(host.getName());
				h.setSurname(host.getSurname());
				h.setGender(host.getGender());
				h.setPassword(host.getPassword());
			}
		}
		writeInFile(hosts);
	}
	
	public void addHost(Host host) {
		ArrayList<Host> hosts = readFromFile();
		hosts.add(host);
		writeInFile(hosts);
	}
	
	public void blockHost(String hostUsername) {
		ArrayList<Host> hosts = readFromFile();
		for(Host host:hosts) {
			if(host.getUsername().equals(hostUsername)) {
				host.setBlocked(true);
			}
		}
		writeInFile(hosts);
	}
	
	public void addApartmentForRentToHost(String hostUsername,int apartmentId) {
		ArrayList<Host> hosts = readFromFile();
		for(Host host:hosts) {
			if(host.getUsername().equals(hostUsername)) {
				ArrayList<Integer> apartmentsForRent = host.getApartmentsForRent();
				apartmentsForRent.add(apartmentId);
				host.setApartmentsForRent(apartmentsForRent);
			}
		}
		writeInFile(hosts);
	}
	
	private ArrayList<Host> readFromFile() {
		ArrayList<Host> hosts = new ArrayList<Host>();
		ObjectMapper mapper = new ObjectMapper();	
		try {
			hosts = mapper.readValue(file, new TypeReference<ArrayList<Host>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
		return hosts;
	}
	private void writeInFile(ArrayList<Host> hosts) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(file, hosts);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	/*public static void main(String[] args) {
		
		ArrayList<Host> hosts = new ArrayList<Host>();
        
		hosts.add(new Host("gaga998","sifra123","Dragana","Čarapić",Gender.FEMALE));
		hosts.add(new Host("sladja997","sifra789","Slađana","Savković",Gender.FEMALE));
        
        HostDAO hostDao = new HostDAO();
        
        hostDao.writeInFile(hosts);
        
        ArrayList<Host> hostsFromFile =  hostDao.getAllHosts();
        
        for(Host host : hostsFromFile) {
        	System.out.println(host.getName() + " " + host.getSurname() );
        }
        
        hostDao.addApartmentForRentToHost("sladja997", 103);

	}*/
}
