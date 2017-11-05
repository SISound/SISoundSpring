package com.sisound.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class Playlist implements Comparable<Playlist>, Actionable {

	private long id;
	private String title;
	private LocalDateTime creationDate;
	private User user;
	private HashMap<Actions, HashSet<String>> actions;
	private TreeSet<Comment> comments;
	private boolean isPrivate;
	private TreeMap<LocalDateTime, Song> songs;
	
	
	public Playlist(String title, LocalDateTime creationDate, User user, boolean isPrivate) {
		this.title = title;
		this.creationDate = creationDate;
		this.user = user;
		this.isPrivate = isPrivate;
		this.comments = new TreeSet<>();
		this.actions = new HashMap<>();
		for (Actions action : Actions.values()) {
			this.actions.put(action, new HashSet());
		}
		this.songs = new TreeMap<>();
	}


	public Playlist(long playlistID, String title, LocalDateTime creationDate, User user, HashMap<Actions, 
			HashSet<String>> actions, TreeSet<Comment> comments, boolean isPrivate, TreeMap<LocalDateTime, Song> songs) {
		this(title, creationDate, user, isPrivate);
		this.id = playlistID;
		this.actions = actions;
		this.comments = comments;
		this.songs = songs;
	}

	@Override
	public long getId() {
		return id;
	}


	public String getTitle() {
		return title;
	}


	public LocalDateTime getCreationDate() {
		return creationDate;
	}


	public User getUser() {
		return user;
	}


	public HashMap<Actions, HashSet<String>> getActions() {
		return actions;
	}


	public TreeSet<Comment> getComments() {
		return comments;
	}


	public boolean getIsPrivate() {
		return isPrivate;
	}


	public TreeMap<LocalDateTime, Song> getSongs() {
		return songs;
	}
	
	@Override
	public void setId(long playlistID) {
		this.id = playlistID;
	}
	
	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void addComment(Comment comment) {
		this.comments.add(comment);
	}
	
	public void addSong(Song song, LocalDateTime time) {
		this.songs.put(time, song);
	}
	
	public void addAction(Actions action, String username) {
		this.actions.get(action).add(username);
	}
	
	@Override
	public int compareTo(Playlist o) {
		return this.creationDate.compareTo(o.getCreationDate());
	}
	
	@Override
	public boolean isSong() {
		return false;
	}

	public boolean getIsSong() {
		return false;
	}

	@Override
	public boolean isComment() {
		return false;
	}
	
	public void setSongs(TreeMap<LocalDateTime, Song> songs) {
		this.songs = songs;
	}
	
	
}
