package com.sisound.controller;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sisound.model.Song;
import com.sisound.model.db.SongDao;

@Controller
public class SortingController {

	@Autowired
	SongDao songDao;
	
	@RequestMapping(value="sortSongs/{sorter}")
	public String sortSongs(@PathVariable("sorter") String sorter, Model model){
		
		try {
			HashSet<Song> songs=songDao.getAllSongs();
			
			if(sorter.equals("date")){
				TreeSet<Song> sortedSongs=new TreeSet<>((o1, o2)->o1.getUploadDate().compareTo(o2.getUploadDate()));
				sortedSongs.addAll(songs);
				model.addAttribute("sortedSongs", sortedSongs);
			}
			else{
				TreeSet<Song> sortedSongs=new TreeSet<>((o1, o2)->Long.compare(o1.getTimesListened(), o2.getTimesListened()));
				sortedSongs.addAll(songs);
				model.addAttribute("sortedSongs", sortedSongs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "sortedPage";
	}
}
