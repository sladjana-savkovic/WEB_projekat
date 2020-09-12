package services;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Holiday;
import dao.HolidayDAO;

@Path ("")
public class HolidayService {

	@Context
	ServletContext ctx;
	
	@Context
	HttpServletRequest request;
	
	private HolidayDAO getHolidayDAO() {
		HolidayDAO holidayDAO = (HolidayDAO) ctx.getAttribute("holidayDAO");
		if(holidayDAO == null) {
			holidayDAO = new HolidayDAO();
			ctx.setAttribute("holidayDAO", holidayDAO);
		}
		return holidayDAO;
	}
	
	@GET
	@Path("/holidays")
	@Produces(MediaType.APPLICATION_JSON)
	public Holiday getHolidays() {
		HolidayDAO holidayDAO = getHolidayDAO();
		return holidayDAO.getAllHolidays();
	}
	
	@DELETE
	@Path("/holidays/delete")
	public void deleteHoliday(String date) {
		HolidayDAO holidayDAO = getHolidayDAO();
		holidayDAO.deleteHoliday(date);
	}
	
	@GET
	@Path("/holidays/contains/{date}")
	public int checkIfContain(@PathParam("date") String date) {
		HolidayDAO holidayDAO = getHolidayDAO();
		if(holidayDAO.isHoliday(date)) {
			return 1;
		}
		return 0;
	}
	
	@POST
	@Path("/holidays/add")
	public void addHoliday(String date) {
		HolidayDAO holidayDAO = getHolidayDAO();
		holidayDAO.addHoliday(date);
	}
}
