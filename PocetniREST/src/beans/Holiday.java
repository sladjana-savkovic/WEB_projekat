package beans;

import java.util.ArrayList;

public class Holiday {
	private ArrayList<String> holidays;
	
	public Holiday() {}

	public Holiday(ArrayList<String> holidays) {
		super();
		this.holidays = holidays;
	}

	public ArrayList<String> getHolidays() {
		return holidays;
	}

	public void setHolidays(ArrayList<String> holidays) {
		this.holidays = holidays;
	}
}
