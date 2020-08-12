package beans;

public class Comment {
	private int id;
	private String guestUsername;
	private int apartmentId;
	private String description;
	private RatingOfApartment rating;
	private boolean isApproved;
	
	public Comment() {}

	public Comment(int id, String guestUsername, int apartmentId, String description, RatingOfApartment rating,boolean isApproved) {
		this.id = id;
		this.guestUsername = guestUsername;
		this.apartmentId = apartmentId;
		this.description = description;
		this.rating = rating;
		this.isApproved = isApproved;
	}
	
	public Comment(Comment comment) {
		this.id = comment.id;
		this.guestUsername = comment.guestUsername;
		this.apartmentId = comment.apartmentId;
		this.description = comment.description;
		this.rating = comment.rating;
		this.isApproved = comment.isApproved;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGuestUsername() {
		return guestUsername;
	}

	public void setGuestUsername(String guestUsername) {
		this.guestUsername = guestUsername;
	}

	public int getApartmentId() {
		return apartmentId;
	}

	public void setApartmentId(int apartmentId) {
		this.apartmentId = apartmentId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public RatingOfApartment getRating() {
		return rating;
	}

	public void setRating(RatingOfApartment rating) {
		this.rating = rating;
	}

	public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}
	
}
