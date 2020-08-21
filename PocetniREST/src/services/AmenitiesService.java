package services;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Amenities;
import dao.AmenitiesDAO;

@Path ("")
public class AmenitiesService {
	
	@Context
	ServletContext ctx;
	
	@Context
	HttpServletRequest request;

	private AmenitiesDAO getAmenitiesDAO() {
		AmenitiesDAO amenitiesDAO = (AmenitiesDAO) ctx.getAttribute("amenitiesDAO");
		if(amenitiesDAO == null) {
			amenitiesDAO = new AmenitiesDAO();
			ctx.setAttribute("amenitiesDAO",amenitiesDAO);
		}
		return amenitiesDAO;
	}
	
	@GET
	@Path("/amenities")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Amenities> getAllAmenities(){
		AmenitiesDAO amenitiesDAO = getAmenitiesDAO();
		return amenitiesDAO.getAllAmenities();
	}
	
	@POST
	@Path("/amenities/add")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addAmenities(Amenities amenities) {
		AmenitiesDAO amenitiesDAO = getAmenitiesDAO();
		if(amenitiesDAO.getAmenitiesById(amenities.getId()) != null)
			return Response.status(Response.Status.BAD_REQUEST).build();
		
		amenitiesDAO.addNewAmenities(amenities);
		return Response.ok().build();
	}
	
	@PUT
	@Path("/amenities/edit")
	@Consumes(MediaType.APPLICATION_JSON)
	public void editAmenities(Amenities amenities) {
		AmenitiesDAO amenitiesDAO = getAmenitiesDAO();
		amenitiesDAO.editNameOfAmenities(amenities);
	}
}
