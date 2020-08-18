package dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Comment;
import beans.CommentStatus;
import beans.RatingOfApartment;
import beans.Reservation;
import beans.ReservationStatus;

public class CommentDAO {
	private String path;
	private File file;
	
	/*
	public static void main(String[] args) {
	
		Comment c1 = new Comment(0, "ana967", 0, "Zadovoljna sam!", RatingOfApartment.EXCELLENT, CommentStatus.CREATED);
		Comment c2 = new Comment(1, "tara88", 1, "Prosječan apartman", RatingOfApartment.GOOD, CommentStatus.CREATED);
		
		ArrayList<Comment> comments = new ArrayList<>();
		comments.add(c1);
		comments.add(c2);
		
		//CommentDAO cDAO = new CommentDAO();
		//cDAO.writeInFile(comments);
		//cDAO.addCommentForApartment(c1);
	}*/
	
	public CommentDAO() {
		path = "data/comments.json";
		file = new File(path);
	}
	
	private ArrayList<Comment> readFromFile() {
		ArrayList<Comment> comments = new ArrayList<Comment>();
		ObjectMapper mapper = new ObjectMapper();	
		try {
			comments = mapper.readValue(file, new TypeReference<ArrayList<Comment>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
		return comments;
	}
	
	private void writeInFile(ArrayList<Comment> comments) {
		ObjectMapper mapper = new ObjectMapper();
		try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, comments);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public ArrayList<Comment> getAllComments(){
		CommentDAO commentDAO = new CommentDAO();
		ArrayList<Comment> commentsFromFile = commentDAO.readFromFile();
		
		return commentsFromFile;
	}
	
	public ArrayList<Comment> getAllCommentsByApartment(int idApartment){
		CommentDAO commentDAO = new CommentDAO();
		ArrayList<Comment> commentsFromFile = commentDAO.readFromFile();
		ArrayList<Comment> comments = new ArrayList<>();
		
		for(Comment c : commentsFromFile) {
			if(c.getApartmentId() == idApartment) {
				comments.add(c);
			}
		}
		return comments;
	}
	
	public ArrayList<Comment> getApprovedCommentsByApartment(int idApartment){
		CommentDAO commentDAO = new CommentDAO();
		ArrayList<Comment> commentsFromFile = commentDAO.readFromFile();
		ArrayList<Comment> comments = new ArrayList<>();
		
		for(Comment c : commentsFromFile) {
			if(c.getStatus().equals(CommentStatus.APPROVED) && c.getApartmentId() == idApartment) {
				comments.add(c);
			}
		}
		return comments;
	}
	
	public int getLastId() {
		CommentDAO commentDAO = new CommentDAO();
		ArrayList<Comment> commentsFromFile = commentDAO.readFromFile();
		if(commentsFromFile.size() == 0) {
			return 0;
		}
		
		return commentsFromFile.get(commentsFromFile.size()-1).getId();
	}
	
	public ArrayList<Comment> getCreatedCommentsByApartment(int idApartment){
		CommentDAO commentDAO = new CommentDAO();
		ArrayList<Comment> commentsFromFile = commentDAO.readFromFile();
		ArrayList<Comment> comments = new ArrayList<>();
		
		for(Comment c : commentsFromFile) {
			if(c.getStatus().equals(CommentStatus.CREATED) && c.getApartmentId() == idApartment) {
				comments.add(c);
			}
		}
		return comments;
	}
	
	public void approveComment(Comment comment) {
		CommentDAO commentDAO = new CommentDAO();
		ArrayList<Comment> commentsFromFile = commentDAO.readFromFile();
		
		for(Comment c : commentsFromFile) {
			if(c.getId() == comment.getId()) {
				c.setStatus(CommentStatus.APPROVED);
			}
		}
		commentDAO.writeInFile(commentsFromFile);
	}
	
	public void disapproveComment(Comment comment) {
		CommentDAO commentDAO = new CommentDAO();
		ArrayList<Comment> commentsFromFile = commentDAO.readFromFile();
		
		for(Comment c : commentsFromFile) {
			if(c.getId() == comment.getId()) {
				c.setStatus(CommentStatus.DISAPPROVED);
			}
		}
		commentDAO.writeInFile(commentsFromFile);
	}
	
	
	public void addCommentForApartment(Comment comment) {
		CommentDAO commentDAO = new CommentDAO();
		ArrayList<Comment> commentsFromFile = commentDAO.readFromFile();
		
		ReservationDAO reservationDAO = new ReservationDAO();
		ArrayList<Reservation> reservations = reservationDAO.getReservationsByGuest(comment.getGuestUsername());
		
		for(Reservation r : reservations) {
			if(r.getApartmentId() == comment.getApartmentId() && (r.getStatus().equals(ReservationStatus.REFUSED) || r.getStatus().equals(ReservationStatus.FINISHED))) {
				commentsFromFile.add(comment);
				break;
			}
		}
		commentDAO.writeInFile(commentsFromFile);
		
	}
	
}
