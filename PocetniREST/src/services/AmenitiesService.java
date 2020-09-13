package services;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Amenities;
import dao.AmenitiesDAO;
import dao.ApartmentDAO;

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
	
	private ApartmentDAO getApartmentDAO() {
		ApartmentDAO apartmentDAO = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		if(apartmentDAO == null) {
			apartmentDAO = new ApartmentDAO();
			ctx.setAttribute("apartmentDAO", apartmentDAO);
		}
		return apartmentDAO;
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
	public void addAmenities(Amenities amenities) {
		AmenitiesDAO amenitiesDAO = getAmenitiesDAO();	
		amenitiesDAO.addNewAmenities(amenities);
	}
	
	@PUT
	@Path("/amenities/edit")
	@Consumes(MediaType.APPLICATION_JSON)
	public void editAmenities(Amenities amenities) {
		AmenitiesDAO amenitiesDAO = getAmenitiesDAO();
		amenitiesDAO.editNameOfAmenities(amenities);
	}
	
	@GET
	@Path("/amenities/new_id")
	public int getNewId() {
		AmenitiesDAO amenitiesDAO = getAmenitiesDAO();
		return amenitiesDAO.getLastId()+1;
	}
	
	@DELETE
	@Path("/amenities/delete")
	public void deleteAmenities(int id) {
		AmenitiesDAO amenitiesDAO = getAmenitiesDAO();
		ApartmentDAO apartmentDAO = getApartmentDAO();
		apartmentDAO.deleteAmenitiesFromAllApartments(id);
		amenitiesDAO.deleteAmenities(id);
	}
	
	@GET
	@Path("/amenities/{id}")
	public String getNameOfAmenities(@PathParam("id") int id) {
		AmenitiesDAO amenitiesDAO = getAmenitiesDAO();
		return amenitiesDAO.getAmenitiesById(id).getName();
	}
	
	@GET
	@Path("/apartments_amenities/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Amenities> getApartmentsAmenities(@PathParam("id") int id){
		AmenitiesDAO amenitiesDAO = getAmenitiesDAO();
		return amenitiesDAO.getApartmentAmenities(id);
	}
	
}
