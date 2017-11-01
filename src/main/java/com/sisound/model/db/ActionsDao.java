package com.sisound.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sisound.model.Actionable;
import com.sisound.model.Actions;
import com.sisound.model.User;


@Component
public class ActionsDao {

	@Autowired
	public UserDao userDao;
	
	private String deleteQuery = "DELETE FROM ? WHERE ? = ?";
	
	public synchronized void likeSong(long song_id, long user_id) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("INSERT INTO songs_likes (user_id, song_id) VALUES (?, ?)");
		stmt.setLong(1, user_id);
		stmt.setLong(2, song_id);
		stmt.execute();
	}
	
	public synchronized void dislikeSong(long song_id, long user_id) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("INSERT INTO songs_dislikes (user_id, song_id) VALUES (?, ?)");
		stmt.setLong(1, user_id);
		stmt.setLong(2, song_id);
		stmt.execute();
	}
//	public synchronized void addAction(Actionable a, Actions action, User user) throws SQLException {
//		
//		Connection con = DBManager.getInstance().getConnection();
//		PreparedStatement stmt = con.prepareStatement("INSERT INTO ? (user_id, ?) VALUES (?, ?)");
//		switch (action) {
//		case LIKE:
//			if(a.isSong()) {
//				stmt.setString(1, "songs_likes");
//				stmt.setString(2, "song_id");
//				
//			}
//			if(a.isComment()){
//				stmt.setString(1, "comments_likes");
//				stmt.setString(2, "comment_id");
//			}
//			else {
//				stmt.setString(1, "playlists_likes");
//				stmt.setString(2, "playlist_id");
//			}
//						
//			break;
//		case DISLIKE:
//			if(a.isSong()) {
//				stmt.setString(1, "songs_dislikes");
//				stmt.setString(2, "song_id");
//				
//			} else {
//				stmt.setString(1, "playlists_dislikes");
//				stmt.setString(2, "playlist_id");
//			}
//			
//			break;
//		case SHARE:
//			if(a.isSong()) {
//				stmt.setString(1, "songs_shares");
//				stmt.setString(2, "song_id");
//				
//			} else {
//				stmt.setString(1, "playlists_shares");
//				stmt.setString(2, "playlist_id");
//			}
//			
//			break;
//		}
//		
//		stmt.setLong(3, user.getUserID());
//		stmt.setLong(4, a.getId());
//		
//		stmt.execute();
//	}
	
	//OK
	public synchronized HashMap<Actions, HashSet<User>> getActions(boolean isSong, long id) throws SQLException {
		
		//creating the result map
		HashMap<Actions, HashSet<User>> actions;
		actions = new HashMap<>();
		for (Actions act : Actions.values()) {
			actions.put(act, new HashSet());
		}
		
		Connection con = DBManager.getInstance().getConnection();

		PreparedStatement stmt = null;
		ResultSet rs=null;
		if(isSong){
			//getting song likes
			stmt=con.prepareStatement("SELECT u.user_name FROM songs_likes as sl JOIN users as u ON sl.user_id=u.user_id WHERE sl.song_id=?");
			stmt.setLong(1, id);
			
		    rs = stmt.executeQuery();
			while (rs.next()) {
				actions.get(Actions.LIKE).add(userDao.getUser(rs.getString(1)));
			}
			
			//getting song dislikes
			stmt = con.prepareStatement("SELECT u.user_name FROM songs_dislikes as sd JOIN users as u ON sd.user_id=u.user_id WHERE sd.song_id=?");
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				actions.get(Actions.DISLIKE).add(userDao.getUser(rs.getString(1)));
			}
			
			//getting song shares
			stmt = con.prepareStatement("SELECT u.user_name FROM songs_shares as ss JOIN users as u ON ss.user_id=u.user_id WHERE ss.song_id=?");
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				actions.get(Actions.SHARE).add(userDao.getUser(rs.getString(1))); 
			}
		}
		else{
			//getting playlist likes
			stmt=con.prepareStatement("SELECT u.user_name FROM playlists_likes as pl JOIN users as u ON pl.user_id=u.user_id WHERE pl.playlist_id=?");
			stmt.setLong(1, id);
			
			rs = stmt.executeQuery();
			while (rs.next()) {
				actions.get(Actions.LIKE).add(userDao.getUser(rs.getString(1)));
			}
			
			//getting playlist dislikes
			stmt = con.prepareStatement("SELECT u.user_name FROM playlists_dislikes as pd JOIN users as u ON pd.user_id=u.user_id WHERE pd.playlist_id=?");
			stmt.setLong(1, id);
            rs = stmt.executeQuery();
			
			while (rs.next()) {
				actions.get(Actions.DISLIKE).add(userDao.getUser(rs.getString(1)));
			}
			
			//getting playlist shares
			stmt = con.prepareStatement("SELECT u.user_name FROM playlists_shares as ps JOIN users as u ON ps.user_id=u.user_id WHERE ps.playlist_id=?");
			stmt.setLong(1, id);
		    rs = stmt.executeQuery();
		
		    while (rs.next()) {
			    actions.get(Actions.SHARE).add(userDao.getUser(rs.getString(1))); 
		    }
		}
		
		return actions;
	}
	
	public synchronized HashMap<Actions, HashSet<User>> getCommentsLikes(long commentID) throws SQLException{
		HashMap<Actions, HashSet<User>> commentLikes=new HashMap<>();
		commentLikes.put(Actions.LIKE, new HashSet<>());
		
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("SELECT username FROM comments_likes as cl JOIN users as u"
				                                  + "ON u.user_id=cl.user_id "
				                                  + "WHERE cl.comment_id=?");
		stmt.setLong(1, commentID);
		ResultSet rs=stmt.executeQuery();
		
		while(rs.next()){
			commentLikes.get(Actions.LIKE).add(userDao.getUser(rs.getString(1)));
		}
		
		return commentLikes;
	}
	
	public synchronized void removeLike(boolean isSong, long actionableId, long userId) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		if(isSong){
			PreparedStatement stmt = con.prepareStatement("DELETE FROM songs_likes WHERE song_id=? AND user_id=?");
			stmt.setLong(1, actionableId);
			stmt.setLong(2, userId);
			stmt.executeUpdate();
		}
		if(!isSong){
			PreparedStatement stmt = con.prepareStatement("DELETE FROM playlists_likes WHERE song_id=? AND user_id=?");
			stmt.setLong(1, actionableId);
			stmt.setLong(2, userId);
			stmt.executeUpdate();
		}
	}
	
	public synchronized void removeDislike(boolean isSong, long actionableId, long userId) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		if(isSong){
			PreparedStatement stmt = con.prepareStatement("DELETE FROM songs_dislikes WHERE song_id=? AND user_id=?");
			stmt.setLong(1, actionableId);
			stmt.setLong(2, userId);
			stmt.executeUpdate();
		}
		if(!isSong){
			PreparedStatement stmt = con.prepareStatement("DELETE FROM playlists_dislikes WHERE song_id=? AND user_id=?");
			stmt.setLong(1, actionableId);
			stmt.setLong(2, userId);
			stmt.executeUpdate();
		}
	}
	
	public synchronized void deleteLikes(boolean isSong, long id) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		
//		PreparedStatement stmt = con.prepareStatement(this.deleteQuery);
//		stmt.setString(1, isSong ? "songs_likes" : "playlists_likes");
//		stmt.setString(2, isSong ? "song_id" : "playlist_id");
//		stmt.setLong(3, id);
//		stmt.execute();
		
		if(isSong){
			PreparedStatement stmt = con.prepareStatement("DELETE FROM songs_likes WHERE song_id=?");
			stmt.setLong(1, id);
			stmt.executeUpdate();
		}
		if(!isSong){
			PreparedStatement stmt = con.prepareStatement("DELETE FROM playlists_likes WHERE playlist_id=?");
			stmt.setLong(1, id);
			stmt.executeUpdate();
		}
	}
	
	public synchronized void deleteDislikes(boolean isSong, long id) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		
		//PreparedStatement stmt = con.prepareStatement(this.deleteQuery);
//		stmt.setString(1, isSong ? "songs_dislikes" : "playlists_dislikes");
//		stmt.setString(2, isSong ? "song_id" : "playlist_id");
		//stmt.setLong(3, id);
		//stmt.executeUpdate();
		if(isSong){
			PreparedStatement stmt = con.prepareStatement("DELETE FROM songs_dislikes WHERE song_id=?");
			stmt.setLong(1, id);
			stmt.executeUpdate();
		}
		if(!isSong){
			PreparedStatement stmt = con.prepareStatement("DELETE FROM playlists_dislikes WHERE playlist_id=?");
			stmt.setLong(1, id);
			stmt.executeUpdate();
		}
		
	}
	
	public synchronized void deleteShares(boolean isSong, long id) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		
		PreparedStatement stmt = con.prepareStatement(this.deleteQuery);
		stmt.setString(1, isSong ? "songs_shares" : "playlists_shares");
		stmt.setString(2, isSong ? "song_id" : "playlist_id");
		stmt.setLong(3, id);
		
		stmt.execute();
	}
	public synchronized void deleteCommentLikes(long id) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		
		//deleting likes
		PreparedStatement stmt = con.prepareStatement(this.deleteQuery);
		stmt.setString(1, "comments_likes");
		stmt.setString(2, "comment_id");
		stmt.setLong(3, id);
		
		stmt.execute();
	}
}
