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
import beans.Apartment;
import beans.Gender;
import beans.Guest;
import beans.Reservation;
import beans.ReservationStatus;
import dao.AmenitiesDAO;
import dao.ApartmentDAO;
import dao.GuestDAO;
import dao.ReservationDAO;

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
	
		return reservationDAO.searchHostsReservationsByGuestUsername("gaga998", u);
	}
	
	@GET
	@Path("/guests_reservations")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Reservation> getReservationsByGuest(){
		//treba ime ulogovanog gosta
		ReservationDAO reservationDAO = new ReservationDAO();
		return reservationDAO.getReservationsByGuest("pero123");
	}
	
	@GET
	@Path("/guests_reservations/sort_ascending")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Reservation> sortGuestReservationsAscending(){
		ReservationDAO reservationDAO = new ReservationDAO();
		//treba ime ulogovanog gosta
		return reservationDAO.sortGuestsReservationsAscending("pero123");
	}
	
	@GET
	@Path("/guests_reservations/sort_descending")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Reservation> sortGuestReservationsDescending(){
		ReservationDAO reservationDAO = new ReservationDAO();
		//treba ime ulogovanog gosta
		return reservationDAO.sortGuestsReservationsDescending("pero123");
	}
	
	@GET
	@Path("/hosts_reservations")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Reservation> getReservationsByHost(){
		//treba ime ulogovanog domacina
		ReservationDAO reservationDAO = new ReservationDAO();
		return reservationDAO.getReservationByHostsApartments("gaga998");
	}
	
	@POST
	@Path("/hosts_reservations/filter")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Reservation> filterReservations(ArrayList<ReservationStatus> status){
		ReservationDAO reservationDAO = new ReservationDAO();
		//treba ime ulogovanog domacina
		return reservationDAO.filterHostsReservationsByStatus(status, "gaga998");
	}
	
	

	@GET
	@Path("/hosts_reservations/sort_ascending")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Reservation> sortHostReservationsAscending(){
		ReservationDAO reservationDAO = new ReservationDAO();
		//treba ime ulogovanog domacina
		return reservationDAO.sortHostsReservationsAscending("gaga998");
	}
	
	@GET
	@Path("/hosts_reservations/sort_descending")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Reservation> sortHostReservationsDescending(){
		ReservationDAO reservationDAO = new ReservationDAO();
		//treba ime ulogovanog domacina
		return reservationDAO.sortHostsReservationsDescending("gaga998");
	}
}
