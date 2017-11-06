package com.sisound.controller;

import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sisound.model.Playlist;
import com.sisound.model.User;
import com.sisound.model.db.PlaylistDao;
import com.sisound.model.db.SongDao;

@Controller
@MultipartConfig
public class PlaylistController {
	
	@Autowired
	private PlaylistDao playlistDao;
	@Autowired
	private SongDao songDao;
	
	@RequestMapping(value="createPlaylistPage", method=RequestMethod.GET)
	public String createPlaylistPage(HttpServletRequest request, Model model){
		long songId=Long.parseLong(request.getParameter("songId").toString());
		
		model.addAttribute("songId", songId);
		
		return "create_playlist";
	}
	
	@RequestMapping(value="createPlaylist{songId}", method = RequestMethod.POST)
	public String loginUser(@PathVariable long songId, HttpSession session, HttpServletRequest request){

		String playlistTitle = request.getParameter("title");
		String priv = request.getParameter("private");
		
		boolean checked = (priv != null && priv.length() > 0);
		User u = (User) session.getAttribute("sessionUser");
		
		if(session.isNew() || u == null) {
			return "redirect:/loginPage";
		}
				
		try {
				Playlist playlist = new Playlist(playlistTitle, LocalDateTime.now(), u, checked);
				playlistDao.createPlaylist(playlist);
				playlist.addSong(songDao.getSongById(songId), LocalDateTime.now());
				u.addPlaylist(playlist);
				playlistDao.addSongToPlaylist(playlistDao.getPlaylistId(playlistTitle, u.getUserID()), songId);	
				
				return "playlist";				
		} 
		catch (SQLException e) {
			return "errorPage";
		}
	}
	
	@RequestMapping(value="/playlist", method=RequestMethod.GET)
	public String songePage(Model model, HttpSession session, @RequestParam(value = "id") long id){
		
		try {
			Playlist pl = playlistDao.searchPlaylistById(id);
			model.addAttribute("commentable", pl);

		} catch (SQLException e) {
			return "errorPage";
		}
				
		return "playlist";
	}

}
