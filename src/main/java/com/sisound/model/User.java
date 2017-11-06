package com.sisound.model;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.TreeSet;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class User {

	private long userID;
	private String firstName;
	private String lastName;
	@NotNull
	@Size(min = 3, max = 45)
	@NotEmpty
	@Pattern(regexp = "[^\\s]+")
	private String username;
	@NotNull
	@Size(min=8)	
//	@Pattern(regexp="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&*+=])(?=\\S+$).{8,}")
	private String password;
	@NotNull
	@NotEmpty
	@Email
	@Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$")
	private String email;
	private String city;
	private String country;
	private String bio;
	private String profilPicture;
	private String coverPhoto;
	private TreeSet<Song> songs;
	private TreeSet<Playlist> playlists;
	private LinkedHashSet<User> followers;
	private HashMap<Long, Boolean> followedIds;
	private HashMap<Long, Boolean> likedSongs;
	private HashMap<Long, Boolean> dislikedSongs;
	private HashMap<Long, Boolean> likedSongComments;
	private HashMap<Long, Boolean> dislikedSongComments;
	private HashMap<Long, Boolean> likedPlaylistComments;
	private HashMap<Long, Boolean> dislikedPlaylistComments;
		
	public User(){}
	//constructor for registering user
	public User(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.songs=new TreeSet();
		this.playlists=new TreeSet();
		this.followers=new LinkedHashSet();
		this.followedIds = new HashMap<>();
		this.likedSongs = new HashMap<>();
		this.dislikedSongs = new HashMap<>();
	}
	
	public User(long userID, String firstName, String lastName, String username, String password, String email,
			String city, String country, String bio, String profilPicture, String coverPhoto) {
		this(username, password, email);
		this.userID = userID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
		this.country = country;
		this.bio = bio;
		this.profilPicture = profilPicture;
		this.coverPhoto = coverPhoto;
	}

	public void addFollower(User u){
		this.followers.add(u);
	}
	
	public void addFollowing(long id) {
		this.followedIds.put(id, true);
	}
	
	public void removeFollowing(long id) {
		this.followedIds.put(id, false);
	}
	
	public void uploadSong(Song song){
		this.songs.add(song);
	}
	
	public void addPlaylist(Playlist p){
		this.playlists.add(p);
	}
	
	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getName() {
		if(this.firstName == null && this.lastName == null) {
			return "";
		}
		return firstName + " " + lastName;
	}
	
	public String getAdress() {
		if(this.city == null) {
			return "";
		}
		return city + " " + country;
	}
	
	public String getLastName() {
		return lastName;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getProfilPicture() {
		return profilPicture;
	}

	public void setProfilPicture(String profilPicture) {
		this.profilPicture = profilPicture;
	}

	public String getCoverPhoto() {
		return coverPhoto;
	}

	public void setCoverPhoto(String coverPhoto) {
		this.coverPhoto = coverPhoto;
	}

	public TreeSet<Song> getSongs() {
		return songs;
	}

	public void setSongs(TreeSet<Song> songs) {
		this.songs = songs;
	}
	
	public TreeSet<Playlist> getPlaylists() {
		return playlists;
	}

	public void setPlaylists(TreeSet<Playlist> playlists) {
		this.playlists = playlists;
	}
	
	public LinkedHashSet<User> getFollowers() {
		return followers;
	}

	public void setFollowers(LinkedHashSet<User> followers) {
		this.followers = followers;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (userID ^ (userID >>> 32));
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setUsername(String username) {
		this.username = username.trim();
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public HashMap<Long, Boolean> getFollowedIds() {
		return followedIds;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (userID != other.userID)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	public void setDislikedSongs(HashMap<Long, Boolean> dislikedSongs) {
		this.dislikedSongs = dislikedSongs;
	}
	
	public void setLikedSongs(HashMap<Long, Boolean> likedSongs) {
		this.likedSongs = likedSongs;
	}
	
	public HashMap<Long, Boolean> getLikedSongs() {
		return likedSongs;
	}
	
	public HashMap<Long, Boolean> getDislikedSongs() {
		return dislikedSongs;
	}
	
	public void addLike(long songId) {
		this.likedSongs.put(songId, true);
	}
	
	public void removeLike(long songId) {
		this.likedSongs.put(songId, false);
	}
	
	public void addDislike(long songId) {
		this.dislikedSongs.put(songId, true);
	}
	
	public void removeDislike(long songId) {
		this.dislikedSongs.put(songId, false);
	}
	
	public void setFollowedIds(HashMap<Long, Boolean> followedIds) {
		this.followedIds = followedIds;
	}
	
	public HashMap<Long, Boolean> getDislikedPlaylistComments() {
		return dislikedPlaylistComments;
	}
	
	public HashMap<Long, Boolean> getDislikedSongComments() {
		return dislikedSongComments;
	}
	
	public HashMap<Long, Boolean> getLikedPlaylistComments() {
		return likedPlaylistComments;
	}
	
	public HashMap<Long, Boolean> getLikedSongComments() {
		return likedSongComments;
	}
	
	public void setDislikedPlaylistComments(HashMap<Long, Boolean> dislikedPlaylistComments) {
		this.dislikedPlaylistComments = dislikedPlaylistComments;
	}
	
	public void setDislikedSongComments(HashMap<Long, Boolean> dislikedSongComments) {
		this.dislikedSongComments = dislikedSongComments;
	}
	
	public void setLikedPlaylistComments(HashMap<Long, Boolean> likedPlaylistComments) {
		this.likedPlaylistComments = likedPlaylistComments;
	}
	
	public void setLikedSongComments(HashMap<Long, Boolean> likedSongComments) {
		this.likedSongComments = likedSongComments;
	}
	
	public void addLikeSongComment(long songId) {
		this.likedSongComments.put(songId, true);
	}
	
	public void removeLikeSongComment(long songId) {
		this.likedSongComments.put(songId, false);
	}
	
	public void addDislikeSongComment(long songId) {
		this.dislikedSongComments.put(songId, true);
	}
	
	public void removeDislikeSongComment(long songId) {
		this.dislikedSongComments.put(songId, false);
	}
	
	public void addLikePlaylistComment(long songId) {
		this.likedPlaylistComments.put(songId, true);
	}
	
	public void removeLikePlaylistComment(long songId) {
		this.likedPlaylistComments.put(songId, false);
	}
	
	public void addDislikePlaylistComment(long songId) {
		this.dislikedPlaylistComments.put(songId, true);
	}
	
	public void removeDislikePlaylistComment(long songId) {
		this.dislikedPlaylistComments.put(songId, false);
	}
	
	public void removeCommentDislike(boolean isSong, long songCommentId) {
		if(isSong) {
			removeDislikeSongComment(songCommentId);
		} else {
			removeDislikePlaylistComment(songCommentId);
		}	
	}
	
	public void removeCommentLike(boolean isSong, long songCommentId) {
		if(isSong) {
			removeLikeSongComment(songCommentId);
		} else {
			removeLikePlaylistComment(songCommentId);
		}	
	}
	
	public void addCommentDislike(boolean isSong, long songCommentId) {
		if(isSong) {
			addDislikeSongComment(songCommentId);
		} else {
			addDislikePlaylistComment(songCommentId);
		}	
	}
	
	public void addCommentLike(boolean isSong, long songCommentId) {
		if(isSong) {
			addLikeSongComment(songCommentId);
		} else {
			addLikePlaylistComment(songCommentId);
		}	
	}
	
}
