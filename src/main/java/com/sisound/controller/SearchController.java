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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.google.gson.JsonArray;
import com.sisound.model.Song;
import com.sisound.model.db.SongDao;

@Controller
public class SearchController {

	private static HashSet<Song> songs=new HashSet<>();
	
	static{
		songs.add(new Song(5, "Pesnichka", LocalDateTime.now(), 3, null, null, null, null, null));
	}
	
	@Autowired
	SongDao songDao;
	
	@RequestMapping(value="searchSongs")
	@ResponseBody
	public void searchSongs(HttpServletRequest req, HttpServletResponse resp){
		String search=req.getParameter("title").trim();
		try {
//			TreeSet<Song> allSongs=songDao.getAllSongs();
//			if(allSongs.size()>this.songs.size()){
//				this.songs.clear();
//				this.songs.addAll(allSongs);
//			}
			
			ArrayList<String> searching=new ArrayList<>();
			for (Song song : songs) {
				if(song.getTitle().toUpperCase().contains(search.toUpperCase())){
					searching.add(song.getTitle());
				}
			}
			
			JsonArray arr=new JsonArray();
			
			for(String s: searching){
				arr.add(s);
			}
			
			resp.getWriter().write(arr.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
