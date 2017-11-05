package com.sisound.controller;

import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sisound.model.Comment;
import com.sisound.model.Playlist;
import com.sisound.model.User;
import com.sisound.model.db.CommentDao;
import com.sisound.model.db.PlaylistDao;
import com.sisound.model.db.SongDao;
import com.sisound.model.db.UserDao;

@Controller
@MultipartConfig
public class CommentController {

	@Autowired
	UserDao userDao;
	@Autowired
	CommentDao commentDao;
	@Autowired
	PlaylistDao playlistDao;
	@Autowired
	SongDao songDao;
	
	@RequestMapping(value="addComment", method=RequestMethod.POST)
	public String addComment(HttpServletRequest req, HttpServletResponse resp, HttpSession session, @RequestParam(value = "id") long id, @RequestParam(value = "song") boolean isSong){
		
		User u = (User) session.getAttribute("sessionUser");
		String text = req.getParameter("commentText");
		
		if(session.isNew() || u==null){
			return "logReg";
		}
		
		try {
			if(!isSong) {
				commentDao.insertComment(new Comment(u.getUsername(), text, LocalDateTime.now(), null), playlistDao.searchPlaylistById(id));				
				return "redirect:/playlist?id=" + id;				
			} else if (isSong) {
				commentDao.insertComment(new Comment(u.getUsername(), text, LocalDateTime.now(), null), songDao.getSongById(id));				
				return "redirect:/track=" + id;
			} 
			return "errorPage";
		} 
		catch (SQLException e) {
			System.out.println(e.getMessage());
			return "errorPage";
		}
	}
	
	@RequestMapping(value="addSubComment", method=RequestMethod.POST)
	public String addSubComment(HttpServletRequest req, HttpServletResponse resp, HttpSession session,
			@RequestParam(value = "id") long id, @RequestParam(value = "song") boolean isSong, @RequestParam(value = "parent") long parentId){
		
		User u = (User) session.getAttribute("sessionUser");
		String text = req.getParameter("commentText");
		
		if(session.isNew() || u==null){
			return "logReg";
		}
		
		try {
			if(!isSong) {
				commentDao.insertSubComment(new Comment(u.getUsername(), text, LocalDateTime.now(), null), playlistDao.searchPlaylistById(id), parentId);				
				return "redirect:/playlist?id=" + id;				
			} else if (isSong) {
				commentDao.insertSubComment(new Comment(u.getUsername(), text, LocalDateTime.now(), null), songDao.getSongById(id), parentId);				
				return "redirect:/track=" + id;
			} 
			return "errorPage";
		} 
		catch (SQLException e) {
			System.out.println(e.getMessage());
			return "errorPage";
		}
	}
}
