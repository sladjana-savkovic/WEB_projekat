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

import beans.Comment;
import dao.CommentDAO;

@Path ("")
public class CommentService {

	@Context
	ServletContext ctx;
	
	@Context
	HttpServletRequest request;
	
	private CommentDAO getCommentDAO() {
		CommentDAO commentDAO = (CommentDAO) ctx.getAttribute("commentDAO");
		if(commentDAO == null) {
			commentDAO = new CommentDAO();
			ctx.setAttribute("commentDAO", commentDAO);
		}
		return commentDAO;
	}

	@GET
	@Path("/comments")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Comment> getCreatedComments(){
		CommentDAO commentDAO = getCommentDAO();
		return commentDAO.getAllCreatedComments();
	}
	
	@PUT
	@Path("/comments/approve")
	public void approveComment(int id) {
		CommentDAO commentDAO = getCommentDAO();
		commentDAO.approveComment(id);
	}
	
	@PUT
	@Path("/comments/disapprove")
	public void disapproveComment(int id) {
		CommentDAO commentDAO = getCommentDAO();
		commentDAO.disapproveComment(id);
	}
}
