package com.sisound.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.TreeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.sisound.model.Actionable;
import com.sisound.model.Comment;


@Component
public class CommentDao {

	@Autowired
	public UserDao userDao;
	@Autowired
	public ActionsDao actionDao;
	
	public synchronized void insertComment(Comment comment, Actionable commented) throws SQLException{
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement("INSERT INTO " + (commented.isSong() ? "songs_comments" : "playlists_comments")
													+" (user_id, comment_text, upload_date, " + (commented.isSong() ? "song_id" : "playlist_id")
													+ ") VALUES (?, ?, ?, ?)");
		
		stmt.setLong(1, userDao.getUser(comment.getUser()).getUserID());
		stmt.setString(2, comment.getText());
		stmt.setTimestamp(3, Timestamp.valueOf(comment.getDate()));
		stmt.setLong(4, commented.getId());
		stmt.executeUpdate();
	}
	
	public synchronized void insertSubComment(Comment comment, Actionable commented, long parentId) throws SQLException{
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement("INSERT INTO " + (commented.isSong() ? "songs_comments" : "playlists_comments")
													+" (user_id, comment_text, upload_date, " + (commented.isSong() ? "song_id" : "playlist_id")
													+ ", parent_id) VALUES (?, ?, ?, ?, ?)");
		
		stmt.setLong(1, userDao.getUser(comment.getUser()).getUserID());
		stmt.setString(2, comment.getText());
		stmt.setTimestamp(3, Timestamp.valueOf(comment.getDate()));
		stmt.setLong(4, commented.getId());
		stmt.setLong(5, parentId);
		stmt.executeUpdate();
	}
	
	
	//OK
	public synchronized TreeSet<Comment> getComments(long id, boolean isSong) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement stmt = null;
		ResultSet rs=null;

		stmt=con.prepareStatement("SELECT c.comment_id, u.user_name, c.comment_text, c.upload_date, c.parent_id FROM " + (isSong ? "songs_comments" : "playlists_comments") + " as c "
								+ "JOIN users as u "
			                   + "ON c.user_id = u.user_id "
			                   + "WHERE " + (isSong ? "c.song_id" : "c.playlist_id") + " = ? "
			                   + "AND parent_id IS NULL");
		stmt.setLong(1, id);
		rs = stmt.executeQuery();
		
		
		HashMap<Long, Comment> mainComments = new HashMap<>();
		
		Comment c = null;
		while (rs.next()) {
			c = new Comment(rs.getLong(1), rs.getString(2), 
					rs.getString(3), rs.getTimestamp(4).toLocalDateTime(), null, new TreeSet());
			
			c.setActions(actionDao.getCommentActions(isSong, rs.getLong(1)));
			mainComments.put(rs.getLong(1), c); 
		}
		

		stmt = con.prepareStatement("SELECT c.comment_id, u.user_name, c.comment_text, c.upload_date, c.parent_id FROM " + (isSong ? "songs_comments" : "playlists_comments") + " as c "
				                  + "JOIN users as u "
				                  + "ON c.user_id = u.user_id "
				                  + "WHERE " + (isSong ? "c.song_id" : "c.playlist_id") + " = ? "
				                  + "AND parent_id IS NOT NULL");
		stmt.setLong(1, id);
		rs = stmt.executeQuery();
		
		while (rs.next()) {
			c = new Comment(rs.getLong(1), rs.getString(2), 
					rs.getString(3), rs.getTimestamp(4).toLocalDateTime(), mainComments.get(rs.getLong(5)), new TreeSet());
			c.setActions(actionDao.getCommentActions(isSong, rs.getLong(1)));
			mainComments.get(rs.getLong(5)).addSubcomment(c);
		}

		
		TreeSet<Comment> set = new TreeSet<Comment>();
		for (Long key : mainComments.keySet()) {
		     set.add(mainComments.get(key));
		}
		
		return set;
	}
	
	public synchronized void deleteComments(long id, boolean isSong) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement("DELETE FROM ? WHERE ? = ?");
		stmt.setString(1, isSong ? "songs_comments" : "playlists_comments");
		stmt.setString(2, isSong ? "song_id" : "playlist_id");
		stmt.setLong(3, id);
		
		stmt.execute();
	}
	
	public synchronized boolean isCommentLiked(boolean isSong, long songId, long userId) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("SELECT count(*) FROM " + (isSong ? "song_comment_likes" : "playlist_comment_likes") + " WHERE comment_id=? AND user_id=?");
		stmt.setLong(1, songId);
		stmt.setLong(2, userId);
		ResultSet rs=stmt.executeQuery();
		rs.next();
		int count=rs.getInt(1);
		System.out.println(count);
		return count>0;
	}
	
	public synchronized boolean isCommentDisliked(boolean isSong, long songId, long userId) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("SELECT count(*) FROM " + (isSong ? "song_comment_dislikes" : "playlist_comment_dislikes") + " WHERE comment_id=? AND user_id=?");
		stmt.setLong(1, songId);
		stmt.setLong(2, userId);
		ResultSet rs=stmt.executeQuery();
		rs.next();
		int count=rs.getInt(1);
		return count>0;
	}

}
