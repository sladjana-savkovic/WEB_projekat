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
import beans.Comment;
import beans.Gender;
import beans.Guest;
import beans.Reservation;
import beans.ReservationStatus;
import dao.AmenitiesDAO;
import dao.ApartmentDAO;
import dao.CommentDAO;
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
	
		return reservationDAO.searchReservationsByGuestUsername(u, reservationDAO.getReservationByHostsApartments("gaga998"));
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
		return reservationDAO.sortReservationsAscending(reservationDAO.getReservationsByGuest("pero123"));
	}
	
	@GET
	@Path("/guests_reservations/sort_descending")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Reservation> sortGuestReservationsDescending(){
		ReservationDAO reservationDAO = new ReservationDAO();
		//treba ime ulogovanog gosta
		return reservationDAO.sortReservationsDescending(reservationDAO.getReservationsByGuest("pero123"));
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
		return reservationDAO.filterReservationsByStatus(status, reservationDAO.getReservationByHostsApartments("gaga998"));
	}
	
	

	@GET
	@Path("/hosts_reservations/sort_ascending")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Reservation> sortHostReservationsAscending(){
		ReservationDAO reservationDAO = new ReservationDAO();
		//treba ime ulogovanog domacina
		return reservationDAO.sortReservationsAscending(reservationDAO.getReservationByHostsApartments("gaga998"));
	}
	
	@GET
	@Path("/hosts_reservations/sort_descending")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Reservation> sortHostReservationsDescending(){
		ReservationDAO reservationDAO = new ReservationDAO();
		//treba ime ulogovanog domacina
		return reservationDAO.sortReservationsDescending(reservationDAO.getReservationByHostsApartments("gaga998"));
	}
	
	@POST
	@Path("/accept_reservation")
	@Consumes(MediaType.APPLICATION_JSON)
	public void acceptReservation(int id) {
		ReservationDAO reservationDAO = new ReservationDAO();
		reservationDAO.acceptReservationByHost(reservationDAO.getReservationById(id));
	}
	
	@POST
	@Path("/finish_reservation")
	@Consumes(MediaType.APPLICATION_JSON)
	public void finishReservation(int id) {
		ReservationDAO reservationDAO = new ReservationDAO();
		reservationDAO.finishReservationByHost(reservationDAO.getReservationById(id));
	}
	
	@POST
	@Path("/refuse_reservation")
	@Consumes(MediaType.APPLICATION_JSON)
	public void refuseReservation(int id) {
		ReservationDAO reservationDAO = new ReservationDAO();
		reservationDAO.refuseReservationByHost(reservationDAO.getReservationById(id));
	}
	
	@POST
	@Path("/cancel_reservation")
	@Consumes(MediaType.APPLICATION_JSON)
	public void cancelReservation(int id) {
		ReservationDAO reservationDAO = new ReservationDAO();
		reservationDAO.cancelReservationByGuest(reservationDAO.getReservationById(id));
	}
	
	@GET
	@Path("/reservations/new_id")
	public int getNewId() {
		ReservationDAO reservationDAO = new ReservationDAO();
		return reservationDAO.getLastId()+1;
	}
	
	@POST
	@Path("/reservations/add")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addReservation(Reservation reservation) {
		ReservationDAO reservationDAO = new ReservationDAO();	
		reservationDAO.addNewReservation(reservation);
	}
	
	@GET
	@Path("/reservations/total_price/{date}/{numberNight}/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public double getTotalPrice(@PathParam("date") String date, @PathParam("numberNight") int numberNight, @PathParam("id")  int id){
		ReservationDAO reservationDAO = new ReservationDAO();
	
		return reservationDAO.getTotalPrice(date, numberNight, id);
	}
	
	@GET
	@Path("/reservations/max_num_night/{date}/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public int getMaxNumNight(@PathParam("date") String date, @PathParam("id")  int id){
		ReservationDAO reservationDAO = new ReservationDAO();
	
		return reservationDAO.getMaxNumberNight(date, id);
	}
	
}
