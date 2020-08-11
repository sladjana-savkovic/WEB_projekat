package beans;

import java.util.ArrayList;

public class Host extends User{
	
	private ArrayList<Integer> apartmentsForRent;
	
	public Host() {
		super();
		apartmentsForRent = new ArrayList<Integer>();
	}

	public Host(String username, String password, String name, String surname, Gender gender) {
		super(username, password, name, surname, gender, TypeOfUser.HOST);
		apartmentsForRent = new ArrayList<Integer>();
	}

	public Host(Host host) {
		super((User)host);
		apartmentsForRent = new ArrayList<Integer>();
	}

	public ArrayList<Integer> getApartmentsForRent() {
		return apartmentsForRent;
	}

	public void setApartmentsForRent(ArrayList<Integer> apartmentsForRent) {
		this.apartmentsForRent = apartmentsForRent;
	}
	
	
}
