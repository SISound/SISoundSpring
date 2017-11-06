package com.sisound.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sisound.model.Actionable;
import com.sisound.model.Actions;
import com.sisound.model.Song;
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
	public synchronized HashMap<Actions, HashSet<String>> getActions(boolean isSong, long id) throws SQLException {
		
		//creating the result map
		HashMap<Actions, HashSet<String>> actions;
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
				actions.get(Actions.LIKE).add(rs.getString(1));
			}
			
			//getting song dislikes
			stmt = con.prepareStatement("SELECT u.user_name FROM songs_dislikes as sd JOIN users as u ON sd.user_id=u.user_id WHERE sd.song_id=?");
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				actions.get(Actions.DISLIKE).add(rs.getString(1));
			}
			
			//getting song shares
			stmt = con.prepareStatement("SELECT u.user_name FROM songs_shares as ss JOIN users as u ON ss.user_id=u.user_id WHERE ss.song_id=?");
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				actions.get(Actions.SHARE).add(rs.getString(1)); 
			}
		}
		else{
			//getting playlist likes
			stmt=con.prepareStatement("SELECT u.user_name FROM playlists_likes as pl JOIN users as u ON pl.user_id=u.user_id WHERE pl.playlist_id=?");
			stmt.setLong(1, id);
			
			rs = stmt.executeQuery();
			while (rs.next()) {
				actions.get(Actions.LIKE).add(rs.getString(1));
			}
			
			//getting playlist dislikes
			stmt = con.prepareStatement("SELECT u.user_name FROM playlists_dislikes as pd JOIN users as u ON pd.user_id=u.user_id WHERE pd.playlist_id=?");
			stmt.setLong(1, id);
            rs = stmt.executeQuery();
			
			while (rs.next()) {
				actions.get(Actions.DISLIKE).add(rs.getString(1));
			}
			
			//getting playlist shares
			stmt = con.prepareStatement("SELECT u.user_name FROM playlists_shares as ps JOIN users as u ON ps.user_id=u.user_id WHERE ps.playlist_id=?");
			stmt.setLong(1, id);
		    rs = stmt.executeQuery();
		
		    while (rs.next()) {
			    actions.get(Actions.SHARE).add(rs.getString(1)); 
		    }
		}
		
		return actions;
	}
	
	public synchronized HashMap<Actions, HashSet<String>> getCommentActions(boolean isSong, long id) throws SQLException {
		
		//creating the result map
		HashMap<Actions, HashSet<String>> actions;
		actions = new HashMap<>();
		for (Actions act : Actions.values()) {
			actions.put(act, new HashSet());
		}
		
		Connection con = DBManager.getInstance().getConnection();

		PreparedStatement stmt = null;
		ResultSet rs=null;

			//getting comment likes
			stmt = con.prepareStatement("SELECT u.user_name FROM " + (isSong ? "song_comment_likes" : "playlist_comment_likes") + " as cl JOIN users as u ON cl.user_id = u.user_id WHERE cl.comment_id=?");
			stmt.setLong(1, id);
			
		    rs = stmt.executeQuery();
			while (rs.next()) {
				actions.get(Actions.LIKE).add(rs.getString(1));
			}
			
			//getting comment dislikes
			stmt = con.prepareStatement("SELECT u.user_name FROM " + (isSong ? "song_comment_dislikes" : "playlist_comment_dislikes") + " as cl JOIN users as u ON cl.user_id = u.user_id WHERE cl.comment_id=?");
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				actions.get(Actions.DISLIKE).add(rs.getString(1));
			}
					
		return actions;
	}
	
	public synchronized HashMap<Actions, HashSet<String>> getCommentsLikes(long commentID) throws SQLException{
		HashMap<Actions, HashSet<String>> commentLikes=new HashMap<>();
		commentLikes.put(Actions.LIKE, new HashSet<>());
		
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("SELECT username FROM comments_likes as cl JOIN users as u"
				                                  + "ON u.user_id=cl.user_id "
				                                  + "WHERE cl.comment_id=?");
		stmt.setLong(1, commentID);
		ResultSet rs=stmt.executeQuery();
		
		while(rs.next()){
			commentLikes.get(Actions.LIKE).add(rs.getString(1));
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
	
	public synchronized  HashMap<Long, Boolean> getSongsAction(long id, boolean isLike) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		
		PreparedStatement stmt = con.prepareStatement("SELECT song_id, user_id FROM "
													+ (isLike ? "songs_likes" : "songs_dislikes") +" WHERE user_id = ?");

		stmt.setLong(1, id);
		ResultSet rs = stmt.executeQuery();
		
		HashMap<Long, Boolean> likedSongs = new HashMap<>();
		
		while(rs.next()){
			likedSongs.put(rs.getLong("song_id"), true);
		}
		
		return likedSongs;
	}
	
	public synchronized  HashMap<Long, Boolean> getSongCommentsAction(long id, boolean isLike) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		
		PreparedStatement stmt = con.prepareStatement("SELECT comment_id, user_id FROM "
													+ (isLike ? "song_comment_likes" : "song_comment_dislikes") +" WHERE user_id = ?");

		stmt.setLong(1, id);
		ResultSet rs = stmt.executeQuery();
		
		HashMap<Long, Boolean> likedSongsComments = new HashMap<>();
		
		while(rs.next()){
			likedSongsComments.put(rs.getLong("comment_id"), true);
		}
		
		return likedSongsComments;
	}
	
	public synchronized  HashMap<Long, Boolean> getPlaylistCommentsAction(long id, boolean isLike) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		
		PreparedStatement stmt = con.prepareStatement("SELECT comment_id, user_id FROM "
													+ (isLike ? "playlist_comment_likes" : "playlist_comment_dislikes") +" WHERE user_id = ?");

		stmt.setLong(1, id);
		ResultSet rs = stmt.executeQuery();
		
		HashMap<Long, Boolean> likedPlaylistComments = new HashMap<>();
		
		while(rs.next()){
			likedPlaylistComments.put(rs.getLong("comment_id"), true);
		}
		
		return likedPlaylistComments;
	}

	public synchronized void removeDislikeComment(boolean isSongComment, long actionableId, long userId) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		
		PreparedStatement stmt = con.prepareStatement("DELETE FROM " +  
												(isSongComment? "song_comment_dislikes":"playlist_comment_dislikes") +
												" WHERE comment_id=? AND user_id=?");
		
		stmt.setLong(1, actionableId);
		stmt.setLong(2, userId);
		stmt.executeUpdate();
	}
	
	public synchronized void removeLikeComment(boolean isSongComment, long actionableId, long userId) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		
		PreparedStatement stmt = con.prepareStatement("DELETE FROM " +  
												(isSongComment? "song_comment_likes":"playlist_comment_likes") +
												" WHERE comment_id=? AND user_id=?");
		
		stmt.setLong(1, actionableId);
		stmt.setLong(2, userId);
		stmt.executeUpdate();
	}
	
	public synchronized void likeComment(boolean isSongComment, long song_id, long user_id) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("INSERT INTO " + (isSongComment? "song_comment_likes":"playlist_comment_likes") + " (user_id, comment_id) VALUES (?, ?)");
		
		stmt.setLong(1, user_id);
		stmt.setLong(2, song_id);
		stmt.execute();
	}
	
	public synchronized void dislikeComment(boolean isSongComment, long song_id, long user_id) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("INSERT INTO " + (isSongComment? "song_comment_dislikes":"playlist_comment_dislikes") + " (user_id, comment_id) VALUES (?, ?)");
		
		stmt.setLong(1, user_id);
		stmt.setLong(2, song_id);
		stmt.execute();
	}
}
