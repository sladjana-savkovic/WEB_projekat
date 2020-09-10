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
	
	@GET
	@Path("/hosts_reservations/search/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Reservation> searchReservations(@PathParam("username") String username){
		ReservationDAO reservationDAO = getReservationDAO();
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
		ReservationDAO reservationDAO =  getReservationDAO();
		return reservationDAO.getReservationsByGuest("pero123");
	}
	
	@GET
	@Path("/guests_reservations/sort_ascending")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Reservation> sortGuestReservationsAscending(){
		ReservationDAO reservationDAO = getReservationDAO();
		//treba ime ulogovanog gosta
		return reservationDAO.sortReservationsAscending(reservationDAO.getReservationsByGuest("pero123"));
	}
	
	@GET
	@Path("/guests_reservations/sort_descending")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Reservation> sortGuestReservationsDescending(){
		ReservationDAO reservationDAO = getReservationDAO();
		//treba ime ulogovanog gosta
		return reservationDAO.sortReservationsDescending(reservationDAO.getReservationsByGuest("pero123"));
	}
	
	@GET
	@Path("/hosts_reservations")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Reservation> getReservationsByHost(){
		//treba ime ulogovanog domacina
		ReservationDAO reservationDAO = getReservationDAO();
		return reservationDAO.getReservationByHostsApartments("gaga998");
	}
	
	@POST
	@Path("/hosts_reservations/filter")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Reservation> filterReservations(ArrayList<ReservationStatus> status){
		ReservationDAO reservationDAO = getReservationDAO();
		//treba ime ulogovanog domacina
		return reservationDAO.filterReservationsByStatus(status, reservationDAO.getReservationByHostsApartments("gaga998"));
	}
	
	

	@GET
	@Path("/hosts_reservations/sort_ascending")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Reservation> sortHostReservationsAscending(){
		ReservationDAO reservationDAO = getReservationDAO();
		//treba ime ulogovanog domacina
		return reservationDAO.sortReservationsAscending(reservationDAO.getReservationByHostsApartments("gaga998"));
	}
	
	@GET
	@Path("/hosts_reservations/sort_descending")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Reservation> sortHostReservationsDescending(){
		ReservationDAO reservationDAO = getReservationDAO();;
		//treba ime ulogovanog domacina
		return reservationDAO.sortReservationsDescending(reservationDAO.getReservationByHostsApartments("gaga998"));
	}
	
	@POST
	@Path("/accept_reservation")
	@Consumes(MediaType.APPLICATION_JSON)
	public void acceptReservation(int id) {
		ReservationDAO reservationDAO = getReservationDAO();
		reservationDAO.acceptReservationByHost(reservationDAO.getReservationById(id));
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
		ReservationDAO reservationDAO = getReservationDAO();;
		reservationDAO.refuseReservationByHost(reservationDAO.getReservationById(id));
	}
	
	@POST
	@Path("/cancel_reservation")
	@Consumes(MediaType.APPLICATION_JSON)
	public void cancelReservation(int id) {
		ReservationDAO reservationDAO = getReservationDAO();
		reservationDAO.cancelReservationByGuest(reservationDAO.getReservationById(id));
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
		apartmentDAO.reduceAvailableDates(reservation.getApartmentId(), LocalDate.parse(reservation.getStartDate()), reservation.getNumberOfNights());
		reservationDAO.addNewReservation(reservation);
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
	
}
