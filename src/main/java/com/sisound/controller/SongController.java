package com.sisound.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.TreeSet;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sisound.WebInitializer;
import com.sisound.model.Actions;
import com.sisound.model.Song;
import com.sisound.model.User;
import com.sisound.model.db.ActionsDao;
import com.sisound.model.db.SongDao;


@Controller
@MultipartConfig
public class SongController {

	@Autowired
	SongDao songDao;
	@Autowired
	ActionsDao actionDao;
	
	//UPLOADING PAGE
	@RequestMapping(value="uploadPage", method=RequestMethod.GET)
	public String uploadPage(){
		return "upload";
	}
	
	//SAVING THE SELECTED SONG
	@RequestMapping(value="saveSong", method=RequestMethod.POST)
	public String saveSong(HttpSession session, Model model, @RequestParam("song") MultipartFile file){
		User u=(User) session.getAttribute("sessionUser");
		File f=new File(WebInitializer.LOCATION + File.separator + file.getOriginalFilename());
		
		try {
			file.transferTo(f);
			
			Song song=new Song(file.getOriginalFilename(), u, "Rock", file.getOriginalFilename(), LocalDateTime.now());
			songDao.uploadSong(song);
			
			HashSet<Song> songs=songDao.getAllSongs();
			model.addAttribute("songs", songs);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "main";
	}
	
	//SEND SONG TO PLAYER
	@RequestMapping(value="getSong{songUrl}", method=RequestMethod.GET)
	@ResponseBody
	public void getSong(@PathVariable String songUrl, HttpServletResponse resp){
		File file=new File(WebInitializer.LOCATION + File.separator + "songs" + File.separator + songUrl + ".mp3");
		try {
			Files.copy(file.toPath(), resp.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	//SEND TO FANCY PLAYER
//	@RequestMapping(value="getMusic", method=RequestMethod.GET)
//	@ResponseBody
//	public void getMusic(HttpServletRequest req, HttpServletResponse resp){
//		String url=(String) req.getParameter("url");
//		File file=new File(WebInitializer.LOCATION + File.separator + "songs" + File.separator + url);
//		try {
//			Files.copy(file.toPath(), resp.getOutputStream());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	//LIKE SONG
	@RequestMapping(value="likeSong", method=RequestMethod.POST)
	@ResponseBody
	public void likeSong(HttpServletRequest req, HttpServletResponse resp, HttpSession session){
		long songId=Long.parseLong(req.getParameter("songId").toString());
		
		System.out.println("PESENTA E " + songId);
		User u=(User) session.getAttribute("sessionUser");
		try {
			actionDao.likeSong(songId, u.getUserID());
			actionDao.deleteDislikes(true, songId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resp.setStatus(200);
	}
	
	//DISLIKE SONG
	@RequestMapping(value="dislikeSong", method=RequestMethod.POST)
	@ResponseBody
	public void dislikeSong(HttpServletRequest req, HttpServletResponse resp, HttpSession session){
		long songId=Long.parseLong(req.getParameter("songId").toString());
		
		User u=(User) session.getAttribute("sessionUser");
		try {
			actionDao.dislikeSong(songId, u.getUserID());
			actionDao.deleteLikes(true, songId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resp.setStatus(200);
	}
}
