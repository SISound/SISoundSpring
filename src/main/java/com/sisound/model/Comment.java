package com.sisound.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

public class Comment implements Comparable<Comment>, Actionable{

	private long commentId;
	private String user;
	private String text;
	private LocalDateTime date;
	private TreeSet<Comment> subcoments;
	private Comment parentComment;
	private HashMap<Actions, HashSet<String>> likes;
	
	public Comment(String user, String text, LocalDateTime date, Comment parentComment) {
		this.user = user;
		this.text = text;
		this.date = date;
		this.subcoments = new TreeSet<>();
		this.parentComment = parentComment;
		if(parentComment != null) {
			parentComment.subcoments.add(this);
		}
		this.likes=new HashMap<>();
		this.likes.put(Actions.LIKE, new HashSet<>());
	}
	
	public Comment(long commentId, String user, String text, LocalDateTime date, Comment parentComment, TreeSet<Comment> subComments) {
		this(user, text, date, parentComment);
		this.commentId = commentId;
		this.subcoments = subComments;
	}

	public String getUser() {
		return user;
	}

	public String getText() {
		return text;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public TreeSet<Comment> getSubcoments() {
		return subcoments;
	}

	public Comment getParentComment() {
		return parentComment;
	}

	public void addSubcomment(Comment comment) {
		this.subcoments.add(comment);
	}

	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public int compareTo(Comment o) {
		return o.getDate().compareTo(this.getDate());
	}

	@Override
	public boolean isSong() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isComment() {
		return true;
	}

	@Override
	public long getId() {
		return commentId;
	}

	@Override
	public void setId(long id) {
		this.commentId = id;
	}
	
	public void setActions(HashMap<Actions, HashSet<String>> likes) {
		this.likes = likes;
	}
	
	public int getLikesCount() {
		System.out.println(this.commentId);
		System.out.println(this.commentId);
		System.out.println(likes.get(Actions.LIKE).size());
		System.out.println(likes.get(Actions.LIKE).size());
		System.out.println(likes.get(Actions.LIKE).size());
		return likes.get(Actions.LIKE).size();
	}
	
	public int getDislikesCount() {
		return likes.get(Actions.DISLIKE).size();
	}

}
