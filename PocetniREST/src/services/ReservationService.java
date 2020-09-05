package services;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import beans.Amenities;
import beans.Gender;
import beans.Guest;
import beans.Reservation;
import dao.AmenitiesDAO;
import dao.GuestDAO;
import dao.ReservationDAO;;

@Path("")
public class ReservationService {

	@Context
	ServletContext ctx;
	
	@Context
	HttpServletRequest request;

	
	@GET
	@Path("/hosts_reservations/search/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Reservation> searchReservations(@PathParam("username") String username){
		ReservationDAO reservationDAO = new ReservationDAO();
		String u = username;
		if(u.equals("null")) {
			u = null;
		}
	
		return reservationDAO.searchReservationsByGuestUsername("gaga998", u);
	}
	
	@GET
	@Path("/guests_reservations")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Reservation> getReservationsByGuest(){
		//fali ime gosta
		ReservationDAO reservationDAO = new ReservationDAO();
		return reservationDAO.getReservationsByGuest("pero123");
	}
	
	@GET
	@Path("/hosts_reservations")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Reservation> getReservationsByHost(){
		//fali ime fomacina
		ReservationDAO reservationDAO = new ReservationDAO();
		return reservationDAO.getReservationByHostsApartments("gaga998");
	}
}
