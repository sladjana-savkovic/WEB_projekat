package services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import beans.Apartment;
import beans.TypeOfUser;
import beans.User;
import dao.ApartmentDAO;
import dto.FilterApartmentsDTO;
import dto.SearchApartments;

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
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		
		//unlogged user or guest
		if(loggedUser == null || loggedUser.getTypeOfUser() == TypeOfUser.GUEST) {
			return apartmentDAO.getActiveApartments();
		}
		//admin
		else if (loggedUser.getTypeOfUser() == TypeOfUser.ADMIN) {
			return apartmentDAO.getAllApartments();
		}
		//host
		return apartmentDAO.getApartmentsByHost(loggedUser.getUsername());
	}
	
	@GET
	@Path("/apartments/new_id")
	public int getNewId() {
		ApartmentDAO apartmentDAO = getApartmentDAO();
		return apartmentDAO.getLastId() + 1;
	}
	
	@GET
	@Path("/apartments/sort_ascending")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Apartment> sortApartmentsAscending(){
		ApartmentDAO apartmentDAO = getApartmentDAO();
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		
		//unlogged user or guest
		if(loggedUser == null || loggedUser.getTypeOfUser() == TypeOfUser.GUEST) {
			return apartmentDAO.sortApartmentsAscending(apartmentDAO.getActiveApartments());
		}
		//admin
		else if (loggedUser.getTypeOfUser() == TypeOfUser.ADMIN) {
			return apartmentDAO.sortApartmentsAscending(apartmentDAO.getAllApartments());
		}
		//host
		return apartmentDAO.sortApartmentsAscending(apartmentDAO.getApartmentsByHost(loggedUser.getUsername()));
	}
	
	@GET
	@Path("/apartments/sort_descending")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Apartment> sortApartmentsDescending(){
		ApartmentDAO apartmentDAO = getApartmentDAO();
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		//unlogged user or guest
		if(loggedUser == null || loggedUser.getTypeOfUser() == TypeOfUser.GUEST) {
			return apartmentDAO.sortApartmentsDescending(apartmentDAO.getActiveApartments());
		}
		//admin
		else if (loggedUser.getTypeOfUser() == TypeOfUser.ADMIN) {
			return apartmentDAO.sortApartmentsDescending(apartmentDAO.getAllApartments());
		}
		return apartmentDAO.sortApartmentsDescending(apartmentDAO.getApartmentsByHost(loggedUser.getUsername()));
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
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		
		if(loggedUser == null) {
			return new ArrayList<Apartment>();
		}
		
		if (loggedUser.getTypeOfUser() == TypeOfUser.ADMIN) {
			return apartmentDAO.filterApartmentsByTypeAmenitiesAndStatus(filter.getTypes(), filter.getAmenities(), 
					filter.getStatus(),apartmentDAO.getAllApartments());
		}
		return apartmentDAO.filterApartmentsByTypeAmenitiesAndStatus(filter.getTypes(), filter.getAmenities(), 
				filter.getStatus(),apartmentDAO.getApartmentsByHost(loggedUser.getUsername()));
	}
	
	@POST
	@Path("/apartments/add")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addApartment(Apartment apartment) {
		ApartmentDAO apartmentDAO = getApartmentDAO();
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		
		if(loggedUser == null) {
			return;
		}
		
		if (loggedUser.getTypeOfUser() == TypeOfUser.HOST) {
			apartment.setHostUsername(loggedUser.getUsername());
		}
		apartmentDAO.addApartment(apartment);
	}
	
	@POST
	@Path("/apartments/search")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Apartment> searchApartments(SearchApartments searchApartments){
		ApartmentDAO apartmentDAO = getApartmentDAO();
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		
		//unlogged user or guest
		if(loggedUser == null || loggedUser.getTypeOfUser() == TypeOfUser.GUEST) {
			return apartmentDAO.searchApartments(searchApartments.getCity(), searchApartments.getStartDate(), 
					searchApartments.getEndDate(), searchApartments.getMinPrice(), searchApartments.getMaxPrice(), 
					searchApartments.getMinRooms(), searchApartments.getMaxRooms(), searchApartments.getPersons(),
					apartmentDAO.getActiveApartments());
		}
		//admin
		else if (loggedUser.getTypeOfUser() == TypeOfUser.ADMIN) {
			return apartmentDAO.searchApartments(searchApartments.getCity(), searchApartments.getStartDate(), 
					searchApartments.getEndDate(), searchApartments.getMinPrice(), searchApartments.getMaxPrice(), 
					searchApartments.getMinRooms(), searchApartments.getMaxRooms(), searchApartments.getPersons(),
					apartmentDAO.getAllApartments());
		}
		//host
		return apartmentDAO.searchApartments(searchApartments.getCity(), searchApartments.getStartDate(), 
				searchApartments.getEndDate(), searchApartments.getMinPrice(), searchApartments.getMaxPrice(), 
				searchApartments.getMinRooms(), searchApartments.getMaxRooms(), searchApartments.getPersons(),
				apartmentDAO.getApartmentsByHost(loggedUser.getUsername()));
	}
	
	@POST
	@Path("/apartments/edit")
	@Consumes(MediaType.APPLICATION_JSON)
	public void editApartment(Apartment apartment) {
		ApartmentDAO apartmentDAO = getApartmentDAO();
		
		ArrayList<Apartment> apartments = apartmentDAO.getAllApartments();
		
		ArrayList<LocalDate> oldDates = new ArrayList<LocalDate>();
		for(Apartment a:apartments) {
			if(a.getId() == apartment.getId()) {
					for(String d : a.getRentingDates()) {
							oldDates.add(LocalDate.parse(d));
					}
			}
		}		
		ArrayList<LocalDate> newDates = new ArrayList<LocalDate>();
		for(String d : apartment.getRentingDates()) {
			newDates.add(LocalDate.parse(d));
		}
		
		apartmentDAO.editApartment(apartment);
		apartmentDAO.changeAvailableAndRentingDates(apartment.getId(), newDates, oldDates);
		apartmentDAO.sortAvailableDates(apartment.getId());
	}
	
	@POST
	@Path("/apartments/{id}/image")
	public Response uploadImage(InputStream in, @HeaderParam("Content-Type") String fileType,
			@HeaderParam("Content-Length") long fileSize, @PathParam("id") int apartmentId) throws IOException {
		String fileName = UUID.randomUUID().toString();
		if (fileType.equals("image/jpeg")) {
			fileName += ".jpg";
		} else {
			fileName += ".png";
		}
		
		java.nio.file.Path BASE_DIR = Paths.get(System.getProperty("catalina.base") + File.separator + "images");
		File directory = new File(System.getProperty("catalina.base") + File.separator + "images");
	    if (! directory.exists()){
	        directory.mkdir();
	    }
		Files.copy(in, BASE_DIR.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
		
		ApartmentDAO apartmentDAO = getApartmentDAO();
		apartmentDAO.addImageToApartment(apartmentId, fileName);
		return Response.status(Response.Status.OK).entity(in).build();
	}
	
	@GET
	@Path("/apartments/all_images/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<String> getAllImagesForApartment(@PathParam("id") int apartmentId){
		ApartmentDAO apartmentDAO = getApartmentDAO();
		return apartmentDAO.getApartment(apartmentId).getPictures();
	}
	
	@GET
	@Path("/apartments/first_image/{id}")
	@Produces({ "image/jpeg" })
	public FileInputStream getFirstImage(@PathParam("id") int apartmentId) throws JsonParseException, JsonMappingException, IOException {
		ApartmentDAO apartmentDAO = getApartmentDAO();
		
		for(String image:apartmentDAO.getApartment(apartmentId).getPictures()) {
			FileInputStream fileInputStream = new FileInputStream(
					new File(System.getProperty("catalina.base") + File.separator + "images" + File.separator + image));
			return fileInputStream;
		}
		return null;
	}
	
	@DELETE
	@Path("/apartments/delete_images")
	public void deleteApartmentImages(int apartmentId) {
		ApartmentDAO apartmentDAO = getApartmentDAO();
		apartmentDAO.deleteApartmentImages(apartmentId);
	}
	
	@GET
	@Path("/apartments/one_image/{image}")
	@Produces({ "image/jpeg" })
	public FileInputStream getOneImage(@PathParam("image") String image) 
			throws JsonParseException, JsonMappingException, IOException {
		
		FileInputStream fileInputStream = new FileInputStream(
				new File(System.getProperty("catalina.base") + File.separator + "images" + File.separator + image));
		
		return fileInputStream;
	}

	@PUT
	@Path("/apartments/add_comment/{apartment_id}/{comment_id}")
	public void appCommentToApartment(@PathParam("apartment_id") int apartment_id,
										@PathParam("comment_id") int comment_id) {
		
		ApartmentDAO apartmentDAO = getApartmentDAO();
		apartmentDAO.addCommentToApartment(apartment_id, comment_id);
	}
	
}
