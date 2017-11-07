package com.sisound.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
			return "errorPage";
		}
		return "upload";
	}
	
	//SAVING THE SELECTED SONG
	@RequestMapping(value="saveSong", method=RequestMethod.POST)
	public String saveSong(HttpSession session, Model model, @RequestParam("song") MultipartFile file, HttpServletRequest req, HttpServletResponse resp){
		User u = (User) session.getAttribute("sessionUser");
		File f=new File(WebInitializer.LOCATION + File.separator + "songs" + File.separator + file.getOriginalFilename());
		String genre=(String) req.getParameter("genre");
		
			try {
				
				Song song = new Song(file.getOriginalFilename(), u, genre, file.getOriginalFilename(), LocalDateTime.now());
				songDao.uploadSong(song);
				file.transferTo(f);
				
			} catch (IllegalStateException | IOException | SQLException e) {
				return "errorPage";
			}
		
		return "redirect:/profile" + u.getUsername();
	}
	
	//SEND SONG TO PLAYER
	@RequestMapping(value="getSong{songUrl}", method=RequestMethod.GET)
	@ResponseBody
	public void getSong(@PathVariable String songUrl, HttpServletResponse resp){
		
		File file=new File(WebInitializer.LOCATION + File.separator + "songs" + File.separator + songUrl + ".mp3");
		
		try {
			Files.copy(file.toPath(), resp.getOutputStream());
		} catch (IOException e) {
			resp.setStatus(503);
		}
	}
		
	//LIKE SONG
	@RequestMapping(value="likeSong", method=RequestMethod.POST)
	@ResponseBody
	public void likeSong(HttpServletRequest req, HttpServletResponse resp, HttpSession session){
		User u=(User) session.getAttribute("sessionUser");
		boolean isLogged=(boolean) session.getAttribute("logged");
		System.out.println(isLogged);
		long songId=Long.parseLong(req.getParameter("song").toString());
		if(u.getUsername()==null){
			resp.setStatus(401);
		}
		else{
			try {
				if(!songDao.isSongLiked(songId, u.getUserID())){
					actionDao.likeSong(songId, u.getUserID());
				}
			} catch (SQLException e) {
				resp.setStatus(503);
			}
			resp.setStatus(200);
		}
	}
	
	//DISLIKE SONG
	@RequestMapping(value="dislikeSong", method=RequestMethod.POST)
	@ResponseBody
	public void dislikeSong(HttpServletRequest req, HttpServletResponse resp, HttpSession session){
		User u=(User) session.getAttribute("sessionUser");
		long songId=Long.parseLong(req.getParameter("songId").toString());
		if(u==null){
			resp.setStatus(401);
		}
		else{
			try {
				if(!songDao.isSongDisliked(songId, u.getUserID())){
					actionDao.dislikeSong(songId, u.getUserID());
					actionDao.removeLike(true, songId, u.getUserID());
					resp.setStatus(200);
				}
				else{
					resp.setStatus(405);
				}
			} catch (SQLException e) {
				resp.setStatus(503);
			}
		}
	}
	
	//track page
	@RequestMapping(value="track={x}", method=RequestMethod.GET)
	public String songePage(@PathVariable Long x, Model model, HttpSession session){
		
		try {
			Song song = songDao.getSongById(x);
			model.addAttribute("commentable", song);
			model.addAttribute("modelUser", song.getUser());

		} catch (SQLException e) {
			e.printStackTrace();
			return "errorPage";
		}
				
		return "track";
	}
	
	@RequestMapping(value="deleteSong", method=RequestMethod.POST)
	public String addComment(HttpSession session, @RequestParam(value = "id") long id){
		
		User u = (User) session.getAttribute("sessionUser");
		
		if(session.isNew() || u==null){
			return "logReg";
		}
		
		try {
			songDao.deleteSong(id);
			return "redirect:/profile" + u.getUsername();				
		} 
		catch (SQLException e) {
			System.out.println(e.getMessage());
			return "errorPage";
		}
	}
}
