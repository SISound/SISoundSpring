package com.sisound.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.google.gson.JsonArray;
import com.sisound.model.Playlist;
import com.sisound.model.Song;
import com.sisound.model.User;
import com.sisound.model.db.PlaylistDao;
import com.sisound.model.db.SongDao;
import com.sisound.model.db.UserDao;

@Controller
public class SearchController {

	
	@Autowired
	SongDao songDao;
	@Autowired
	UserDao userDao;
	@Autowired
	PlaylistDao playlistDao;
	
	@RequestMapping(value="searchsong", method=RequestMethod.GET)
	public String searchSongs(Model model,  @RequestParam(value = "value") String input){
		
		try {
			HashSet<Song> songs= songDao.searchSongByName(input);
			System.out.println(songs.size());
			if(songs!=null){
				model.addAttribute("songsToShow", songs);
				model.addAttribute("searched", input);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "songSearch";
	}
	
	@RequestMapping(value="/searchuser", method=RequestMethod.GET)
	public String searchUsers(Model model,  @RequestParam(value = "value") String input){
		
		//String search=(String) req.getParameter("search");
		
		try {
			HashSet<User> users=userDao.searchUser(input);
			if(users!=null){
				model.addAttribute("searchedUsers", users);
				model.addAttribute("searched", input);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "userSearch";
	}
	
	@RequestMapping(value="/searchplaylist", method=RequestMethod.GET)
	public String searchPlaylist(Model model, @RequestParam(value="value") String input){
		try {
			HashSet<Playlist> playlists = playlistDao.searchPlaylist(input);
			if(playlists!=null){
				model.addAttribute("searchedPlaylists", playlists);
				model.addAttribute("searched", input);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "playlistSearch";
	}
	
	@RequestMapping(value="search", method=RequestMethod.GET)
	public String search(HttpServletRequest req){
		
		String input = req.getParameter("search");
		String type = req.getParameter("searchType");
		
		
		//return "redirect:/search=" + type + "?" + input;
		return "redirect:/search" + type +"?value=" + input;
	}	
}
