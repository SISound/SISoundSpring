package com.sisound.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class User {

	private long userID;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private String email;
	private String city;
	private String country;
	private String bio;
	private String profilPicture;
	private String coverPhoto;
	private TreeSet<Song> songs;
	private TreeSet<Playlist> playlists;
	private LinkedHashSet<User> followers;
	private HashSet<Long> followedIds;
	
	public User(){}
	//constructor for registering user
	public User(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.songs=new TreeSet();
		this.playlists=new TreeSet();
		this.followers=new LinkedHashSet();
		this.followedIds=new HashSet<>();
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
		return firstName + " " + lastName;
	}
	
	public String getAdress() {
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
		this.username = username;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public HashSet<Long> getFollowedIds() {
		return followedIds;
	}
	
	public boolean isFollowing(Long userId){
		return this.followedIds.contains(userId);
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
	
}
