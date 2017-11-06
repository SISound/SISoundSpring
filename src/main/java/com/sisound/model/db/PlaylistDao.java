package com.sisound.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.TreeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sisound.model.Comment;
import com.sisound.model.Playlist;
import com.sisound.model.Song;
import com.sisound.model.User;

@Component
public class PlaylistDao {
	
	@Autowired
	public UserDao userDao;

	@Autowired
	public ActionsDao actionsDao;
	
	@Autowired
	public SongDao songDao;
	
	@Autowired
	public CommentDao commentDao;
	
	//OK
	public synchronized void createPlaylist(Playlist playlist) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("INSERT INTO playlists (playlist_name, user_id, upload_date, isPrivate) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, playlist.getTitle());
		stmt.setLong(2, playlist.getUser().getUserID());
		stmt.setString(3, playlist.getCreationDate().toString());
		stmt.setBoolean(4, playlist.getIsPrivate());
		stmt.executeUpdate();
		ResultSet rs=stmt.getGeneratedKeys();
		rs.next();
		playlist.setId(rs.getLong(1));
	}
	
	//OK
	public synchronized boolean existPlaylist(Playlist playlist) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("SELECT count(*) FROM playlists WHERE playlist_id=?");
		stmt.setLong(1, playlist.getId());
		ResultSet rs=stmt.executeQuery();
		rs.next();
		int count=rs.getInt(1);
		return count>0;
	}
	
	//OK
	public synchronized TreeSet<Playlist> getPlaylistsForUser(User u) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("SELECT playlist_id, playlist_name, upload_date, isPrivate FROM playlists WHERE user_id=?");
		stmt.setLong(1, u.getUserID());
		ResultSet rs=stmt.executeQuery();
		TreeSet<Playlist> playlists=new TreeSet<>();

		
		while(rs.next()){
			playlists.add(new Playlist(rs.getLong(1), rs.getString(2), rs.getTimestamp(3).toLocalDateTime(), u, actionsDao.getActions(false, rs.getLong(1)),
					commentDao.getComments(rs.getLong(1), false), rs.getBoolean(4), songDao.getSongsForPlaylist(rs.getLong(1), u)));
		}
		
		return playlists;
	}
	
	public synchronized long getPlaylistId(String plTitle, long userId) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("SELECT playlist_id FROM playlists WHERE playlist_name=? AND user_id=?");
		stmt.setString(1, plTitle);
		stmt.setLong(2, userId);
		ResultSet rs=stmt.executeQuery();
		rs.next();
		return rs.getLong(1);
	}
	
	public synchronized HashSet<Playlist> searchPlaylist(String search) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("SELECT p.playlist_id, p.playlist_name, u.user_name, p.upload_date, p.isPrivate "
												  + "FROM playlists as p JOIN users as u ON p.user_id=u.user_id "
												  + "WHERE p.playlist_name LIKE ? AND p.isPrivate = 0");
		stmt.setString(1, "%" + search + "%");
		ResultSet rs = stmt.executeQuery();
		HashSet<Playlist> playlists=new HashSet<>();
		while(rs.next()){
			User tmp = new User();
			tmp.setUsername("");
			playlists.add(new Playlist(rs.getLong(1), rs.getString(2), rs.getTimestamp(4).toLocalDateTime(), userDao.getUser(rs.getString(3)),
	                actionsDao.getActions(false, rs.getLong(1)), commentDao.getComments(rs.getLong(1), false), 
	                rs.getBoolean(5),songDao.getSongsForPlaylist(rs.getLong(1), tmp)));
		}
		
		return playlists;
	}
	
	public synchronized Playlist searchPlaylistById(Long id) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("SELECT p.playlist_id, p.playlist_name, u.user_name, p.upload_date, p.isPrivate \r\n" + 
													"FROM playlists as p JOIN users as u \r\n" + 
													"ON p.user_id = u.user_id \r\n" + 
													"WHERE playlist_id = ?;");
		stmt.setLong(1, id);
		ResultSet rs=stmt.executeQuery();
		rs.next();
		
		Playlist p=new Playlist(rs.getLong(1), rs.getString(2), rs.getTimestamp(4).toLocalDateTime(), userDao.getUser(rs.getString(3)),
				                actionsDao.getActions(false, rs.getLong(1)), commentDao.getComments(rs.getLong(1), false), 
				                rs.getBoolean(5),songDao.getSongsForPlaylist(rs.getLong(1), userDao.getUser(rs.getString(3))));
		
		//p.setSongs(songDao.getSongsForPlaylist(id));
		
		return p;
	}
	
	//deleting playlist
	public synchronized void deletePlaylist(Playlist playlist) throws SQLException {
		Connection con=DBManager.getInstance().getConnection();
		con.setAutoCommit(false);
			
		try {
			//delete comment-likes

			for (Comment comment : commentDao.getComments(playlist.getId(), false)) {
				actionsDao.deleteCommentLikes(comment.getId());
			}
			
			//delete comments
			commentDao.deleteComments(playlist.getId(), false);
			
			//delete likes
			actionsDao.deleteLikes(false, playlist.getId());
			//delete dislikes
			actionsDao.deleteDislikes(false, playlist.getId());
			//delete shares
			actionsDao.deleteShares(false, playlist.getId());
			
			
			//delete from playlist_songs
			PreparedStatement stmt = con.prepareStatement("DELETE FROM playlists_songs WHERE playlist_id = ?");
			stmt.setLong(1, playlist.getId());
			stmt.execute();
			
			//delete playlist
			stmt=con.prepareStatement("DELETE FROM playlists WHERE playlist_id = ?");
			stmt.setLong(1, playlist.getId());
			stmt.execute();
				
			con.commit();
		} catch (SQLException e) {
			//reverse
			con.rollback();
			throw new SQLException();
	
		} finally {
			con.setAutoCommit(true);
		}
	}
	
//	public synchronized void addSongToPlaylist(long userId, String playlistName, Song s) throws SQLException{
//
//		songDao.uploadSong(s);
//		
//		//getting the ID of the playlist to which we want to add song 
//		Connection con=DBManager.getInstance().getConnection();
//		PreparedStatement stmt=con.prepareStatement("SELECT playlist_id FROM playlists WHERE user_id=? AND playlist_name=?");
//		stmt.setLong(1, userId);
//		stmt.setString(2, playlistName);
//		ResultSet rs=stmt.executeQuery();
//		rs.next();
//		long playlistId=rs.getLong(1);
//		
//		//adding the song to the playlist 
//		stmt=con.prepareStatement("INSERT INTO playlists_songs (playlist_id, song_id, upload_date) VALUES (?, ?, ?)");
//		stmt.setLong(1, playlistId);
//		stmt.setLong(2, s.getId());
//		stmt.setString(3, LocalDateTime.now().toString());
//	}
	
	public synchronized void addSongToPlaylist(long playlistId, long songId) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("INSERT INTO playlists_songs (playlist_id, song_id, upload_date) VALUES (?, ?, now())");
		stmt.setLong(1, playlistId);
		stmt.setLong(2, songId);
		stmt.execute();
	}
	
	public synchronized void deleteSongFromPlaylist(long userId, String playlistName, String songName) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		
		//getting the ID of the playlist which we want to delete song from
		PreparedStatement stmt=con.prepareStatement("SELECT playlist_id FROM playlists WHERE playlist_name=? AND user_id=?");
		stmt.setLong(1, userId);
		stmt.setString(2, playlistName);
		ResultSet rs=stmt.executeQuery();
		rs.next();
		long playlistId=rs.getLong(1);
				
		//getting the ID of the song we want to delete
		stmt=con.prepareStatement("SELECT song_id FROM songs WHERE song_name=? AND user_id=?");
		stmt.setString(1, songName);
		stmt.setLong(2, userId);
		rs=stmt.executeQuery();
		rs.next();
		long songId=rs.getLong(1);
		
		//deleting the song from the playlist
		stmt=con.prepareStatement("DELETE FROM playlists_songs WHERE playlist_id=? AND song_id=?");
		stmt.setLong(1, playlistId);
		stmt.setLong(2, songId);
		stmt.executeQuery();
	}

}
