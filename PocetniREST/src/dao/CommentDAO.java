package dao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Comment;
import beans.CommentStatus;
import beans.Reservation;
import beans.ReservationStatus;

public class CommentDAO {
	private File file;
	private String path;
	
	/*public static void main(String[] args) {
	
		Comment c1 = new Comment(0, "ana967", 0, "Zadovoljna sam!", 5, CommentStatus.CREATED);
		Comment c2 = new Comment(1, "tara88", 1, "Prosječan apartman", 3, CommentStatus.CREATED);
		Comment c3 = new Comment(2, "milica967", 1, "Jako loše!!!", 1, CommentStatus.CREATED);
		
		ArrayList<Comment> comments = new ArrayList<>();
		comments.add(c1);
		comments.add(c2);
		
		CommentDAO cDAO = new CommentDAO();
		//cDAO.addCommentForApartment(c3);
		//cDAO.writeInFile(comments);
		//cDAO.addCommentForApartment(c1);
	}*/
	
	public CommentDAO() {
		File dataDir = new File(System.getProperty("catalina.base") + File.separator + "data");
		if(!dataDir.exists()) {
			dataDir.mkdir();
		}
		this.path = System.getProperty("catalina.base") + File.separator + "data" + File.separator + "comments.json";
		//path = Paths.get("WEB projekat\\PocetniREST\\WebContent\\data").toAbsolutePath().toString() + File.separator + "comments.json";
		file = new File(path);
		 try {
		   if (file.createNewFile()){
		    ArrayList<Comment> comments = new ArrayList<Comment>();
		    writeInFile(comments);
		   }
		  } catch (IOException e) {
		   e.printStackTrace();
		  }
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
		ArrayList<Comment> commentsFromFile = readFromFile();
		
		return commentsFromFile;
	}
	
	public ArrayList<Comment> getAllCommentsByApartment(int idApartment){
		ArrayList<Comment> commentsFromFile = readFromFile();
		ArrayList<Comment> comments = new ArrayList<>();
		
		for(Comment c : commentsFromFile) {
			if(c.getApartmentId() == idApartment) {
				comments.add(c);
			}
		}
		return comments;
	}
	
	public ArrayList<Comment> getApprovedCommentsByApartment(int idApartment){
		ArrayList<Comment> commentsFromFile = readFromFile();
		ArrayList<Comment> comments = new ArrayList<>();
		
		for(Comment c : commentsFromFile) {
			if(c.getStatus().equals(CommentStatus.APPROVED) && c.getApartmentId() == idApartment) {
				comments.add(c);
			}
		}
		return comments;
	}
	
	public int getLastId() {
		ArrayList<Comment> commentsFromFile = readFromFile();
		if(commentsFromFile.size() == 0) {
			return 0;
		}
		
		return commentsFromFile.get(commentsFromFile.size()-1).getId();
	}
	
	public ArrayList<Comment> getCreatedCommentsByApartment(int idApartment){
		ArrayList<Comment> commentsFromFile = readFromFile();
		ArrayList<Comment> comments = new ArrayList<>();
		
		for(Comment c : commentsFromFile) {
			if(c.getStatus().equals(CommentStatus.CREATED) && c.getApartmentId() == idApartment) {
				comments.add(c);
			}
		}
		return comments;
	}
	
	public void approveComment(int commentId) {
		ArrayList<Comment> commentsFromFile = readFromFile();
		
		for(Comment c : commentsFromFile) {
			if(c.getId() == commentId) {
				c.setStatus(CommentStatus.APPROVED);
				break;
			}
		}
		writeInFile(commentsFromFile);
	}
	
	public void disapproveComment(int commentId) {
		ArrayList<Comment> commentsFromFile = readFromFile();
		
		for(Comment c : commentsFromFile) {
			if(c.getId() == commentId) {
				c.setStatus(CommentStatus.DISAPPROVED);
				break;
			}
		}
		writeInFile(commentsFromFile);
	}
	
	
	public void addCommentForApartment(Comment comment) {
		ArrayList<Comment> commentsFromFile = readFromFile();
		
		ReservationDAO reservationDAO = new ReservationDAO();
		ArrayList<Reservation> reservations = reservationDAO.getReservationsByGuest(comment.getGuestUsername());
		
		for(Reservation r : reservations) {
			if(r.getApartmentId() == comment.getApartmentId() && (r.getStatus().equals(ReservationStatus.REFUSED) || r.getStatus().equals(ReservationStatus.FINISHED))) {
				commentsFromFile.add(comment);
				break;
			}
		}
		writeInFile(commentsFromFile);
	}
	
	public ArrayList<Comment> getAllCreatedComments(){
		ArrayList<Comment> commentsFromFile = readFromFile();
		ArrayList<Comment> createdComments = new ArrayList<Comment>();
		for(Comment c:commentsFromFile) {
			if(c.getStatus() == CommentStatus.CREATED)
				createdComments.add(c);
		}
		return createdComments;
	}
	
}
