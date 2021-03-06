package com.sisound.controller;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sisound.model.Song;
import com.sisound.model.User;
import com.sisound.model.db.CountryDao;
import com.sisound.model.db.GenresDao;
import com.sisound.model.db.SongDao;
import com.sisound.model.db.UserDao;


@Controller
public class WelcomeController {

	@Autowired
	UserDao userDao;
	@Autowired
	SongDao songDao;
	@Autowired
	GenresDao genresDao;
	@Autowired
	CountryDao countryDao;
	
	@RequestMapping(value="/index{x}")
	public String welcome(HttpSession session, Model model, @PathVariable String x){
		
		HashSet<Song> songs;
		try {
			
			switch (x) {
			case "=likes":
				LinkedHashSet<Song> sortedByLikes=songDao.getTop10();
				model.addAttribute("songsToShow", sortedByLikes);
				break;
			case "=recent":
				songs = songDao.getAllSongs();
				TreeSet<Song> sortedByDate = new TreeSet<>((o1, o2)->(o1.getUploadDate().compareTo(o2.getUploadDate()))*-1);
				sortedByDate.addAll(songs);
				model.addAttribute("songsToShow", sortedByDate);
				break;
			default:
				songs = songDao.getAllSongs();
				model.addAttribute("songsToShow", songs);
				break;
			}
						
			return "main3";
			
		} catch (SQLException e) {
			return "errorPage";
		}
		
	}
	
	//all the songs of the people user follows
	@RequestMapping(value="/recent", method=RequestMethod.GET)
	public String searchUsers(Model model, HttpSession session){
		
		User u = (User)session.getAttribute("sessionUser");
		
		if(u == null){
			return "redirect:/index";
		}
		
		try {
			HashSet<Song> songs = songDao.getFollowingSongs(u.getUserID());
			TreeSet<Song> sortedByDate = new TreeSet<>((o1, o2)->(o1.getUploadDate().compareTo(o2.getUploadDate()))*-1);
			sortedByDate.addAll(songs);
			model.addAttribute("songsToShow", sortedByDate);
			
		} catch (SQLException e) {
			return "errorPage";
		}
		
		return "following";
	}
}
