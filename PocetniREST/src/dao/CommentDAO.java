package dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
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
	
	public CommentDAO() {
		File dataDir = new File(System.getProperty("catalina.base") + File.separator + "data");
		if(!dataDir.exists()) {
			dataDir.mkdir();
		}
		this.path = System.getProperty("catalina.base") + File.separator + "data" + File.separator + "comments.json";
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
			comments = mapper.readValue(Paths.get(path).toFile(), new TypeReference<ArrayList<Comment>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
		return comments;
	}
	
	private void writeInFile(ArrayList<Comment> comments) {
		ObjectMapper mapper = new ObjectMapper();
		try {
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(comments);
            BufferedWriter writer = new BufferedWriter (new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8));
		    writer.write(json);  
		    writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public ArrayList<Comment> getAllComments(){
		ArrayList<Comment> commentsFromFile = readFromFile();
		
		return commentsFromFile;
	}
	
	public Comment getComment(int id) {
		ArrayList<Comment> commentsFromFile = readFromFile();
		for(Comment c:commentsFromFile) {
			if(c.getId() == id) {
				return c;
			}
		}
		return null;
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
