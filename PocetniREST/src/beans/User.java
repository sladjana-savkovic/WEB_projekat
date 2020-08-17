package beans;

public abstract class User {
	private String username;
	private String password;
	private String name;
	private String surname;
	private Gender gender;
	private TypeOfUser typeOfUser;
	private boolean isBlocked;
	
	public User() {}
	
	public User(String username, String password, String name, String surname, Gender gender, TypeOfUser typeOfUser,boolean isBlocked) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.typeOfUser = typeOfUser;
		this.isBlocked = isBlocked;
	}
	
	public User(User user) {
		this.username = user.username;
		this.password = user.password;
		this.name = user.name;
		this.surname = user.surname;
		this.gender = user.gender;
		this.typeOfUser = user.typeOfUser;
		this.isBlocked = user.isBlocked;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public TypeOfUser getTypeOfUser() {
		return typeOfUser;
	}

	public void setTypeOfUser(TypeOfUser typeOfUser) {
		this.typeOfUser = typeOfUser;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}
	
}
