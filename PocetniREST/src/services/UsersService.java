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

import beans.Gender;
import beans.TypeOfUser;
import beans.User;
import dao.UserDAO;
import dto.LoginUserDTO;

@Path ("")
public class UsersService {

	@Context
	ServletContext ctx;
	
	@Context
	HttpServletRequest request;
	
	private UserDAO getUserDAO() {
		UserDAO userDAO = (UserDAO) ctx.getAttribute("userDAO");
		if (userDAO == null) {
			userDAO = new UserDAO();
			ctx.setAttribute("userDAO", userDAO);
		}

		return userDAO;
	}
	
	@POST
	@Path("/registration")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registration(User user) {
		UserDAO userDAO = getUserDAO();
		if (userDAO.getUser(user.getUsername()) != null) 
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Korisničko ime je zauzeto. Odaberite drugo korisničko ime").build();
		
		userDAO.addUser(user);
		return Response.ok().build();
	}
	
	@POST
	@Path("/edit_profile")
	@Consumes(MediaType.APPLICATION_JSON)
	public void edit_profile(User user) {
		UserDAO userDAO = getUserDAO();
		userDAO.editUser(user);
	}
	
	@GET
	@Path("/users/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@PathParam("username") String username) {
		UserDAO userDAO = getUserDAO();
		return userDAO.getUser(username);
	}
	
	@GET
	@Path("/hosts_guests/search/{username}/{gender}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<User> searchGuestsByLoggedHosts(@PathParam("username") String username,@PathParam("gender") String gender){
		UserDAO userDAO = getUserDAO();
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		
		String u = username;
		Gender g = Gender.MALE;
		
		if(u.equals("null")) {
			u = null;
		}
		if(gender.equals("female")){
			g = Gender.FEMALE;
		}else if(gender.equals("null")) {
			g = null;
		}
		
		if(loggedUser == null || loggedUser.getTypeOfUser() != TypeOfUser.HOST) {
			return new ArrayList<User>();
		}
		
		return userDAO.getGuestsByUsernameAndGender(u, g, userDAO.getGuestsByHost(loggedUser.getUsername()));
	}
		
	@GET
	@Path("/guests_and_hosts/search/{username}/{gender}/{usertype}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<User> searchGuestsAndHosts(@PathParam("username") String username,@PathParam("gender") String gender,
												@PathParam("usertype") String usertype){
		UserDAO userDAO = getUserDAO();
		String u = username;
		Gender g = Gender.MALE;
		
		if(u.equals("null")) {
			u = null;
		}
		if(gender.equals("female")){
			g = Gender.FEMALE;
		}else if(gender.equals("null")) {
			g = null;
		}
		
		ArrayList<User> retVal = new ArrayList<User>();
		
		if(usertype.equals("guest")) {
			retVal.addAll(userDAO.getGuestsByUsernameAndGender(u, g, userDAO.getAllGuests()));
		}
		else if(usertype.equals("host")) {
			retVal.addAll(userDAO.getHostsByUsernameAndGender(u, g, userDAO.getAllHosts()));
		}else {
			retVal.addAll(userDAO.getGuestsByUsernameAndGender(u, g, userDAO.getAllGuests()));
			retVal.addAll(userDAO.getHostsByUsernameAndGender(u, g, userDAO.getAllHosts()));
		}
		
		return retVal;
	}
	
	@GET
	@Path("/guests")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<User> getAllGuests(){
		UserDAO userDAO = getUserDAO();
		return userDAO.getAllGuests();
	}
	
	@GET
	@Path("/guests_and_hosts")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<User> getAllGuestsAndHosts(){
		UserDAO userDAO = getUserDAO();
		ArrayList<User> guests_hosts = new ArrayList<User>();
		guests_hosts.addAll(userDAO.getAllGuests());
		guests_hosts.addAll(userDAO.getAllHosts());
		return guests_hosts;
	}
	
	@GET
	@Path("/hosts_guests")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<User> getHostsGuests(){
		UserDAO userDAO = getUserDAO();
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		if (loggedUser != null && loggedUser.getTypeOfUser() == TypeOfUser.HOST) {
			return userDAO.getGuestsByHost(loggedUser.getUsername());
		}
		return new ArrayList<User>();
		
	}
	
	@GET 
	@Path("/hosts")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<User> getAllHosts(){
		UserDAO userDAO = getUserDAO();
		return userDAO.getAllHosts();
	}
	
	@POST
	@Path("/users_block")
	public Response blockUser(String username) {	
		UserDAO userDAO = getUserDAO();
		userDAO.blockUser(username);
		return Response.status(200).build();
	}
	
	@POST
	@Path("/signin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public Response signIn(LoginUserDTO userDTO) {
		String username = userDTO.getUsername();
		String password = userDTO.getPassword();
		
		UserDAO userDAO = getUserDAO();
		
		if(userDAO.getUser(username) != null) {
			if(userDAO.getUser(username).isBlocked()) {
				return Response.status(Response.Status.FORBIDDEN)
						.entity("Upozorenje! Vaš nalog je blokiran.").build();
			}
			if(!userDAO.checkPassword(username, password)) {
					return Response.status(Response.Status.BAD_REQUEST)
									.entity("Pogrešna lozinka! Pokušajte ponovo.").build();
			}else {
				User user = userDAO.getUser(username);
				request.getSession().setAttribute("loggedUser", user);
				
				if(user.getTypeOfUser() == TypeOfUser.GUEST)
					return Response.ok().entity("guest_new-reservation.html").build();
				else if(user.getTypeOfUser() == TypeOfUser.HOST)
					return Response.ok().entity("host_guests-review.html").build();	
				else 
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
	
	@GET
	@Path("/get_loggedUser")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLoggedUser() {
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		if(loggedUser == null) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		UserDAO userDAO = getUserDAO();
		User retVal = userDAO.getUser(loggedUser.getUsername());
		return Response.ok().entity(retVal).build();
	}
	
}
