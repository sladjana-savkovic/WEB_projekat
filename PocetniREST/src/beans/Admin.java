package beans;

public class Admin extends User{
	
	public Admin() { 
		super(); 
	}

	public Admin(String username, String password, String name, String surname, Gender gender, TypeOfUser typeOfUser,boolean isBlocked) {
		super(username, password, name, surname, gender, typeOfUser, isBlocked);
	}

	public Admin(Admin admin) {
		super((User)admin);
	}
}
