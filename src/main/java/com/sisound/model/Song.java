package com.sisound.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.TreeSet;

import java.util.HashSet;


public class Song implements Comparable<Song>, Actionable, Serializable {

	private long id;
	private String title;
	private LocalDateTime uploadDate;
	private int timesListened;
	private User user;
	private String url;
	private String genre;
	private HashMap<Actions, HashSet<String>> actions;
	private TreeSet<Comment> comments;
	
	//constructor for adding song
	public Song(String title, User user, String genre , String url, LocalDateTime uploadDate) {
		this.title = title;
		this.genre = genre;
		this.user =  user;
		this.url = url;
		this.uploadDate = uploadDate;
		this.timesListened = 0;
		this.actions = new HashMap<>();
		for (Actions action : Actions.values()) {
			this.actions.put(action, new HashSet<>());
		}
		this.comments = new TreeSet<>();
	}

	//constructor for retrieving from db
	public Song(long songId, String title, LocalDateTime uploadDate, int timesListened, User user, String url,
			String genre, HashMap<Actions, HashSet<String>> actions, TreeSet<Comment> comments) {
		this(title, user, genre, url, uploadDate);
		this.id = songId;
		this.timesListened = timesListened;
		this.actions = actions;
		this.comments = comments;
	}
	
	public void addComment(Comment c){
		this.comments.add(c);
	}
	
	@Override
	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public LocalDateTime getUploadDate() {
		return uploadDate;
	}
	
	public LocalDate getUploadDateOnly() {
		return uploadDate.toLocalDate();
	}

	public int getTimesListened() {
		return timesListened;
	}

	public User getUser() {
		return user;
	}

	public String getUrl() {
		return url;
	}

	public String getGenre() {
		return genre;
	}

	public HashMap<Actions, HashSet<String>> getActions() {
		return actions;
	}

	public TreeSet<Comment> getComments() {
		return comments;
	}

	@Override
	public void setId(long songId) {
		this.id = songId;
	}
	
	public void setTimesListened(int timesListened) {
		this.timesListened = timesListened;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public int getLikesCount(){
		return this.actions.get(Actions.LIKE).size();
	}

	public int getDislikesCount(){
		return this.actions.get(Actions.DISLIKE).size();
	}
	
	public void addAction(Actions action, String username) {
		this.actions.get(action).add(username);
	}
	
	@Override
	public boolean isSong() {
		return true;
	}

	@Override
	public boolean isComment() {
		return false;
	}

	@Override
	public int compareTo(Song o) {
		 return this.uploadDate.compareTo(o.uploadDate);
	}
}
