package services;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Apartment;
import dao.ApartmentDAO;
import dto.FilterApartmentsDTO;

import javax.ws.rs.PathParam;

@Path ("")
public class ApartmentService {

	@Context
	ServletContext ctx;
	
	@Context
	HttpServletRequest request;
	
	private ApartmentDAO getApartmentDAO() {
		ApartmentDAO apartmentDAO = (ApartmentDAO) ctx.getAttribute("apartmentDAO");
		if(apartmentDAO == null) {
			apartmentDAO = new ApartmentDAO();
			ctx.setAttribute("apartmentDAO", apartmentDAO);
		}
		return apartmentDAO;
	}
	
	@GET
	@Path("/apartments/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Apartment getApartment(@PathParam("id") int id) {
		ApartmentDAO apartmentDAO = getApartmentDAO();
		return apartmentDAO.getApartment(id);
	}
	
	@GET
	@Path("/apartments")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Apartment> getAllApartments(){
		ApartmentDAO apartmentDAO = getApartmentDAO();
		return apartmentDAO.getUndeletedApartments();
	}
	
	@GET
	@Path("/apartments/sort_ascending")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Apartment> sortApartmentsAscending(){
		ApartmentDAO apartmentDAO = getApartmentDAO();
		return apartmentDAO.sortApartmentsAscending();
	}
	
	@GET
	@Path("/apartments/sort_descending")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Apartment> sortApartmentsDescending(){
		ApartmentDAO apartmentDAO = getApartmentDAO();
		return apartmentDAO.sortApartmentsDescending();
	}
	
	@DELETE
	@Path("/apartments/delete")
	public void deleteApartment(int id) {
		ApartmentDAO apartmentDAO = getApartmentDAO();
		apartmentDAO.deleteApartment(id);
	}
	
	@POST
	@Path("/apartments/filter")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Apartment> filterApartments(FilterApartmentsDTO filter){
		ApartmentDAO apartmentDAO = getApartmentDAO();
		/*System.out.println("------------------------------------------------");
		System.out.println(filter.getTypes());
		System.out.println(filter.getAmenities());
		System.out.println(filter.getStatus());*/
		return apartmentDAO.filterApartmentsByTypeAmenitiesAndStatus(filter.getTypes(), filter.getAmenities(), filter.getStatus());
	}
}
