package dto;

import java.util.ArrayList;
import beans.TypeOfApartment;

public class FilterApartmentsDTO {
	ArrayList<TypeOfApartment> types;
	ArrayList<Integer> amenities;
	ArrayList<String> status;
	
	public FilterApartmentsDTO() {}

	public FilterApartmentsDTO(ArrayList<TypeOfApartment> types, ArrayList<Integer> amenities,
			ArrayList<String> status) {
		this.types = types;
		this.amenities = amenities;
		this.status = status;
	}

	public ArrayList<TypeOfApartment> getTypes() {
		return types;
	}

	public void setTypes(ArrayList<TypeOfApartment> types) {
		this.types = types;
	}

	public ArrayList<Integer> getAmenities() {
		return amenities;
	}

	public void setAmenities(ArrayList<Integer> amenities) {
		this.amenities = amenities;
	}

	public ArrayList<String> getStatus() {
		return status;
	}

	public void setStatus(ArrayList<String> status) {
		this.status = status;
	}
}
