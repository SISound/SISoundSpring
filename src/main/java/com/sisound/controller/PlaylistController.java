package com.sisound.controller;

import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sisound.model.Playlist;
import com.sisound.model.User;
import com.sisound.model.db.PlaylistDao;

@Controller
@MultipartConfig
public class PlaylistController {
	
	@Autowired
	private PlaylistDao playlistDao;
	
	@RequestMapping(value="createPlaylist", method = RequestMethod.POST)
	public String loginUser(HttpSession session, HttpServletRequest request){

		String playlistTitle = request.getParameter("title");
		String priv = request.getParameter("private");
		
		boolean checked = (priv != null && priv.length() > 0);
		User u = (User) session.getAttribute("sessionUser");
		
		if(u == null) {
			return "redirect:/loginPage";
		}
				
		try {
				Playlist playlist = new Playlist(playlistTitle, LocalDateTime.now(), u, checked);
				playlistDao.createPlaylist(playlist);
				u.addPlaylist(playlist);
									
				return "playlist";				
		} 
		catch (SQLException e) {
			System.out.println(e.getMessage());
			return "errorPage";
		}
	}

}
