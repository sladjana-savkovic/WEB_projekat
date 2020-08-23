package services;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.PathParam;

import beans.Admin;
import beans.Gender;
import beans.Guest;
import beans.Host;
import dao.AdminDAO;
import dao.GuestDAO;
import dao.HostDAO;
import dto.LoginUserDTO;

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
			ctx.setAttribute("guestDAO", guestDAO);
		}

		return guestDAO;
	}
	private HostDAO getHostDAO() {
		HostDAO hostDAO = (HostDAO) ctx.getAttribute("hostDAO");
		if(hostDAO == null) {
			hostDAO = new HostDAO();
			ctx.setAttribute("hostDAO", hostDAO);
		}
		return hostDAO;
	}
	
	private AdminDAO getAdminDAO() {
		AdminDAO adminDAO = (AdminDAO) ctx.getAttribute("adminDAO");
		if(adminDAO == null) {
			adminDAO = new AdminDAO();
			ctx.setAttribute("adminDAO", adminDAO);
		}
		return adminDAO;
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
	@Path("/host_add")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addHost(Host host) {
		HostDAO hostDAO = getHostDAO();
		if(hostDAO.getHost(host.getUsername()) != null)
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Korisničko ime je zauzeto. Odaberite drugo korisničko ime").build();
		hostDAO.addHost(host);
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
	
	@GET
	@Path("/guests/search/{username}/{gender}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Guest> searchGuests(@PathParam("username") String username,@PathParam("gender") String gender){
		GuestDAO guestDAO = getGuestDAO();
		Gender g = Gender.MALE;
		if(gender.equals("female")) {
			g = Gender.FEMALE;
		}
		return guestDAO.getGuestsByUsernameAndGender(username, g);
	}
	
	@GET
	@Path("/hosts_guests/search/{username}/{gender}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Guest> searchHostsGuests(@PathParam("username") String username,@PathParam("gender") String gender){
		GuestDAO guestDAO = getGuestDAO();
		Gender g = Gender.MALE;
		if(gender.equals("female")) {
			g = Gender.FEMALE;
		}
		//umjesto gaga998 ide username ulogovang gosta
		return guestDAO.getHostsGuestsByUsernameAndGender("gaga998",username, g);
	}
	
	@GET
	@Path("/hosts/search/{username}/{gender}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Host> searchHosts(@PathParam("username") String username,@PathParam("gender") String gender){
		HostDAO hostDAO = getHostDAO();
		Gender g = Gender.MALE;
		if(gender.equals("female")){
			g = Gender.FEMALE;
		}
		return hostDAO.getHostsByUsernameAndGender(username, g);
	}
	
	@GET
	@Path("/guests")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Guest> getAllGuests(){
		GuestDAO guestDAO = getGuestDAO();
		return guestDAO.getAllGuests();
	}
	
	@GET
	@Path("/hosts_guests")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Guest> getHostsGuests(){
		GuestDAO guestDAO = getGuestDAO();
		//username ulogovanog gosta
		return guestDAO.getGuestsByHost("gaga998");
	}
	
	@GET 
	@Path("/hosts")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Host> getAllHosts(){
		HostDAO hostDAO = getHostDAO();
		return hostDAO.getAllHosts();
	}
	
	@POST
	@Path("/users_block")
	public Response blockUser(String username) {	
		GuestDAO guestDAO = getGuestDAO();
		HostDAO hostDAO = getHostDAO();
		if(guestDAO.getGuest(username) != null) {
			guestDAO.blockGuest(username);
		}else {
			hostDAO.blockHost(username);
		}
		return Response.status(200).build();
	}
	
	@POST
	@Path("/signin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public Response signIn(LoginUserDTO user) {
		String username = user.getUsername();
		String password = user.getPassword();
		
		GuestDAO guestDAO = getGuestDAO();
		HostDAO hostDAO = getHostDAO();
		AdminDAO adminDAO = getAdminDAO();
		
		if(guestDAO.getGuest(username) != null) {
			if(!guestDAO.checkPassword(username, password)) {
					return Response.status(Response.Status.BAD_REQUEST)
									.entity("Pogrešna lozinka! Pokušajte ponovo.").build();
			}else {
				Guest guest = guestDAO.getGuest(username);
				request.getSession().setAttribute("loggedUser", guest);
				return Response.ok().entity("guest_new-reservation.html").build();
			}
		}
		
		else if(hostDAO.getHost(username) != null) {
			if(!hostDAO.checkPassword(username, password)) {
				return Response.status(Response.Status.BAD_REQUEST)
								.entity("Pogrešna lozinka! Pokušajte ponovo.").build();
			}else {
				Host host = hostDAO.getHost(username);
				request.getSession().setAttribute("loggedUser", host);
				return Response.ok().entity("host_guests-review.html").build();	
			}
		}
		
		else if(adminDAO.getAdmin(username) != null) {
			if(!adminDAO.checkPassword(username, password)) {
				return Response.status(Response.Status.BAD_REQUEST)
								.entity("Pogrešna lozinka! Pokušajte ponovo.").build();
			}else {
				Admin admin = adminDAO.getAdmin(username);
				request.getSession().setAttribute("loggedUser", admin);
				return Response.ok().entity("admin_apartments.html").build();	
			}
		}
		
		return Response.status(Response.Status.BAD_REQUEST)
				.entity("Korisničko ime ne postoji. Pokušajte ponovo.").build();
	}
	
	@GET
	@Path("/signout")
	@Produces(MediaType.TEXT_HTML)
	public Response signOut() {
		HttpSession session = request.getSession();
		if(session != null && session.getAttribute("loggedUser") != null) {
			session.invalidate();
		}
		return Response.ok().build();
	}

}
