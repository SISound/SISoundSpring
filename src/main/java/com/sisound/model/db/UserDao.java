package com.sisound.model.db;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import com.google.common.hash.Hashing;
import com.sisound.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class UserDao {

	@Autowired
	private CountryDao countryDao;
	@Autowired
	private SongDao songDao;
	@Autowired
	private PlaylistDao playlistDao;
	@Autowired
	private ActionsDao actionDao;

	
	public synchronized void insertUser(User u) throws SQLException{
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement stmt = con.prepareStatement("INSERT INTO users (user_name, user_password, email) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, u.getUsername());
		stmt.setString(2, Hashing.sha512().hashString(u.getPassword(), StandardCharsets.UTF_8).toString());
		stmt.setString(3, u.getEmail());
		stmt.executeUpdate();//here
		ResultSet rs = stmt.getGeneratedKeys();//and here
		rs.next();
		u.setUserID(rs.getLong(1));
	}
	
	public synchronized boolean existsUser(String username, String password) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("SELECT count(*) as count FROM users where user_name=? AND user_password=?");
		stmt.setString(1, username);
		stmt.setString(2, Hashing.sha512().hashString(password, StandardCharsets.UTF_8).toString());
		ResultSet rs = stmt.executeQuery();
		rs.next();
		
		return rs.getInt(1)>0;
	}
	
	public synchronized User getUser(String username) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("SELECT u.user_id, u.user_name, u.user_password, u.email, "
				                                  + "u.first_name, " 
				                                  + "u.last_name, "
				                                  + "u.city_name, "
				                                  + "c.country_name as country, "
				                                  + "u.bio, "
				                                  + "u.profile_pic, "
				                                  + "u.cover_photo "
				                                  + "FROM users as u LEFT join countries as c on u.country_id=c.country_id "
				                                  + "WHERE user_name=?")/*"SELECT u.user_id, u.user_name, u.user_password, u.email, u.first_name, u.last_name, u.city_name, c.country_name, u.bio, u.profile_pic, u.cover_photo" + 
				                                  		"FROM users as u LEFT JOIN countries as c on u.country_id = c.country_id WHERE user_name = ?")*/;
		stmt.setString(1, username);
		ResultSet rs=stmt.executeQuery();
		rs.next();
		User u=new User(rs.getLong(1), rs.getString(5), rs.getString(6), rs.getString(2), rs.getString(3), rs.getString(4),
				rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11));
		
		u.setSongs(songDao.getSongsForUser(u));
		u.setPlaylists(playlistDao.getPlaylistsForUser(u));
		u.setFollowers(getFollowers(u));
		u.setFollowedIds(getFollowedIds(u));
		u.setLikedSongs(actionDao.getSongsAction(u.getUserID(), true));
		u.setDislikedSongs(actionDao.getSongsAction(u.getUserID(), false));
		
		u.setLikedSongComments(actionDao.getSongCommentsAction(u.getUserID(), true));
		u.setDislikedSongComments(actionDao.getSongCommentsAction(u.getUserID(), false));
		u.setLikedPlaylistComments(actionDao.getPlaylistCommentsAction(u.getUserID(),true));
		u.setDislikedPlaylistComments(actionDao.getPlaylistCommentsAction(u.getUserID(), false));
		
		return u;
	}
	
	public synchronized HashMap<Long, Boolean> getFollowedIds(User u) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("SELECT followed_id FROM follows WHERE follower_id=?");
		
		stmt.setLong(1, u.getUserID());
		ResultSet rs=stmt.executeQuery();
		HashMap<Long, Boolean> followedIds = new HashMap<>();
		
		while(rs.next()){
			followedIds.put(rs.getLong(1), true);
		}
		
		return followedIds;
	}
	
	public synchronized HashSet<Long> getAllUsersIds() throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("SELECT user_id FROM users");
		ResultSet rs=stmt.executeQuery();
		HashSet<Long> allUsers=new HashSet<>();
		while(rs.next()){
			allUsers.add(rs.getLong(1));
		}
		
		return allUsers;
	}
	
	public synchronized LinkedHashSet<User> getFollowers(User u) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("SELECT u.user_id, "
									              + "u.first_name, " 
									              + "u.last_name, "
									              + "u.user_name, "
									              + "u.user_password, "
									              + "u.email, "
									              + "u.city_name, "
									              + "c.country_name, "
									              + "u.bio, "
				                                  + "u.profile_pic, "
				                                  + "u.cover_photo "
				                                  + "FROM follows as f "
				                                  + "JOIN users as u "
				                                  + "ON f.follower_id=u.user_id "
				                                  + "LEFT JOIN countries as c "
				                                  + "ON u.country_id=c.country_id "
				                                  + "WHERE f.followed_id=?");
		stmt.setLong(1, u.getUserID());
		ResultSet rs=stmt.executeQuery();
		LinkedHashSet<User> followers=new LinkedHashSet<>();
		while(rs.next()){
			followers.add(new User(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), 
					               rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11)));
		}
		
		return followers;
	}
	           
	public synchronized void followUser(long followerId, long followedId) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("INSERT INTO follows (follower_id, followed_id) VALUES (?, ?)");
		stmt.setLong(1, followerId);
		stmt.setLong(2, followedId);
		stmt.executeUpdate();
	}
	
	public synchronized void unfollowUser(long followerId, long followedId) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("DELETE FROM follows WHERE follower_id=? AND followed_id=?");
		stmt.setLong(1, followerId);
		stmt.setLong(2, followedId);
		stmt.executeUpdate();
	}

	public synchronized boolean usernameExists(String username) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("SELECT count(*) as count FROM users WHERE user_name = ?");
		stmt.setString(1, username);
		ResultSet rs=stmt.executeQuery();
		rs.next();
		return rs.getInt(1)>0;
	}

	public synchronized boolean emailExists(String email) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("SELECT count(*) as count FROM users WHERE email = ?");
		stmt.setString(1, email);
		ResultSet rs=stmt.executeQuery();
		rs.next();
		return rs.getInt("count")>0;	
	}
	
	public synchronized boolean passwordCheck (String password, String username) throws SQLException {
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("SELECT count(*) as count FROM users WHERE user_name = ? AND user_password = ?");
		stmt.setString(1, username);
		stmt.setString(1, Hashing.sha512().hashString(password, StandardCharsets.UTF_8).toString());
		ResultSet rs=stmt.executeQuery();
		rs.next();
		return rs.getInt("count")>0;	
	}
	
	public synchronized void passwordChange (String newPassword, String username) throws SQLException {
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("UPDATE users SET user_password = ? WHERE user_name = ?");
		stmt.setString(2, username);
		stmt.setString(1, Hashing.sha512().hashString(newPassword, StandardCharsets.UTF_8).toString());
		stmt.execute();
	}
	
	public synchronized void emailChange (String newEmail, String username) throws SQLException {
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("UPDATE users SET email = ? WHERE user_name = ?");
		stmt.setString(2, username);
		stmt.setString(1, newEmail);
		stmt.execute();
	}
	
	public synchronized void editProfile(User user) throws SQLException {
		Connection con=DBManager.getInstance().getConnection();
		PreparedStatement stmt=con.prepareStatement("UPDATE users SET city_name = ?, bio = ?, profile_pic = ?, first_name = ?, "
				                                  + "last_name = ?, cover_photo = ?, country_id = ? WHERE user_name = ?");
		stmt.setString(1, user.getCity());
		stmt.setString(2, user.getBio());
		stmt.setString(3, user.getProfilPicture());
		stmt.setString(4, user.getFirstName());
		stmt.setString(5, user.getLastName());
		stmt.setString(6, user.getCoverPhoto());
		stmt.setLong(7, countryDao.getCountryId(user.getCountry()));			

		stmt.setString(8, user.getUsername());
		
		stmt.execute();
	}
	
	public synchronized HashSet<User> searchUser(String search) throws SQLException{
		Connection con=DBManager.getInstance().getConnection();
		
		PreparedStatement stmt=con.prepareStatement("SELECT u.user_id, u.user_name, u.user_password, u.email, u.first_name, u.last_name, "
				                                  + "u.city_name, c.country_name, u.bio, u.profile_pic, u.cover_photo "
				                                  + "FROM users as u LEFT join countries as c on u.country_id=c.country_id "
				                                  + "WHERE u.user_name LIKE ? OR u.first_name LIKE ? OR u.last_name LIKE ?");
		stmt.setString(1, "%" + search + "%");
		stmt.setString(2, "%" + search + "%");
		stmt.setString(3, "%" + search + "%");
		ResultSet rs=stmt.executeQuery();
		HashSet<User> users=new HashSet<>();
		while(rs.next()){
			users.add(new User(rs.getLong(1), rs.getString(5), rs.getString(6), rs.getString(2), rs.getString(3), rs.getString(4),
					rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11)));
		}
		return users;	                                 
	}
}
