package com.sisound.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sisound.WebInitializer;
import com.sisound.model.Song;
import com.sisound.model.User;
import com.sisound.model.db.SongDao;

@Controller
@MultipartConfig
@RequestMapping(value="songCtrl")
public class SongController {

	@Autowired
	SongDao songDao;
	
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
			
			TreeSet<Song> songs=songDao.getAllSongs();
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
	
	@RequestMapping(value="getSong{songUrl}", method=RequestMethod.GET)
	@ResponseBody
	public void getSong(@PathVariable String songUrl, HttpServletResponse resp){
		File file=new File(WebInitializer.LOCATION + File.separator + songUrl + ".mp3");
		try {
			Files.copy(file.toPath(), resp.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
