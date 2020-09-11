package services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

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

import beans.Reservation;
import beans.ReservationStatus;
import beans.TypeOfUser;
import beans.User;
import dao.ApartmentDAO;
import dao.ReservationDAO;

@Path("")
public class ReservationService {

	@Context
	ServletContext ctx;
	
	@Context
	HttpServletRequest request;
	
	private ReservationDAO getReservationDAO() {
		ReservationDAO reservationDAO = (ReservationDAO) ctx.getAttribute("reservationDAO");
		if(reservationDAO == null) {
			reservationDAO = new ReservationDAO();
			ctx.setAttribute("reservationDAO", reservationDAO);
		}
		return reservationDAO;
	}
	
	@GET
	@Path("/reservations/apartment_delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean checkIfApartmentHasReservation(@PathParam("id") int id) {
		ReservationDAO reservationDAO = getReservationDAO();
		return reservationDAO.checkIfApartmentHasReservation(id);
	}
	
	@POST
	@Path("/accept_reservation")
	@Consumes(MediaType.APPLICATION_JSON)
	public void acceptReservation(int id) {
		ReservationDAO reservationDAO = getReservationDAO();
		ApartmentDAO apartmentDAO = new ApartmentDAO();	
		Reservation r = reservationDAO.getReservationById(id);
		
		reservationDAO.acceptReservationByHost(r);
		
		apartmentDAO.reduceAvailableDates(r.getApartmentId(), LocalDate.parse(r.getStartDate()), r.getNumberOfNights());
		
	}
	
	@POST
	@Path("/finish_reservation")
	@Consumes(MediaType.APPLICATION_JSON)
	public void finishReservation(int id) {
		ReservationDAO reservationDAO = getReservationDAO();
		reservationDAO.finishReservationByHost(reservationDAO.getReservationById(id));
	}
	
	@POST
	@Path("/refuse_reservation")
	@Consumes(MediaType.APPLICATION_JSON)
	public void refuseReservation(int id) {
		ReservationDAO reservationDAO = getReservationDAO();
		ApartmentDAO apartmentDAO = new ApartmentDAO();
		Reservation r = reservationDAO.getReservationById(id);
		
		if(r.getStatus().equals(ReservationStatus.ACCEPTED)) {
			apartmentDAO.backAvailableDates(r.getApartmentId(), r.getStartDate(), r.getNumberOfNights());
		}
		
		reservationDAO.refuseReservationByHost(reservationDAO.getReservationById(id));
	}
	
	@POST
	@Path("/cancel_reservation")
	@Consumes(MediaType.APPLICATION_JSON)
	public void cancelReservation(int id) {
		ReservationDAO reservationDAO = getReservationDAO();
		ApartmentDAO apartmentDAO = new ApartmentDAO();
		
		Reservation r = reservationDAO.getReservationById(id);
		reservationDAO.cancelReservationByGuest(r);
		
		if(r.getStatus().equals(ReservationStatus.ACCEPTED)) {
		apartmentDAO.backAvailableDates(r.getApartmentId(), r.getStartDate(), r.getNumberOfNights());
		}
	}
	
	@GET
	@Path("/reservations/new_id")
	public int getNewId() {
		ReservationDAO reservationDAO = getReservationDAO();
		return reservationDAO.getLastId()+1;
	}
	
	@POST
	@Path("/reservations/add")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addReservation(Reservation reservation) {
		ReservationDAO reservationDAO = getReservationDAO();
		ApartmentDAO apartmentDAO = new ApartmentDAO();
	
		
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		
		if(loggedUser != null) {
			reservation.setGuestUsername(loggedUser.getUsername());
			reservationDAO.addNewReservation(reservation);
			//apartmentDAO.reduceAvailableDates(reservation.getApartmentId(), LocalDate.parse(reservation.getStartDate()), reservation.getNumberOfNights());
		}
		
	}
	
	@GET
	@Path("/reservations/total_price/{date}/{numberNight}/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public double getTotalPrice(@PathParam("date") String date, @PathParam("numberNight") int numberNight, @PathParam("id")  int id){
		ReservationDAO reservationDAO = getReservationDAO();
	
		return reservationDAO.getTotalPrice(date, numberNight, id);
	}
	
	@GET
	@Path("/reservations/max_num_night/{date}/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public int getMaxNumNight(@PathParam("date") String date, @PathParam("id")  int id){
		ReservationDAO reservationDAO = getReservationDAO();
	
		return reservationDAO.getMaxNumberNight(date, id);
	}
	
	@GET
	@Path("/reservations")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Reservation> getReservations(){
		ReservationDAO reservationDAO = getReservationDAO();
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		
		if(loggedUser == null) {
			return new ArrayList<Reservation>();
		}
		else if(loggedUser.getTypeOfUser() == TypeOfUser.GUEST) {
			return reservationDAO.getReservationsByGuest(loggedUser.getUsername());
		}
		else if (loggedUser.getTypeOfUser() == TypeOfUser.HOST) {
			return reservationDAO.getReservationByHostsApartments(loggedUser.getUsername());
		}
		
		return reservationDAO.getAllReservations();
		
	}
	
	@GET
	@Path("/reservations/search/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Reservation> searchAllReservations(@PathParam("username") String username){
		ReservationDAO reservationDAO = getReservationDAO();
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		
		String u = username;
		if(u.equals("null")) {
			u = null;
		}
		
		
		if(loggedUser == null) {
			return new ArrayList<Reservation>();
		}
		else if (loggedUser.getTypeOfUser() == TypeOfUser.HOST) {
			return reservationDAO.searchReservationsByGuestUsername(u, reservationDAO.getReservationByHostsApartments(loggedUser.getUsername()));
		}
		
		return reservationDAO.searchReservationsByGuestUsername(u, reservationDAO.getAllReservations());
		
	}
	
	@POST
	@Path("/reservations/filter")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Reservation> filterAllReservations(ArrayList<ReservationStatus> status){
		ReservationDAO reservationDAO = getReservationDAO();
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		
		if(loggedUser == null) {
			return new ArrayList<Reservation>();
		}
		else if (loggedUser.getTypeOfUser() == TypeOfUser.HOST) {
			return reservationDAO.filterReservationsByStatus(status, reservationDAO.getReservationByHostsApartments(loggedUser.getUsername()));
		}
		
		return reservationDAO.filterReservationsByStatus(status, reservationDAO.getAllReservations());
	}
	
	@GET
	@Path("/reservations/sort_ascending")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Reservation> sortReservationsAscending(){
		ReservationDAO reservationDAO = getReservationDAO();
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		
		if(loggedUser == null) {
			return new ArrayList<Reservation>();
		}
		else if(loggedUser.getTypeOfUser() == TypeOfUser.GUEST) {
			return reservationDAO.sortReservationsAscending(reservationDAO.getReservationsByGuest(loggedUser.getUsername()));
		}
		else if (loggedUser.getTypeOfUser() == TypeOfUser.HOST) {
			return reservationDAO.sortReservationsAscending(reservationDAO.getReservationByHostsApartments(loggedUser.getUsername()));
		}
		
		return reservationDAO.sortReservationsAscending(reservationDAO.getAllReservations());	
		
	}
	
	@GET
	@Path("/reservations/sort_descending")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Reservation> sortReservationsDescending(){
		ReservationDAO reservationDAO = getReservationDAO();
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		
		if(loggedUser == null) {
			return new ArrayList<Reservation>();
		}
		else if(loggedUser.getTypeOfUser() == TypeOfUser.GUEST) {
			return reservationDAO.sortReservationsDescending(reservationDAO.getReservationsByGuest(loggedUser.getUsername()));
		}
		else if (loggedUser.getTypeOfUser() == TypeOfUser.HOST) {
			return reservationDAO.sortReservationsDescending(reservationDAO.getReservationByHostsApartments(loggedUser.getUsername()));
		}
		
		return reservationDAO.sortReservationsDescending(reservationDAO.getAllReservations());	
		
	}
	
}
