package beans;

public class Admin extends User{
	
	public Admin() { 
		super(); 
	}

	public Admin(String username, String password, String name, String surname, Gender gender,boolean isBlocked) {
		super(username, password, name, surname, gender, TypeOfUser.ADMIN,isBlocked);
	}

	public Admin(Admin admin) {
		super((User)admin);
	}
}
