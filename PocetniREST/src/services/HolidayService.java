package services;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
}
