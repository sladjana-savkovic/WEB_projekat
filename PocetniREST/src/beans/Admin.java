package beans;

public class Admin extends User{
	
	public Admin() { 
		super(); 
	}

	public Admin(String username, String password, String name, String surname, Gender gender) {
		super(username, password, name, surname, gender, TypeOfUser.ADMIN);
	}

	public Admin(Admin admin) {
		super((User)admin);
	}
}
