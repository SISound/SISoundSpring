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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.google.gson.JsonArray;
import com.sisound.model.Song;
import com.sisound.model.User;
import com.sisound.model.db.SongDao;
import com.sisound.model.db.UserDao;

@Controller
public class SearchController {

	
	@Autowired
	SongDao songDao;
	@Autowired
	UserDao userDao;
	
	@RequestMapping(value="searchSong", method=RequestMethod.GET)
	public String searchSongs(HttpServletRequest req, HttpServletResponse resp, Model model){
		
		String search= (String) req.getParameter("search");
		System.out.println(search);
		try {
			HashSet<Song> songs= songDao.searchSongByName(search);
			System.out.println(songs.size());
			if(songs!=null){
				model.addAttribute("searchedSongs", songs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "songSearch";
	}
	
	@RequestMapping(value="searchUser", method=RequestMethod.GET)
	public String searchUsers(HttpServletRequest req, HttpServletResponse resp, Model model){
		
		String search=(String) req.getParameter("search");
		
		try {
			HashSet<User> users=userDao.searchUser(search);
			if(users!=null){
				model.addAttribute("searchedUsers", users);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "userSearch";
	}
}
