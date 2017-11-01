package com.sisound.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sisound.WebInitializer;
import com.sisound.model.Song;
import com.sisound.model.User;
import com.sisound.model.db.ActionsDao;
import com.sisound.model.db.GenresDao;
import com.sisound.model.db.SongDao;


@Controller
@MultipartConfig
public class SongController {

	@Autowired
	SongDao songDao;
	@Autowired
	ActionsDao actionDao;
	@Autowired
	GenresDao genresDao;
	
	//UPLOADING PAGE
	@RequestMapping(value="uploadPage", method=RequestMethod.GET)
	public String uploadPage(Model model){
		try {
			Map<String, Long> genres=genresDao.getAllGenres();
			model.addAttribute("genres", genres);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "upload";
	}
	
	//SAVING THE SELECTED SONG
	@RequestMapping(value="saveSong", method=RequestMethod.POST)
	public String saveSong(HttpSession session, Model model, @RequestParam("song") MultipartFile file, HttpServletRequest req, HttpServletResponse resp){
		User u=(User) session.getAttribute("sessionUser");
		File f=new File(WebInitializer.LOCATION + File.separator + "songs" + File.separator + file.getOriginalFilename());
		String genre=(String) req.getParameter("genre");
		System.out.println("THE GENRE IS" + genre);
		
			try {
				file.transferTo(f);
				
				Song song=new Song(file.getOriginalFilename(), u, genre, file.getOriginalFilename(), LocalDateTime.now());
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
		
		return "upload";
	}
	
	//SEND SONG TO PLAYER
	@RequestMapping(value="getSong{songUrl}", method=RequestMethod.GET)
	@ResponseBody
	public void getSong(@PathVariable String songUrl, HttpServletResponse resp){
		System.out.println(songUrl);
		System.out.println(songUrl);
		System.out.println(songUrl);
		System.out.println(songUrl);
		System.out.println(songUrl);
		System.out.println(songUrl);
		
		File file=new File(WebInitializer.LOCATION + File.separator + "songs" + File.separator + songUrl + ".mp3");
		System.out.println(file.toPath());
		System.out.println(file.toPath());System.out.println(file.toPath());
		System.out.println(file.toPath());
		System.out.println(file.toPath());
		System.out.println(file.toPath());
		
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
		User u=(User) session.getAttribute("sessionUser");
		long songId=Long.parseLong(req.getParameter("songId").toString());
		if(u==null){
			resp.setStatus(401);
		}
		else{
			try {
				if(songDao.getSongById(songId).getUser().equals(u)){
					return;
				}
				actionDao.likeSong(songId, u.getUserID());
				actionDao.removeDislike(true, songId, u.getUserID());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resp.setStatus(200);
		}
	}
	
	//DISLIKE SONG
	@RequestMapping(value="dislikeSong", method=RequestMethod.POST)
	@ResponseBody
	public void dislikeSong(HttpServletRequest req, HttpServletResponse resp, HttpSession session){
		User u=(User) session.getAttribute("sessionUser");
		if(u==null){
			resp.setStatus(401);
		}
		else{
			long songId=Long.parseLong(req.getParameter("songId").toString());
			try {
				if(songDao.getSongById(songId).getUser().equals(u)){
					return;
				}
				actionDao.dislikeSong(songId, u.getUserID());
				actionDao.removeLike(true, songId, u.getUserID());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resp.setStatus(200);
		}
	}
	
	//track page
	@RequestMapping(value="track={x}", method=RequestMethod.GET)
	public String songePage(@PathVariable Long x, Model model, HttpSession session){
		
		try {
			Song song = songDao.getSongById(x);
			model.addAttribute("modelSong", song);

			model.addAttribute("modelUser", song.getUser());
//			session.setAttribute("avatar", song.getUser().getProfilPicture());
			session.setAttribute("songProfile", song.getUrl());
		} catch (SQLException e) {
			e.printStackTrace();
			return "errorPage";
		}
				
		return "track";
	}
	
//	@RequestMapping(value="getSongProfile", method=RequestMethod.GET)
//	public void getSongForProfile(HttpServletResponse resp, HttpSession session){
//
//		try {
//			File file=new File((String)session.getAttribute("songProfile"));
//			Files.copy(file.toPath(), resp.getOutputStream());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println("opa");
//		
////		return "redirect:track={x}";
//	}
}
