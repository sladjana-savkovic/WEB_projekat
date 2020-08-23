package services;

import java.util.ArrayList;

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

import beans.Admin;
import beans.Guest;
import beans.Host;
import beans.User;

import javax.ws.rs.PathParam;

@Path ("")
public class UserVerification {

	@Context
	ServletContext ctx;
	
	@Context
	HttpServletRequest request;
	
	@GET
	@Path("/verification/is_logged")
	@Produces(MediaType.TEXT_HTML)
	public Response isLoggedIn() {
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		if (loggedUser == null) {
			return Response.status(Response.Status.FORBIDDEN)
					.entity("Prijavite se da biste pristupili ovoj stranici.").build();
		}
		return Response.ok().build();
	}
	
	@GET
	@Path("/verification/admin")
	@Produces(MediaType.TEXT_HTML)
	public Response isAdmin() {
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		if (loggedUser == null) {
			return Response.status(Response.Status.FORBIDDEN)
					.entity("Prijavite se da biste pristupili ovoj stranici.").build();
		} else if (loggedUser instanceof Admin) {
			return Response.ok().build();
		} else {
			return Response.status(Response.Status.FORBIDDEN)
					.entity("Prijavite se kao administrator da biste pristupili ovoj stranici.").build();
		}
	}
	
	@GET
	@Path("/verification/host")
	@Produces(MediaType.TEXT_HTML)
	public Response isHost() {
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		if (loggedUser == null) {
			return Response.status(Response.Status.FORBIDDEN)
					.entity("Prijavite se da biste pristupili ovoj stranici.").build();
		} else if (loggedUser instanceof Host) {
			return Response.ok().build();
		} else {
			return Response.status(Response.Status.FORBIDDEN)
					.entity("Prijavite se kao domaćin da biste pristupili ovoj stranici.").build();
		}
	}
	
	@GET
	@Path("/verification/guest")
	@Produces(MediaType.TEXT_HTML)
	public Response isGuest() {
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		if (loggedUser == null) {
			return Response.status(Response.Status.FORBIDDEN)
					.entity("Prijavite se da biste pristupili ovoj stranici.").build();
		} else if (loggedUser instanceof Guest) {
			return Response.ok().build();
		} else {
			return Response.status(Response.Status.FORBIDDEN)
					.entity("Prijavite se kao gost da biste pristupili ovoj stranici.").build();
		}
	}
}
