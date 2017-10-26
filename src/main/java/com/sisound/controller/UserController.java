package com.sisound.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.sql.SQLException;
import java.util.Map;
import java.util.TreeSet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sisound.model.Song;
import com.sisound.model.User;
import com.sisound.model.db.GenresDao;
import com.sisound.model.db.SongDao;
import com.sisound.model.db.UserDao;

@Controller
public class UserController {

	@Autowired
	UserDao userDao;
	@Autowired
	SongDao songDao;
	@Autowired
	GenresDao genresDao;
	
	//register
	@RequestMapping(value="regPage", method = RequestMethod.GET)
	public String addUser(Model m){
		User u = new User();
		m.addAttribute("user", u);
		return "register";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String saveUser(@ModelAttribute User newUser){
		try {
			userDao.insertUser(newUser);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return "main";
	}

	//login user
	@RequestMapping(value="loginPage", method=RequestMethod.GET)
	public String loginPage(){
		return "login";
	}
	
	@RequestMapping(value="logUser", method=RequestMethod.POST)
	public String loginUser(HttpServletRequest request, ServletContext application){
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		
		try {
			boolean exist=userDao.existsUser(username, password);
			if(exist){
				User u=userDao.getUser(username);
				request.getSession().setAttribute("user", u);
				synchronized (application) {
					if(application.getAttribute("songs") == null){
						TreeSet<Song> songs = songDao.getAllSongs();
						application.setAttribute("songs", songs);
					}
					if(application.getAttribute("genres") == null){
						Map genres=genresDao.getAllGenres();
						application.setAttribute("genres", genres);
					}
				}
				return "main";
			}
			else{
				request.setAttribute("error", "User does not exist!");
				return "login";
			}
		} catch (SQLException e) {
			request.setAttribute("error", "database problem : " + e.getMessage());
			return "index";
		}
	}
}
