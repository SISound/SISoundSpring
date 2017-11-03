package com.sisound.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sisound.model.Song;

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
//		Object o = session.getAttribute("logged");
//		boolean logged =  (o != null && ((boolean) o ));
		
		//TODO
		
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
//			songs = songDao.getAllSongs();
//			
//			if(session.getAttribute("songs") == null){
//				session.setAttribute("songs", songs);
//			}
//			
//			TreeSet<Song> sortedByDate=new TreeSet<>((o1, o2)->(o1.getUploadDate().compareTo(o2.getUploadDate()))*-1);
//			sortedByDate.addAll(songs);
//			if(session.getAttribute("sortedByDate") == null){
//				session.setAttribute("sortedByDate", sortedByDate);
//			}
//			
//			LinkedHashSet<Song> sortedByLikes=songDao.getTop10();
//			if(session.getAttribute("sortedByLikes")==null){
//				session.setAttribute("sortedByLikes", sortedByLikes);
//			}
//			model.addAttribute("sortedByLikes", sortedByLikes);
			
		} catch (SQLException e) {
			e.printStackTrace();
			return "errorPage";
		}
		
//		if(session.isNew() || !logged){
//			return "index";
//		}
//		else{
//			return "main";
//		}
	}
}
