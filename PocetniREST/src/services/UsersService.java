package services;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.PathParam;

import beans.Guest;
import dao.GuestDAO;

@Path ("")
public class UsersService {

	@Context
	ServletContext ctx;
	
	@Context
	HttpServletRequest request;
	
	private GuestDAO getGuestDAO() {
		GuestDAO guestDAO = (GuestDAO) ctx.getAttribute("guestDAO");
		if (guestDAO == null) {
			guestDAO = new GuestDAO();
			ctx.setAttribute("guests", guestDAO);
		}

		return guestDAO;
	}
	
	@POST
	@Path("/registration")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registration(Guest guest) {
		GuestDAO guestDAO = getGuestDAO();
		
		if (guestDAO.getGuest(guest.getUsername()) != null) 
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Korisničko ime je zauzeto. Odaberite drugo korisničko ime").build();
		
		guestDAO.addGuest(guest);
		return Response.ok().build();
	}
	
	@POST
	@Path("/edit_profile")
	@Consumes(MediaType.APPLICATION_JSON)
	public void edit_profile(Guest guest) {
		GuestDAO guestDAO = getGuestDAO();
		guestDAO.editGuest(guest);
	}
	
	@GET
	@Path("/guests/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Guest getGuest(@PathParam("username") String username) {
		GuestDAO guestDAO = getGuestDAO();
		return guestDAO.getGuest(username);
	}
	
	
}
